package com.parking.ticketing.parking_lot.factory;

import java.util.HashMap;
import java.util.Map;

import com.parking.ticketing.parking_lot.bean.ParkingLot;
import com.parking.ticketing.parking_lot.bean.Vehicle;

public class ParkingLotBuilder {
	
	private Integer id;
	private String name;
	private Integer capacity;
	private Map<Integer, Vehicle> parkedVehicle;
	
	public ParkingLotBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	public ParkingLotBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public ParkingLotBuilder setCapacity(Integer capacity) {
		this.capacity = capacity;
		return this;
	}
	public ParkingLotBuilder setParkedVehicle(Map<Integer, Vehicle> parkedVehicle) {
		this.parkedVehicle = parkedVehicle;
		return this;
	}
	
	public ParkingLot build() {
		return new ParkingLot(id, name, capacity, parkedVehicle);
	}
	
	
}
