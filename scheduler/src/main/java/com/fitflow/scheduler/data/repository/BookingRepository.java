package com.fitflow.scheduler.data.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitflow.scheduler.data.entities.db.BookingEntityDb;


public interface BookingRepository extends MongoRepository<BookingEntityDb, String> {

        

}
