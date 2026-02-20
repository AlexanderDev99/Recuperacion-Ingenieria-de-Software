package com.fitflow.scheduler.data.entities.apis.metrics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsApi {

    String userId;
    private String exercise;
    private Double value;
    private String unit;
    private String classId;


}
