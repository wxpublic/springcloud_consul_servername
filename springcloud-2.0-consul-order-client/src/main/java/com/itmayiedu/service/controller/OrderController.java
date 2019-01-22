package com.itmayiedu.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @version 1.0
 * @Description:
 * @author: ChenRuiQing.
 * Create Time:  2019-01-21 下午 9:29
 */
@RestController
public class OrderController {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    private int i = 0;
    private String memberUrl;
    // 此Demo为非轮询模式！手动代码模拟轮询；
    @RequestMapping("/getOrder")
    public String getOrder(){
        if(i==0){
            memberUrl = discoveryClient.getInstances("consul-member").get(0).getUri().toString()+"/getMember";
            i = 1;
        }else{
            memberUrl = discoveryClient.getInstances("consul-member").get(1).getUri().toString()+"/getMember";
            i = 0;
        }
        System.out.println("当前URL："+memberUrl);
        String resultMember = restTemplate.getForObject(memberUrl, String.class);
        System.out.println("RPC getMember方法结果："+memberUrl);
        return "我是getOrder订单服务，我要调用的会员服务说：>>"+resultMember;
    }

    @RequestMapping("/discoveryClientMember")
    public List<ServiceInstance> discoveryClientMember() {
        List<ServiceInstance> instances = discoveryClient.getInstances("consul-member");
        for (ServiceInstance serviceInstance : instances) {
            System.out.println("url:" + serviceInstance.getUri());
        }
        return instances;
    }
}