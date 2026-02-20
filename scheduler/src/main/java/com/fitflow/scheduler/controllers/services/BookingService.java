package com.fitflow.scheduler.controllers.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitflow.scheduler.controllers.rabbit.RabbitMessage;
import com.fitflow.scheduler.data.entities.Status;
import com.fitflow.scheduler.logic.usercases.GetAllBookingsUseCase;
import com.fitflow.scheduler.logic.usercases.GetBookingByIdUseCase;
import com.fitflow.scheduler.logic.usercases.RegisterBookingUseCase;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/schedule")
public class BookingService {

    @Autowired
    private RegisterBookingUseCase registerBooking;

    @Autowired
    private GetAllBookingsUseCase getAllBookings;

    @Autowired
    private GetBookingByIdUseCase getBookingById;

    @Autowired
    private RabbitMessage rabbitMessage;

    @GetMapping("/bookings/create")
        public ResponseEntity<?> register(
                @RequestParam("userId") String userId,
                @RequestParam("classId") String classId,
                @RequestParam("bookDate") String bookDate,
                @RequestParam("status") String status) {
        rabbitMessage.sendLog("/api/v1/schedule/bookings/create");

        LocalDateTime bookingDate = LocalDateTime.parse(bookDate);
        Status staus = Status.valueOf(status.toUpperCase());
        // Pasamos los parámetros directamente al Use Case
        return registerBooking.register(userId, classId,bookingDate , staus).fold(
                ResponseEntity::ok,
                ex -> ResponseEntity.badRequest().body(ex.getMessage()));
    }

    
    @GetMapping("bookings/all")
    public ResponseEntity<?> findAll() {
        rabbitMessage.sendLog("/api/v1/schedule/bookings/all");
        return getAllBookings.getAll().fold(
                ResponseEntity::ok,
                ex -> ResponseEntity.internalServerError().body(ex.getMessage()));
    }

    @GetMapping("bookings/user/{userId}")
    public ResponseEntity<?> findById(@PathVariable("userId") String userId) {
        rabbitMessage.sendLog("/api/v1/schedule/bookings/user/" + userId);
        return getBookingById.get(userId).fold(
                ResponseEntity::ok,
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    
}
