package com.fitflow.scheduler.logic.usercases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitflow.scheduler.controllers.data.converters.EntityConverters;
import com.fitflow.scheduler.controllers.data.entities.BookingEntityUI;
import com.fitflow.scheduler.data.entities.db.BookingEntityDb;
import com.fitflow.scheduler.data.repository.BookingRepository;
import com.fitflow.scheduler.logic.validators.Result;



@Service
public class GetAllBookingsUseCase {

    @Autowired
    private BookingRepository bookingRepository;

    public Result<List<BookingEntityUI>> getAll() {
        List<BookingEntityUI> usersUI = new ArrayList<BookingEntityUI>();
        try {
            List<BookingEntityDb> users = bookingRepository.findAll();
            users.forEach(it -> usersUI.add(EntityConverters.bookingEntityDbToUI(it)));
            return Result.success(usersUI);
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

}
