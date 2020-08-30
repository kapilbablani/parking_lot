package com.parking.ticketing.parking_lot.service;

import java.util.Map;

import com.parking.ticketing.parking_lot.bean.ParkingLot;
import com.parking.ticketing.parking_lot.bean.Vehicle;

public interface ParkingService {
	
	ParkingLot createParkingLot(Integer capacity);
	
	Vehicle parkVehicle(ParkingLot parkingLot, String registrationNumber);
	
	Vehicle unparkVehicle(ParkingLot parkingLot, String registrationNumber, Integer hours);
	
	Map<Integer, Vehicle> parkingStatus(ParkingLot parkingLot);
	
}
