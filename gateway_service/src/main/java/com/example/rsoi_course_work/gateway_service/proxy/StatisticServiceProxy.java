package com.example.rsoi_course_work.gateway_service.proxy;


import com.example.rsoi_course_work.gateway_service.model.statistic_operation.StatisticOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "statistic-service", url = "http://localhost:8060/api/v1")
public interface StatisticServiceProxy {
    @GetMapping("/statistic-operations")
    public ResponseEntity<List<StatisticOperation>> getStatisticOperations();

    @PostMapping("/statistic-operations")
    public ResponseEntity<HttpStatus> createStatisticOperation(@RequestBody StatisticOperation statisticOperation);
}
