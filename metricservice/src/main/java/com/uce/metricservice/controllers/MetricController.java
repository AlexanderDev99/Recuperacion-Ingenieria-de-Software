package com.uce.metricservice.controllers;

import com.uce.metricservice.data.entities.Metric;
import com.uce.metricservice.data.entities.MetricsDTO;
import com.uce.metricservice.logic.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// 1. Definimos la base correcta aquí
@RequestMapping("/api/v1/metrics") 
@RequiredArgsConstructor
public class MetricController {
    
    private final MetricService metricService;
    
    /**
     * Endpoint: POST /api/v1/metrics/create
     */
    // 2. Solo ponemos la parte final de la ruta
    @PostMapping("/create") 
    public ResponseEntity<Metric> createMetric(@RequestBody MetricsDTO metricsDTO) {
        Metric savedMetric = metricService.saveMetric(metricsDTO); // Asegúrate de que tu servicio tenga este método
        return new ResponseEntity<>(savedMetric, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint: GET /api/v1/metrics/user/{userId}
     */
    // 3. Este es el GET exacto que pide el requerimiento
    @GetMapping("/user/{userId}") 
    public ResponseEntity<List<Metric>> getMetricsByUser(@PathVariable String userId) {
        // Necesitarás crear este método en tu MetricService y MetricRepository
        List<Metric> metrics = metricService.getMetricsByUserId(userId); 
        return ResponseEntity.ok(metrics);
    }
}