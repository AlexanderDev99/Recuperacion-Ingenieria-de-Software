package com.uce.metricservice.data.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsDTO {
    @NotBlank(message = "El userId es obligatorio")
    private String userId;
    
    @NotBlank(message = "El ejercicio es obligatorio")
    private String exercise; // Ej: "Deadlift"
    
    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a cero")
    private Double value;    // Ej: 120.5
    
    @NotBlank(message = "La unidad es obligatoria")
    private String unit;     // Ej: "kg"
    
    // classId NO se recibe desde el endpoint - se consulta al scheduler
}
