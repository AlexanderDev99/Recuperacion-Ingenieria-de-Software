package com.uce.metricservice.data.entities;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private String userId;
    private String classId;
    private LocalDateTime bookingDate;
}