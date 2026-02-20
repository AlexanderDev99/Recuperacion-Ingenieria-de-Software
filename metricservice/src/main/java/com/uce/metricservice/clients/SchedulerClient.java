package com.uce.metricservice.clients;

import com.uce.metricservice.data.entities.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// El "name" es el nombre del servicio en Eureka.
// El "url" es un truco temporal para que te funcione AHORA MISMO a nivel local 
// mientras Eureka está apagado (asumiendo que el Scheduler correrá en el 8082).
@FeignClient(name = "scheduler-service", url = "http://localhost:8082")
public interface SchedulerClient {

    // Este es el endpoint exacto que el Equipo 2 debe exponer [cite: 30]
    @GetMapping("/api/v1/schedule/bookings/user/{userId}")
    BookingDTO getLastBookingByUser(@PathVariable("userId") String userId);
}