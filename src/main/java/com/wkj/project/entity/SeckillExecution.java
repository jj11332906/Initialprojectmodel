package com.wkj.project.entity;

public class SeckillExecution {

    private Long seckillId;

    //秒杀执行结果状态state用于-1,0,1这种状态的表示，这些数字分别被赋予不同的含义，后面讲到。stateInfo表示state状态数字的中文解释，比如：秒杀成功、秒杀结束、秒杀系统异常等信息。
    private int state;

    //状态标识
    private String stateInfo;

    //秒杀成功的订单对象
    private SeckillOrder seckillOrder;

    public SeckillExecution(Long seckillId,int state,String stateInfo,SeckillOrder seckillOrder){
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.seckillOrder = seckillOrder;
    }

    public SeckillExecution(Long seckillId,int state,String stateInfo){
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

}
