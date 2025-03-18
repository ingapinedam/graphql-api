package com.observability.graphql.client;

import com.observability.graphql.model.DateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "date-service", url = "${date-service.url}")
public interface DateServiceClient {

    @GetMapping("/api/date")
    DateResponse getCurrentDate();
}