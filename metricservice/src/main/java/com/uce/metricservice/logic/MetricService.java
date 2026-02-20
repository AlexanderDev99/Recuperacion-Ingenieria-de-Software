package com.uce.metricservice.logic;

import com.uce.metricservice.clients.SchedulerClient;
import com.uce.metricservice.data.entities.BookingDTO;
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
    private final SchedulerClient schedulerClient;

    /**
     * Guarda una métrica consultando el classId al scheduler-service.
     * Requerimiento PDF: El classId NO debe recibirse desde el endpoint,
     * el microservicio de métricas debe consultar internamente al scheduler.
     * Se verifica que la clase sea vigente (bookingDate >= fecha actual).
     */
    public Metric saveMetric(MetricsDTO dto) {
        // Consultamos al scheduler para obtener el último booking del usuario
        BookingDTO lastBooking = schedulerClient.getLastBookingByUser(dto.getUserId());
        
        // Verificar si la clase sigue siendo vigente
        LocalDateTime now = LocalDateTime.now();
        if (lastBooking.getBookingDate() == null || lastBooking.getBookingDate().isBefore(now)) {
            throw new IllegalStateException("La clase no es vigente. La fecha de la clase (" 
                + lastBooking.getBookingDate() + ") ya ha pasado.");
        }
        
        String classId = lastBooking.getClassId();

        Metric metric = new Metric();
        metric.setUserId(dto.getUserId());
        metric.setExercise(dto.getExercise());
        metric.setValue(dto.getValue());
        metric.setUnit(dto.getUnit());
        metric.setClassId(classId); // Obtenido del scheduler
        metric.setTimestamp(LocalDateTime.now());

        return metricRepository.save(metric);
    }

    public List<Metric> getMetricsByUserId(String userId) {
        return metricRepository.findByUserId(userId);
    }
}