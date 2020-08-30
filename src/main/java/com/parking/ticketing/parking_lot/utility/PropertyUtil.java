package com.parking.ticketing.parking_lot.utility;

public class PropertyUtil {
	
	private PropertyUtil() {
	}
	
	private static final Double DEFAULT_PARKING_CHARGE = 10D;
	private static final Double ADDITIONAL_PARKING_CHARGE_PER_HOUR = 10D;
	private static final Integer DEFAULT_PARKING_HOUR = 2;
	private static final String VEHICLE_REG_REGEX = "^[A-Z]{2}[-][0-9]{2}[-][A-Z]{1,2}[-][0-9]{3,4}$";
	
	public static final Integer MIN_PARKING_CAPACITY = 5;
	public static final Integer MAX_PARKING_CAPACITY = 100;
	
	public static final String CMD_CREATE_PARKING = "create_parking_lot";
	public static final String CMD_PARK_VEHICLE = "park";
	public static final String CMD_UNPARK_VEHICLE = "leave";
	public static final String CMD_STATUS = "status";
	
	public static Double getDefaultParkingCharges() {
		return DEFAULT_PARKING_CHARGE;
	}
	
	public static String getVehicleRegistrationRegularExp() {
		return VEHICLE_REG_REGEX;
	}
	
	public static Integer getDefaultParkingHours() {
		return DEFAULT_PARKING_HOUR;
	}
	
	public static Double getAdditionalParkingChargePerHour() {
		return ADDITIONAL_PARKING_CHARGE_PER_HOUR;
	}
}
