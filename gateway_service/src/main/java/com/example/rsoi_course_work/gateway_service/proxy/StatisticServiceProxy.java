package com.example.rsoi_course_work.gateway_service.proxy;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "statistic-service", url = "https://statistic-service-xelagurd.herokuapp.com/api/v1")
public interface StatisticServiceProxy {
}
