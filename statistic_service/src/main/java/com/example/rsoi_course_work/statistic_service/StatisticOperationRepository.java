package com.example.rsoi_course_work.statistic_service;

import com.example.rsoi_course_work.statistic_service.model.StatisticOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticOperationRepository extends JpaRepository<StatisticOperation, Long> {
}