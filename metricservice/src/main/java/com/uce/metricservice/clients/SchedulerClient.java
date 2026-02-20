package com.uce.metricservice.clients;

import com.uce.metricservice.data.entities.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicación con scheduler-service.
 * Usa Eureka para descubrimiento de servicios (sin URL hardcodeada).
 */
@FeignClient(name = "SCHEDULER-SERVICE")
public interface SchedulerClient {

    /**
     * Obtiene el último booking del usuario para extraer el classId.
     * El scheduler-service retorna solo el último booking creado.
     */
    @GetMapping("/api/v1/schedule/bookings/user/{userId}")
    BookingDTO getLastBookingByUser(@PathVariable("userId") String userId);
}