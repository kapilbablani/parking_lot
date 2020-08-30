package com.parking.ticketing.parking_lot.bean;

import static com.parking.ticketing.parking_lot.utility.PropertyUtil.getDefaultParkingCharges;

public class Vehicle {
	private String registrationNo;
	private String ownerName;
	private VehicleType vehicleType;
	private Integer slotNumber;
	private Double charges;
	
	public Vehicle () {
	}
	
	public Vehicle(String registrationNo, String ownerName, VehicleType vehicleType, Integer slotNumber) {
		super();
		this.registrationNo = registrationNo;
		this.ownerName = ownerName;
		this.vehicleType = vehicleType;
		this.slotNumber = slotNumber;
		this.charges = getDefaultParkingCharges();
	}
	
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public Integer getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}

	public Double getCharges() {
		return charges;
	}
	public void setCharges(Double charges) {
		this.charges = charges;
	}

	@Override
	public String toString() {
		return "Vehicle [registrationNo=" + registrationNo + ", ownerName="
				+ ownerName + ", vehicleType=" + vehicleType + ", slotNumber="
				+ slotNumber + ", charges=" + charges + "]";
	}

}
