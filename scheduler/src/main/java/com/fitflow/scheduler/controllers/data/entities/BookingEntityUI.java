package com.fitflow.scheduler.controllers.data.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntityUI {
    private String userId;
    private String classId;
    private LocalDateTime bookingDate;

}
