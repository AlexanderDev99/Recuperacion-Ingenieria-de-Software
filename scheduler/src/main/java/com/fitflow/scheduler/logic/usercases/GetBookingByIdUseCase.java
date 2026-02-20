package com.fitflow.scheduler.logic.usercases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitflow.scheduler.controllers.data.converters.EntityConverters;
import com.fitflow.scheduler.controllers.data.entities.BookingEntityUI;
import com.fitflow.scheduler.data.repository.BookingRepository;
import com.fitflow.scheduler.logic.validators.Result;


@Service
public class GetBookingByIdUseCase {

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Obtiene SOLO el último booking creado para un userId específico.
     * Requerimiento PDF: El endpoint GET /api/v1/schedule/bookings/user/{userId} 
     * debe devolver solo el último booking creado, no una lista completa.
     */
    public Result<BookingEntityUI> get(String userId) {
        try {
            var bookingOptional = bookingRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
            if (bookingOptional.isPresent()) {
                return Result.success(EntityConverters.bookingEntityDbToUI(bookingOptional.get()));
            } else {
                return Result.failure(new Exception("No se encontraron reservas para el usuario: " + userId));
            }
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

}
