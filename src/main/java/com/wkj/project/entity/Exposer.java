package com.wkj.project.entity;

public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //加密措施，避免用户通过抓包拿到秒杀地址
    private String md5;

    //ID
    private long seckillId;

    //系统当前时间（毫秒）
    private long now;

    //秒杀开启时间
    private long start;

    //秒杀结束时间
    private long end;

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, Long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }


    //todo 1.首先指明商品当前秒杀状态：秒杀未开始、秒杀进行中、秒杀已结束；
    //todo 2.如果秒杀未开始返回false和相关时间用于前端展示秒杀倒计时；
    //todo 3.如果秒杀已经结束就返回false和当前商品的ID；
    //todo 4.如果秒杀正在进行中就返回该商品的秒杀地址（md5混合值，避免用户抓包拿到秒杀地址）。
}
