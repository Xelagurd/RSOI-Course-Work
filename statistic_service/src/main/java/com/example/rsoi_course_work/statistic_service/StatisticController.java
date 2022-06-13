package com.example.rsoi_course_work.statistic_service;

import com.example.rsoi_course_work.statistic_service.model.StatisticOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/statistic-operations")
    public ResponseEntity<List<StatisticOperation>> getStatisticOperations() {
        return statisticService.getStatisticOperations();
    }

    @PostMapping("/statistic-operations")
    public ResponseEntity<HttpStatus> createStatisticOperation(@RequestBody StatisticOperation statisticOperation) {
        return statisticService.createStatisticOperation(statisticOperation);
    }
}
