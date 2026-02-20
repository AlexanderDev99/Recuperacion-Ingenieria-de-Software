package com.uce.metricservice.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsDTO {
    private String userId;
    private String exercise; // Ej: "Deadlift"
    private Double value;    // Ej: 120.5
    private String unit;     // Ej: "kg"
    private String classId;  // No se ingresa desde el endpoint sino se consulta al microservicio encargado
}
