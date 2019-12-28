package com.wkj.project.resource;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;

import java.util.HashMap;

/**
 * 开放网关服务调用例子
 *
 * https://gateway-test.1919.cn/actuator/gateway/routes 查看统一网关上，哪些服务注册了什么API上去
 *
 * @author Zebe
 */
public class OpenApiApplicationTests {

    /*

    TODO 例子说明（所有参数均为测试环境）
    本例子，依赖了 hutool 工具包中的 JSON 和 MD5摘要计算工具，使用方根据自身需要决定是否依赖此包。
    可以根据自己项目需求，使用自己的解析工具。
    Maven依赖如下：
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>4.1.19</version>
    </dependency>

     */


    public static void main(String[] args) {

     String result = "";

        // TODO 第 4 步：通过网关发起API调用（以查询瓶码基本信息的接口为例）
        String apiUrl = "http://10.0.1.83/icrm/api/web/?app_act=crm/vip/get_vip_information_by_all_list&key=baison&timestamp=1576719517614&OpenID=ovgV1jqhPsj7nM3sR__iaslVRqYs&sign=6e910cba73491950b16e5a9325d44634&v=1";
        HttpRequest request = HttpRequest.get(apiUrl);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Cache-Control","no-cache");
        headers.put("Host","10.0.1.83");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Connection","keep-alive");
        headers.put("User-Agent","Mozilla");

        request.addHeaders(headers);
        System.out.println("\n-> 开始发起网关请求：" + apiUrl + "\n   HEADER头部参数如下：");
        headers.forEach((k, v) -> System.out.println("\t" + k + "：" + v));
        result = request.execute().body();

        // TODO 第 5 步：解析调用结果（注意判断异常情况，因为公共网关是第一层网关，如果调用成功，会返回第二层应用网关的真实接口返回数据）
        // TODO 如果调用失败，返回的JSON结构不是第二层接口返回的（带了method等字段），这一点需要注意看《文档》
        try {
            String jsonStr = new JSONObject(result).toStringPretty();
            System.out.println("<- 模拟发起网关调用返回结果如下：\n" + jsonStr + "\n\n");
        } catch (Exception e) {
            System.out.println("<- 模拟发起网关调用发生异常，返回的原始结果如下：\n" + result + "\n\n");
        }

        System.out.println("\n<- 网关上已注册的接口映射如下：\n");

    }

}