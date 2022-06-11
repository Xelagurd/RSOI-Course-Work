package com.example.rsoi_course_work.rental_service;

import com.example.rsoi_course_work.rental_service.exception.ErrorResponse;
import com.example.rsoi_course_work.rental_service.model.PairOfScooterUidAndPaymentUid;
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

    public ResponseEntity<List<Rental>> getUserRentals(String username) {
        return new ResponseEntity<>(new ArrayList<>(rentalRepository.findByUsername(username)), HttpStatus.OK);
    }

    public ResponseEntity<Rental> getUserRental(String username, UUID rentalUid) {
        Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    public ResponseEntity<PairOfScooterUidAndPaymentUid> cancelUserRental(String username, UUID rentalUid) {
        Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        rental.setStatus(RentalStatus.CANCELED);
        rentalRepository.save(rental);

        return new ResponseEntity<>(new PairOfScooterUidAndPaymentUid(rental.getScooter_uid(), rental.getPayment_uid()), HttpStatus.OK);
    }

    public ResponseEntity<UUID> finishUserRental(String username, UUID rentalUid) {
        Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid)
                .orElseThrow(() -> new ErrorResponse("Not found user`s rental for UID"));

        rental.setStatus(RentalStatus.FINISHED);
        rentalRepository.save(rental);

        return new ResponseEntity<>(rental.getScooter_uid(), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> createRental(Rental rental) {
        rentalRepository.save(new Rental(rental.getRental_uid(), rental.getUsername(), rental.getPayment_uid(),
                rental.getScooter_uid(), rental.getDate_from(), rental.getDate_to(), rental.getStatus()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}