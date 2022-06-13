package com.example.rsoi_course_work.gateway_service;

import com.example.rsoi_course_work.gateway_service.model.statistic_operation.StatisticOperation;
import com.example.rsoi_course_work.gateway_service.proxy.StatisticServiceProxy;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class StatisticOperationsQueue {
    private static BlockingQueue<StatisticOperation> eventQueue = null;

    private final StatisticServiceProxy statisticServiceProxy;

    public StatisticOperationsQueue(StatisticServiceProxy statisticServiceProxy) {
        this.statisticServiceProxy = statisticServiceProxy;
    }

    private void initialize() {
        if (eventQueue == null) {
            eventQueue = new LinkedBlockingQueue<>();
            RequestProcessor requestProcessor = new RequestProcessor();
            requestProcessor.start();
        }
    }

    public void putStatisticOperationInQueue(StatisticOperation statisticOperation) {
        try {
            initialize();
            eventQueue.put(statisticOperation);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    class RequestProcessor extends Thread {
        @Override
        public void run() {
            while (true) {
                StatisticOperation statisticOperation = null;
                try {
                    Thread.sleep(3000);
                    statisticOperation = eventQueue.take();
                    try {
                        statisticServiceProxy.createStatisticOperation(statisticOperation);
                    } catch (FeignException e) {
                        StatisticOperationsQueue.this.putStatisticOperationInQueue(statisticOperation);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}