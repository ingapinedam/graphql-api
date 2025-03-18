package com.observability.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateResponse {
    private String currentDate;
    private String formattedDate;
    private Long timestamp;
    private String timezone;
}