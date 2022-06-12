package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.exception.ErrorResponse;
import com.example.rsoi_course_work.rental_service.model.CanceledRentalResponse;
import com.example.rsoi_course_work.rental_service.model.FinishedRentalResponse;
import com.example.rsoi_course_work.rental_service.model.Rental;
import com.example.rsoi_course_work.rental_service.model.RentalStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public ResponseEntity<List<Rental>> getUserRentals(UUID userUid) {
        return new ResponseEntity<>(new ArrayList<>(rentalRepository.findByUser_uid(userUid)), HttpStatus.OK);
    }

    public ResponseEntity<List<Rental>> getLocatedScooterRentals(UUID locatedScooterUid) {
        return new ResponseEntity<>(new ArrayList<>(rentalRepository.findByLocated_scooter_uid(locatedScooterUid)), HttpStatus.OK);
    }

    public ResponseEntity<Rental> getUserRental(UUID userUid, UUID rentalUid) {
        Rental rental = rentalRepository.findByUser_uidAndRental_uid(userUid, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    public ResponseEntity<CanceledRentalResponse> cancelUserRental(UUID userUid, UUID rentalUid) {
        Rental rental = rentalRepository.findByUser_uidAndRental_uid(userUid, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        rental.setStatus(RentalStatus.CANCELED);
        rentalRepository.save(rental);

        return new ResponseEntity<>(new CanceledRentalResponse(rental.getLocated_scooter_uid(),
                rental.getPayment_uid()), HttpStatus.OK);
    }

    public ResponseEntity<FinishedRentalResponse> finishUserRental(UUID userUid, UUID rentalUid) {
        Rental rental = rentalRepository.findByUser_uidAndRental_uid(userUid, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        rental.setStatus(RentalStatus.FINISHED);
        rentalRepository.save(rental);

        return new ResponseEntity<>(new FinishedRentalResponse(rental.getLocated_scooter_uid(),
                rental.getReturn_to()), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createRental(Rental rental) {
        rentalRepository.save(new Rental(rental.getRental_uid(), rental.getUser_uid(), rental.getLocated_scooter_uid(),
                rental.getPayment_uid(), rental.getTaken_from(), rental.getReturn_to(), rental.getDate_from(),
                rental.getDate_to(), rental.getStatus()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}