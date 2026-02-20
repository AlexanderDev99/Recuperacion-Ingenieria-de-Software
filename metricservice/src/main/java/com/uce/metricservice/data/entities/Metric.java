package com.uce.metricservice.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "metrics")
public class Metric {
    @Id
    private String id;
    private String userId;
    private String exercise; // Ej: "Deadlift"
    private Double value;    // Ej: 120.5
    private String unit;     // Ej: "kg"
    private String classId;  // Consultado al microservicio encargado
    private LocalDateTime timestamp; // Fecha y hora del registro
}
