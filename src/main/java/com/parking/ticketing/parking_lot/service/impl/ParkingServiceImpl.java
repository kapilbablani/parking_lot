package com.parking.ticketing.parking_lot.service.impl;

import static com.parking.ticketing.parking_lot.utility.MessageUtil.BLANK_REGISTRATION_NO;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.DEFAULT_PARKING_NAME;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.INCORRECT_BLANK_PARKING_CAPACITY;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.INCORRECT_MAX_PARKING_CAPACITY;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.INCORRECT_MIN_PARKING_CAPACITY;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.INVALID_REGISTRATION_NO;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.PARKING_FULL;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.PARKING_NOT_CREATED;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.VEHICLE_ALREADY_PARKED;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.VEHICLE_NOT_PARKED;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.MAX_PARKING_CAPACITY;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.MIN_PARKING_CAPACITY;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.getAdditionalParkingChargePerHour;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.getDefaultParkingCharges;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.getDefaultParkingHours;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.getVehicleRegistrationRegularExp;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.parking.ticketing.parking_lot.bean.ParkingLot;
import com.parking.ticketing.parking_lot.bean.Vehicle;
import com.parking.ticketing.parking_lot.bean.VehicleType;
import com.parking.ticketing.parking_lot.exception.InvalidParkingException;
import com.parking.ticketing.parking_lot.exception.InvalidVehicleException;
import com.parking.ticketing.parking_lot.factory.ParkingLotBuilder;
import com.parking.ticketing.parking_lot.service.ParkingService;

public class ParkingServiceImpl implements ParkingService {

	@Override
	public ParkingLot createParkingLot(Integer capacity) {
		validateParkingCapacity(capacity);
		
		return new ParkingLotBuilder()
				.setId(1)
				.setName(DEFAULT_PARKING_NAME)
				.setCapacity(capacity)
				.setParkedVehicle(createParkingLotMap(capacity))
				.build();
	}
	
	@Override
	public Vehicle parkVehicle(ParkingLot parkingLot, String registrationNumber) {
		if(null == parkingLot)
			throw new InvalidParkingException(PARKING_NOT_CREATED);
		validateParkingWhileParkingRequest(parkingLot);
		validateCarBeforeParking(parkingLot, registrationNumber);
		
		Map<Integer, Vehicle> parks = parkingLot.getParkedVehicle();
		Set<Integer> slots = parkingLot.getParkedVehicle().keySet();
		
		Vehicle car = null;
		for(Integer slot : slots) {
			if(null == parks.get(slot)) {
				car = new Vehicle(registrationNumber, "Owner", VehicleType.CAR, slot);
				parks.put(slot, car);
				break;
			}
		}
		parkingLot.setParkedVehicle(parks);
		return car;
	}


	@Override
	public Vehicle unparkVehicle(ParkingLot parkingLot, String registrationNumber, Integer hours) {
		if(null == parkingLot)
			throw new InvalidParkingException(PARKING_NOT_CREATED);
		
		boolean isVehicleParked = isSameVehicleAlreadyParked(parkingLot, registrationNumber);
		if(!isVehicleParked)
			throw new InvalidVehicleException(String.format(VEHICLE_NOT_PARKED, registrationNumber));
		
		Vehicle parkedVehicle = getVehicleDetails(parkingLot, registrationNumber);
		if(null == parkedVehicle)
			throw new InvalidVehicleException(String.format(VEHICLE_NOT_PARKED, registrationNumber));
		
		Double charges = calculateParkingCharges(hours);
		parkedVehicle.setCharges(charges);
		
		parkingLot.getParkedVehicle().put(parkedVehicle.getSlotNumber(), null);
		return parkedVehicle;
	}
	
	@Override
	public Map<Integer, Vehicle> parkingStatus(ParkingLot parkingLot) {
		if(null == parkingLot)
			throw new InvalidParkingException(PARKING_NOT_CREATED);
		Map<Integer, Vehicle> vehicles = parkingLot.getParkedVehicle();
		return vehicles.entrySet()
				.stream()
				.filter(v -> v.getValue() != null)
				.collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));
		
	}
	
	private Double calculateParkingCharges(Integer hours) {
		if(hours > getDefaultParkingHours())
			return getDefaultParkingCharges() + (getAdditionalParkingChargePerHour() * (hours- getDefaultParkingHours()));
		return getDefaultParkingCharges();
	}

	private Map<Integer, Vehicle> createParkingLotMap(Integer capacity) {
		Map<Integer, Vehicle> map = new TreeMap<>();
		for(int i=1;i<=capacity;i++) {
			map.put(i, null);
		}
		return map;
	}
	
	private void validateParkingCapacity(Integer capacity) {
		if(null == capacity)
			throw new InvalidParkingException(INCORRECT_BLANK_PARKING_CAPACITY);
		if(capacity < MIN_PARKING_CAPACITY)
			throw new InvalidParkingException(INCORRECT_MIN_PARKING_CAPACITY);
		if(capacity > MAX_PARKING_CAPACITY)
			throw new InvalidParkingException(INCORRECT_MAX_PARKING_CAPACITY);
	}
	
	private void validateParkingWhileParkingRequest(ParkingLot parkingLot) {
		boolean isParkingFull = true;
		
		Map<Integer, Vehicle> park = parkingLot.getParkedVehicle();
		Iterator<Entry<Integer, Vehicle>> it = park.entrySet().iterator();
		
		while(it.hasNext()) {
			Vehicle v = it.next().getValue();
			if(null == v) {
				isParkingFull = false;
				break;
			}
		}
		if(isParkingFull)
			throw new InvalidParkingException(PARKING_FULL);
	}
	
	private void validateCarBeforeParking(ParkingLot parkingLot, String registrationNumber) {
		if(null == registrationNumber)
			throw new InvalidVehicleException(BLANK_REGISTRATION_NO);
		if(! isValidRegistrationNumber(registrationNumber))
			throw new InvalidVehicleException(INVALID_REGISTRATION_NO);
		if(isSameVehicleAlreadyParked(parkingLot, registrationNumber))
			throw new InvalidVehicleException(VEHICLE_ALREADY_PARKED);
	}

	private boolean isSameVehicleAlreadyParked(ParkingLot parkingLot, String registrationNumber) {
		Map<Integer, Vehicle> park = parkingLot.getParkedVehicle();
		Iterator<Entry<Integer, Vehicle>> it = park.entrySet().iterator();
		
		while(it.hasNext()) {
			Vehicle v = it.next().getValue();
			if(null != v && v.getRegistrationNo().equalsIgnoreCase(registrationNumber))
				return true;
		}
		return false;
	}

	private boolean isValidRegistrationNumber(String registrationNumber) {
		return Pattern.matches(getVehicleRegistrationRegularExp(), registrationNumber);
	}
	
	private Vehicle getVehicleDetails(ParkingLot parkingLot, String registrationNumber) {
		Map<Integer, Vehicle> park = parkingLot.getParkedVehicle();
		Iterator<Entry<Integer, Vehicle>> it = park.entrySet().iterator();
		
		while(it.hasNext()) {
			Vehicle v = it.next().getValue();
			if(null != v && registrationNumber.equalsIgnoreCase(v.getRegistrationNo())) {
				return v;
			}
		}
		return null;
	}
	
}
