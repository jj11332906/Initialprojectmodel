package com.wkj.project.resource;

import com.wkj.project.test.MQTest;
import com.wkj.project.util.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
@Slf4j
@Api(description = "测试")
public class TestResource {

    @Autowired
    HttpRequestUtil httpRequestUtil;

    @Autowired
    MQTest mqTest;

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
        String msg  = httpRequestUtil.doGet("",null);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("testSendMq")
    @ApiOperation(value = "testSendMq")
    public ResponseEntity<String> testSendMq(
    ) {
      mqTest.sendMq();
        return ResponseEntity.ok("success");
    }
    @GetMapping("testSendMqRabbit")
    @ApiOperation(value = "testSendMqRabbit")
    public ResponseEntity<String> testSendMqRabbit(
    ) {
        mqTest.sendMqRabbit();
        return ResponseEntity.ok("success");
    }
    @GetMapping("testSendMqExchange")
    @ApiOperation(value = "testSendMqExchange")
    public ResponseEntity<String> testSendMqExchange(
    ) {
        mqTest.sendMqExchange();
        return ResponseEntity.ok("success");
    }



}
