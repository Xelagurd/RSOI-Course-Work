package com.example.rsoi_course_work.gateway_service.proxy;


import com.example.rsoi_course_work.gateway_service.model.LocatedScooter;
import com.example.rsoi_course_work.gateway_service.model.LocatedScooterPartialList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "statistic-service", url = "https://statistic-service-xelagurd.herokuapp.com/api/v1")
public interface StatisticServiceProxy {
}
