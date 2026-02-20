package com.fitflow.scheduler.data.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitflow.scheduler.data.entities.db.BookingEntityDb;

import java.util.Optional;


public interface BookingRepository extends MongoRepository<BookingEntityDb, String> {

    /**
     * Retorna solo el último booking creado para un userId específico.
     * Requerimiento: GET /api/v1/schedule/bookings/user/{userId} debe devolver solo el último booking.
     */
    Optional<BookingEntityDb> findTopByUserIdOrderByCreatedAtDesc(String userId);

}
