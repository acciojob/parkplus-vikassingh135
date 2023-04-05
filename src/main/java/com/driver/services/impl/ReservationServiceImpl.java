package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
   //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
<<<<<<< HEAD
        Reservation reservation = new Reservation();
        if(userRepository3.findById(userId) == null){
            throw new Exception("Cannot make reservation");
        }
        User user = userRepository3.findById(userId).get();
        if(parkingLotRepository3.findById(parkingLotId) == null){
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        Spot optimalSpot = null;
        int optimalPrice = Integer.MAX_VALUE;
        for(Spot spot: spotList){
            if(!spot.getOccupied()){
                if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
                    if(numberOfWheels <= 2){
                        if(optimalPrice > spot.getPricePerHour()){
                            optimalPrice = spot.getPricePerHour();
                            optimalSpot = spot;
                        }
                    }
                } else if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
                    if(numberOfWheels <= 4){
                        if(optimalPrice > spot.getPricePerHour()){
                            optimalPrice = spot.getPricePerHour();
                            optimalSpot = spot;
                        }
                    }
                } else{
                    if(optimalPrice > spot.getPricePerHour()){
                        optimalPrice = spot.getPricePerHour();
                        optimalSpot = spot;
                    }
                }
=======
        if(!userRepository3.existsById(userId)) throw new Exception("Cannot make reservation");
        if(!parkingLotRepository3.existsById(parkingLotId)) throw new Exception("Cannot make reservation");
        List<Spot> spots = parkingLotRepository3.findById(parkingLotId).get().getSpotList();
        Spot origSpot = null;
        for(Spot s : spots) {
            //if(s.getOccupied()) continue;
            if(s.getSpotType().equals(SpotType.TWO_WHEELER)) {
                if(numberOfWheels<=2) {
                    if(origSpot==null || origSpot.getPricePerHour()>s.getPricePerHour()) origSpot = s;
                }
            } 
            if(s.getSpotType().equals(SpotType.FOUR_WHEELER)) {
                if(numberOfWheels<=4) {
                    if(origSpot==null || origSpot.getPricePerHour()>s.getPricePerHour()) origSpot = s;
                }
            }
            if(s.getSpotType().equals(SpotType.OTHERS)) {
                if(origSpot==null || origSpot.getPricePerHour()>s.getPricePerHour()) origSpot = s;
>>>>>>> 388d7d38c64c20fb93fa42788afd1f018624235e
            }
        }
        if(optimalSpot == null){
            throw new Exception("Cannot make reservation");
        }
        optimalSpot.setOccupied(true);
        reservation.setUser(user);
<<<<<<< HEAD
        reservation.setSpot(optimalSpot);
        reservation.setNumberOfHours(timeInHours);
        List<Reservation> reservations = user.getReservationList();
        List<Reservation> reservationList = optimalSpot.getReservationList();
        reservationList.add(reservation);
        reservations.add(reservation);
        user.setReservationList(reservationList);
        optimalSpot.setReservationList(reservationList);
        userRepository3.save(user);
        spotRepository3.save(optimalSpot);
=======
        reservation.setSpot(origSpot);
        origSpot.setOccupied(true);
        origSpot.getReservationList().add(reservation);
        user.getReservationList().add(reservation);
        userRepository3.save(user);
        spotRepository3.save(origSpot);
        //reservationRepository3.save(reservation);
>>>>>>> 388d7d38c64c20fb93fa42788afd1f018624235e
        return reservation;
    }
}
