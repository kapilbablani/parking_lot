package com.parking.ticketing.parking_lot.bean;

import java.util.Map;

public class ParkingLot {
	
	private Integer id;
	private String name;
	private Integer capacity;
	private Map<Integer, Vehicle> parkedVehicle;
	
	public ParkingLot(Integer id, String name, Integer capacity, Map<Integer, Vehicle> parkedVehicle) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.parkedVehicle = parkedVehicle;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Map<Integer, Vehicle> getParkedVehicle() {
		return parkedVehicle;
	}
	public void setParkedVehicle(Map<Integer, Vehicle> parkedVehicle) {
		this.parkedVehicle = parkedVehicle;
	}
	
}
