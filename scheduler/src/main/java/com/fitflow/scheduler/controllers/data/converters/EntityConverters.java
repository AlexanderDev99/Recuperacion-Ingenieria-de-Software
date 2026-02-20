package com.fitflow.scheduler.controllers.data.converters;

import com.fitflow.scheduler.controllers.data.entities.BookingEntityUI;
import com.fitflow.scheduler.data.entities.db.BookingEntityDb;

public class EntityConverters {

    // Convierte de la Entidad de Base de Datos (MongoDB) a la interfaz de usuario (DTO/UI)
    public static BookingEntityUI bookingEntityDbToUI(BookingEntityDb db) {
        if (db == null) {
            return null;
        }

        return BookingEntityUI.builder()
                .userId(db.getUserId())       // Mapeo de ID de usuario [cite: 31]
                .classId(db.getClassId())     // Mapeo de ID de clase [cite: 34]
                .bookingDate(db.getBookingDate()) // Mapeo de fecha [cite: 36]
                .build();
    }

    // Convierte de la interfaz de usuario (DTO/UI) a la Entidad de Base de Datos
    public static BookingEntityDb bookingEntityUIToDb(BookingEntityUI ui) {
        if (ui == null) {
            return null;
        }

        return BookingEntityDb.builder()
                .userId(ui.getUserId())       // Mapeo de ID de usuario [cite: 32]
                .classId(ui.getClassId())     // Mapeo de ID de clase [cite: 34]
                .bookingDate(ui.getBookingDate()) // Mapeo de fecha futura [cite: 35]
                .build();
    }
}
