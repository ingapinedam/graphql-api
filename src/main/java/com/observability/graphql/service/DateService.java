package com.observability.graphql.service;

import com.observability.graphql.client.DateServiceClient;
import com.observability.graphql.model.DateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DateService {

    private DateServiceClient dateServiceClient;

    @Autowired
    public DateService(DateServiceClient dateServiceClient) {
        this.dateServiceClient = dateServiceClient;
    }

    public DateResponse getCurrentDate() {
        try {
            DateResponse dateResponse = dateServiceClient.getCurrentDate();
            log.info("Current date from service: {}", dateResponse.getFormattedDate());
            return dateResponse;
        } catch (Exception e) {
            log.error("Error fetching date from service: {}", e.getMessage());
            throw e;
        }
    }

}
