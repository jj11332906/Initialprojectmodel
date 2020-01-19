package com.wkj.project.service;

import com.wkj.project.entity.Exposer;
import com.wkj.project.entity.Seckill;
import com.wkj.project.entity.SeckillExecution;
import com.wkj.project.entity.SeckillOrder;
import com.wkj.project.enums.SeckillStatEnum;
import com.wkj.project.exception.RepeatKillException;
import com.wkj.project.exception.SeckillCloseException;
import com.wkj.project.exception.SeckillException;
import com.wkj.project.mapper.SeckillMapper;
import com.wkj.project.mapper.SeckillOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SeckillService {

    @Autowired
    SeckillMapper seckillMapper;
    @Autowired
    SeckillOrderMapper seckillOrderMapper;
    //设置盐值字符串，随便定义，用于混淆MD5值
    private final String salt = "sjajaspu-i-2jrfm;sd";

    /**
     * 获取所有的秒杀商品列表
     *
     * @return
     */
    public List<Seckill> findAll(){
        return seckillMapper.findAll();
    }
    /**
     * 获取某一条商品秒杀信息
     *
     * @param seckillId
     * @return
     */
    public Seckill findById(long seckillId){
        return seckillMapper.findById(seckillId);
    }
    /**
     * 秒杀开始时输出暴露秒杀的地址
     * 否者输出系统时间和秒杀时间
     * 在之前我们做的后端项目中，跳转到某个详情页一般都是：根据ID查询该详情数据，然后将页面跳转到详情页并将数据直接渲染到页面上。但是秒杀系统不同，它也不能就这样简单的定义，要知道秒杀技术的难点就是如何应对高并发？同一件商品，比如瞬间有十万的用户访问，而还存在各种黄牛，有各种工具去抢购这个商品，那么此时肯定不止10万的访问量的，并且开发者要尽量的保证每个用户抢购的公平性，也就是不能让一个用户抢购一堆数量的此商品。
     * 这就是我们常说的接口防刷问题。因此单独定义一个获取秒杀接口的方法是有必要的。
     * 接口方法：Exposer exportSeckillUrl(long seckillId);从参数列表中很易明白：就是根据该商品的ID获取到这个商品的秒杀url地址；但是返回值类型Exposer是什么呢？
         思考一下如何做到接口防刷？
         首先要保证该商品处于秒杀状态。也就是1.秒杀开始时间要<当前时间；2.秒杀截止时间要>当前时间。
         要保证一个用户只能抢购到一件该商品，应做到商品秒杀接口对应同一用户只能有唯一的一个URL秒杀地址，不同用户间秒杀地址应是不同的，且配合订单表seckill_order中联合主键的配置实现。
     * @param seckillId
     */
    public Exposer exportSeckillUrl(long seckillId){
        Seckill seckill = seckillMapper.findById(seckillId);
        if(seckill == null){
            //说明没有查询到
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //获取系统时间
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转换特定字符串的过程，不可逆的算法
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    //生成MD5值
    private String getMD5(Long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 执行秒杀的操作
     *
     * @param seckillId
     * @param userPhone
     * @param money
     * @param md5
     * 可以看到针对库存业务其实还是两个操作：1.减库存；2.记录购买明细。但是其中涉及到很多事物操作和性能优化问题我们放在后面讲。这里我们将这两个操作合并为一个接口方法：执行秒杀的操作。
     * seckillId和userPhone用于在insert订单明细时进行防重复秒杀；只要有相同的seckillId和userPhone就一定主键冲突报错。
     * seckillId和md5用于组成秒杀接口地址的一部分，当用户点击抢购时获取到之前暴露的秒杀地址中的md5值和当前传入的md5值进行比较，如果匹配再进行下一步操作。
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, BigDecimal money, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException
    {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑： 1.减库存；2.储存秒杀订单
        Date nowTime = new Date();

        try{
            //记录秒杀订单信息
            int insertCount = seckillOrderMapper.insertOrder(seckillId,money,userPhone);
            //唯一性：seckillId,userPhone,保证一个用户只能秒杀一件商品
            if(insertCount<=0){
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            }else{
                //减库存
                int updateCount = seckillMapper.reduceStock(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新记录，秒杀结束
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功
                    SeckillOrder seckillOrder = seckillOrderMapper.findById(seckillId);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS.getState(),SeckillStatEnum.SUCCESS.getStateInfo(), seckillOrder);
                }
            }

        } catch (SeckillCloseException | RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //所有编译期异常，转换为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }

    }

}
