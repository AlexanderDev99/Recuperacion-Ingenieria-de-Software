package com.uce.metricservice.data.repository;

import com.uce.metricservice.data.entities.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends MongoRepository<Metric, String> {
    // Buscar métricas por userId
    List<Metric> findByUserId(String userId);
    
    // Buscar métricas por userId y ejercicio
    List<Metric> findByUserIdAndExercise(String userId, String exercise);
    
    // Buscar métricas por classId
    List<Metric> findByClassId(String classId);
}
