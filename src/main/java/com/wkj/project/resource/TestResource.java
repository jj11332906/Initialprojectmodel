package com.wkj.project.resource;

import com.wkj.project.test.FanoutSender;
import com.wkj.project.test.Sender;
import com.wkj.project.test.TopicSend;
import com.wkj.project.util.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/test")
@Slf4j
@Api(description = "测试")
public class TestResource {

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Autowired
    Sender sender;
    @Autowired
    FanoutSender fanoutSender;

    @Autowired
    TopicSend topicSend;

    @GetMapping("/product/{id}")
    @ApiOperation(value = "getProduct")
    public String getProduct(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    @ApiOperation(value = "getOrder")
    public String getOrder(@PathVariable String id) {
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }

    @GetMapping("testHttp")
    @ApiOperation(value = "testHttp")
    public ResponseEntity<String> testHttp(
    ) {
        String msg = httpRequestUtil.doGet("", null);
        return ResponseEntity.ok(msg);
    }


    @PostMapping("helloRabbit")
    @ApiOperation(value="rabbitmq简单模式")
    public void helloRabbit() throws Exception{
        sender.send();
    }

    @PostMapping("neoRabbit")
    @ApiOperation(value="rabbitmq工作模式")
    public void neoRabbit() throws Exception{
        for (int i=0;i<10;i++) {
            sender.send(i);
        }
    }

    @PostMapping("fanoutSender")
    @ApiOperation(value="rabbitmq发布订阅模式")
    public void fanoutSender() throws Exception{
       fanoutSender.send();
    }

    @PostMapping("topicSender")
    @ApiOperation(value="rabbitmq路由模式及主题模式")
    public void topicSender() throws Exception{
        topicSend.send();
    }

    @PostMapping("topicSender1")
    @ApiOperation(value="rabbitmq路由模式及主题模式")
    public void topicSender1() throws Exception{
        topicSend.send1();
    }

    @PostMapping("topicSender2")
    @ApiOperation(value="rabbitmq路由模式及主题模式")
    public void topicSender2() throws Exception{
        topicSend.send2();
    }


}
