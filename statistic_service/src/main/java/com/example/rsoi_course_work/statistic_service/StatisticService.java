package com.example.rsoi_course_work.statistic_service;

import com.example.rsoi_course_work.statistic_service.model.StatisticOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {
    private final StatisticOperationRepository statisticOperationRepository;

    public StatisticService(StatisticOperationRepository statisticOperationRepository) {
        this.statisticOperationRepository = statisticOperationRepository;
    }

    public ResponseEntity<List<StatisticOperation>> getStatisticOperations() {
        return new ResponseEntity<>(statisticOperationRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createStatisticOperation(StatisticOperation statisticOperation) {
        statisticOperationRepository.save(new StatisticOperation(statisticOperation.getStatistic_operation_uid(),
                statisticOperation.getService_type(), statisticOperation.getStatistic_operation_type(),
                statisticOperation.getDate(), statisticOperation.getUser_uid(), statisticOperation.getScooter_uid(),
                statisticOperation.getLocated_scooter_uid(), statisticOperation.getRental_station_uid(),
                statisticOperation.getRental_uid(), statisticOperation.getPayment_uid()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}