package com.parking.ticketing.parking_lot.utility;

public class MessageUtil {
	
	private MessageUtil() {
	}
	
	public static final String DEFAULT_PARKING_NAME = "MALL PARKING CO.IN";
	
	public static final String INCORRECT_BLANK_PARKING_CAPACITY = "Parking Lot capacity cannot be blank";
	public static final String INCORRECT_MAX_PARKING_CAPACITY = "Parking Lot capacity cannot be more than than "+PropertyUtil.MAX_PARKING_CAPACITY;
	public static final String INCORRECT_MIN_PARKING_CAPACITY = "Parking Lot capacity cannot be less than "+PropertyUtil.MIN_PARKING_CAPACITY;
	public static final String PARKING_FULL = "Sorry, parking lot is full";
	
	public static final String BLANK_VEHICLE_DETAILS = "Vehicle Details are mandatory"; 
	public static final String BLANK_REGISTRATION_NO = "Registration number cannot be blank";
	public static final String INVALID_REGISTRATION_NO = "Invalid Registration number provided";
	public static final String VEHICLE_ALREADY_PARKED = "This vehicle is already parked";
	public static final String VEHICLE_NOT_PARKED = "Registration number %s not found";
	public static final String PARKING_NOT_CREATED = "Parking does not exist";
	
	public static final String INVALID_INPUT = "Invalid Input";
	public static final String FILE_NOT_FOUND = "File not found in the path specified.";
	public static final String PARSE_ERROR = "Parse Error due to invalid command inputs";
	public static final String SOMETHING_WENT_WRONG = "Something went wrong";
	
	public static final String CREATED_PARKING_SLOT = "Created parking lot with %s slots";
	public static final String ALLOTED_SLOT = "Allocated slot number: ";
	public static final String UNPARK_SLOT_MESSAGE = "Registration number %s with Slot Number %d is free with Charge %f";
	public static final String PARK_STATUS = "Slot No.    Registration No.";
}
