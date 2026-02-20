package com.fitflow.scheduler.logic.usercases;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitflow.scheduler.controllers.data.converters.EntityConverters;
import com.fitflow.scheduler.controllers.data.entities.BookingEntityUI;
import com.fitflow.scheduler.data.entities.Status;
import com.fitflow.scheduler.data.entities.db.BookingEntityDb;
import com.fitflow.scheduler.data.repository.BookingRepository;
import com.fitflow.scheduler.logic.network.BookingMembershipInterface;
import com.fitflow.scheduler.logic.validators.Result;


@Service
public class RegisterBookingUseCase {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingMembershipInterface bookingMembershipInterface;

    public Result<BookingEntityUI> register(String userId, String classId, LocalDateTime bookingDate ,Status status) {
        try {         
            Boolean isActive = bookingMembershipInterface.isActive(userId);
            if(isActive == null || !isActive) {
                return Result.failure(new Exception("User does not have an active membership"));
            }

            BookingEntityDb bookingBuilder = BookingEntityDb.builder()
                    .userId(userId)
                    .classId(classId)
                    .bookingDate(bookingDate)
                    .status(status)
                    .createdAt(LocalDateTime.now()) 
                    .build();
            
            var bookSaved = bookingRepository.save(bookingBuilder);
            return Result.success(EntityConverters.bookingEntityDbToUI(bookSaved));

        } catch (Exception e) {
            return Result.failure(e);
        }
    }

}
