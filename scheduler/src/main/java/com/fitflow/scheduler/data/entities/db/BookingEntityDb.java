package com.fitflow.scheduler.data.entities.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.fitflow.scheduler.data.entities.Status;

@Document(collection = "booking")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntityDb {

    @Id
    private String userId;
    private String classId;
    private LocalDateTime bookingDate;
    private Status status;
    private LocalDateTime createdAt;
}
