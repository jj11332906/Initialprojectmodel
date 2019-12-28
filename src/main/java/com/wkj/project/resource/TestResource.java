package com.wkj.project.resource;

import com.wkj.project.test.Sender;
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
    @ApiOperation(value="helloRabbit")
    public void helloRabbit() throws Exception{
        sender.send();
    }


}
