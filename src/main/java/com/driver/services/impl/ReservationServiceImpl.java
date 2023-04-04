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
            }
        }
        
        if(origSpot==null) throw new Exception("Cannot make reservation");
        
        User user = userRepository3.findById(userId).get();
        
        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        reservation.setSpot(origSpot);
        origSpot.setOccupied(true);
        origSpot.getReservationList().add(reservation);
        user.getReservationList().add(reservation);
        userRepository3.save(user);
        spotRepository3.save(origSpot);
        //reservationRepository3.save(reservation);
        return reservation;
    }
}
