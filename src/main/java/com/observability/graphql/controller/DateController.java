package com.observability.graphql.controller;

import com.observability.graphql.model.DateResponse;
import com.observability.graphql.service.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DateController {

    private final DateService dateService;

    @QueryMapping
    public DateResponse currentDate() {
        log.info("GraphQL query for current date received");
        DateResponse response = dateService.getCurrentDate();
        log.info("Returning date information: {}", response);
        return response;
    }
}
