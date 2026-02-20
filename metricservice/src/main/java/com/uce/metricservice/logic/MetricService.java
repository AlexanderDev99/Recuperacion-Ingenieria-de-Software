package com.uce.metricservice.logic;

import com.uce.metricservice.clients.SchedulerClient;
import com.uce.metricservice.data.entities.Metric;
import com.uce.metricservice.data.entities.MetricsDTO;
import com.uce.metricservice.data.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;
    private final SchedulerClient schedulerClient; // Inyectamos el cliente HTTP

public Metric saveMetric(MetricsDTO dto) {
    // 1. COMENTA temporalmente la llamada real al Scheduler para evitar el Error 500
    /*
    BookingDTO lastBooking = schedulerClient.getLastBookingByUser(dto.getUserId());
    String classId = lastBooking.getClassId();
    */

    // 2. USA un valor simulado para poder probar tu base de datos
    String classId = "CLASE-MOCK-PRUEBA"; 

    // 3. El resto del código sigue igual
    Metric metric = new Metric();
    metric.setUserId(dto.getUserId());
    metric.setExercise(dto.getExercise());
    metric.setValue(dto.getValue());
    metric.setUnit(dto.getUnit());
    metric.setClassId(classId); // Usamos el simulado
    metric.setTimestamp(LocalDateTime.now());

    return metricRepository.save(metric);
}

public List<Metric> getMetricsByUserId(String userId) {
        return metricRepository.findByUserId(userId);
    }
}