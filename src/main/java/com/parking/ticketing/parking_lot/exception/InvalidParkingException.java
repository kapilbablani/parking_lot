package com.parking.ticketing.parking_lot.exception;

public class InvalidParkingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidParkingException(String message) {
		super(message);
	}

}
