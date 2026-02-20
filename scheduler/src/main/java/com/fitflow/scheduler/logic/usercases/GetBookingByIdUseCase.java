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

    public Result<BookingEntityUI> get(String id) {
        try {
            var bookingOptional = bookingRepository.findById(id);
            if (bookingOptional.isPresent()) {
                return Result.success(EntityConverters.bookingEntityDbToUI(bookingOptional.get()));
            } else {
                return Result.failure(new Exception("Usuario con ID " + id + " no encontrado"));
            }
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

}
