package com.parking.ticketing.parking_lot.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.parking.ticketing.parking_lot.bean.ParkingLot;
import com.parking.ticketing.parking_lot.bean.Vehicle;
import com.parking.ticketing.parking_lot.exception.InvalidParkingException;
import com.parking.ticketing.parking_lot.exception.InvalidVehicleException;
import com.parking.ticketing.parking_lot.service.ParkingService;

public class ParkingServiceImplTest {

	private ParkingService parkingService = new ParkingServiceImpl();
	
	@Test
	public void testT1_itShouldGiveSuccessResponse_createParkingLot() {
		//given
		int parkingCapacity = 5;
		
		//when
		ParkingLot parkingLot = parkingService.createParkingLot(parkingCapacity);
		
		//then
		assertNotNull(parkingLot);
		assertEquals(parkingCapacity, parkingLot.getParkedVehicle().size());
	}
	
	@Test(expected = InvalidParkingException.class)
	public void testT2_itShouldThrowExceptionWhenParkingCreatedWithNoCapacity_createParkingLot() {
		//given
		Integer parkingCapacity = null;
				
		//when
		parkingService.createParkingLot(parkingCapacity);
		
		//then
	}
	
	@Test(expected = InvalidParkingException.class)
	public void testT3_itShouldThrowExceptionWhenParkingCreatedWithCapacityLessThanAllowed_createParkingLot() {
		//given
		Integer parkingCapacity = 3;
				
		//when
		parkingService.createParkingLot(parkingCapacity);
		
		//then
	}
	
	@Test
	public void testT4_itShouldGiveSuccessWithProperRegNumber_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		String registrationNumber = "MH-12-BY-1245";
		Integer expectedSlotNumber = 1;
		
		//when
		Vehicle vehicle = parkingService.parkVehicle(parkingLot, registrationNumber);
		
		//then
		assertNotNull(vehicle);
		assertEquals(expectedSlotNumber, vehicle.getSlotNumber());
	}
	
	@Test
	public void testT5_itShouldGiveSuccessWithThreeDigitRegNumber_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		String registrationNumber = "MH-12-B-124";
		Integer expectedSlotNumber = 1;
		
		//when
		Vehicle vehicle = parkingService.parkVehicle(parkingLot, registrationNumber);
		
		//then
		assertNotNull(vehicle);
		assertEquals(expectedSlotNumber, vehicle.getSlotNumber());
	}
	
	/**
	 * This expection will be thrown when 6th vehicle is requested to park but capacity of
	 * parking lot is 5
	 */
	@Test(expected = InvalidParkingException.class)
	public void testT6_itShouldThrowParkingFullException_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1824");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1825");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1826");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1827");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1828");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1829");
		
		//then
	}
	
	@Test(expected = InvalidParkingException.class)
	public void testT7_itShouldThrowParkingFullException_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1824");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1825");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1826");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1827");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1828");
		parkingService.parkVehicle(parkingLot, "MH-12-BY-1829");
		
		//then
	}
	
	@Test(expected = InvalidVehicleException.class)
	public void testT8_itShouldThrowBlankRegNumberExcpetion_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, null);
		
		//then
	}
	
	@Test(expected = InvalidVehicleException.class)
	public void testT9_itShouldThrowInvalidRegistrationNumberExcpetion_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-45-1824");
		
		//then
	}
	
	@Test(expected = InvalidVehicleException.class)
	public void testT10_itShouldThrowVehicleAlreadyParkedException_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1824");
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1824");
		
		//then
	}
	
	@Test
	public void testT11_itShouldGiveProperNearestSlotNumber_parkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		Integer expectedSlotOne = 1;
		Integer expectedSlotTwo = 2;
		
		//when
		Vehicle v1 = parkingService.parkVehicle(parkingLot, "MH-45-BY-1824");
		Vehicle v2 = parkingService.parkVehicle(parkingLot, "MH-45-BY-1825");
		
		//then
		assertEquals(expectedSlotOne, v1.getSlotNumber());
		assertEquals(expectedSlotTwo, v2.getSlotNumber());
	}
	
	@Test
	public void testT12_itShouldGiveSuccessOnUnparkVehicle_unparkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		int hoursParked = 2;
		Double expectedCharges = 10D;
		String registrationNo = "MH-45-BY-1824";
		
		//when
		Vehicle v1 = parkingService.parkVehicle(parkingLot, registrationNo);
		parkingService.unparkVehicle(parkingLot, registrationNo, hoursParked);
		
		//then
		assertNotNull(v1);
		assertEquals(expectedCharges, v1.getCharges());
	}
	
	@Test
	public void testT13_itShouldGiveSuccessOnUnparkVehicleWithAdditionalHours_unparkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		int hoursParked = 4;
		Double expectedCharges = 30D;
		String registrationNo = "MH-45-BY-1824";
		
		//when
		Vehicle v1 = parkingService.parkVehicle(parkingLot, registrationNo);
		parkingService.unparkVehicle(parkingLot, registrationNo, hoursParked);
		
		//then
		assertNotNull(v1);
		assertEquals(expectedCharges, v1.getCharges());
	}
	
	@Test(expected = InvalidVehicleException.class)
	public void testT14_itShouldThrowVehicleNotParkedExceptionOnUnPark_unparkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		int hoursParked = 4;
		String registrationNo = "MH-45-BY-1824";
		
		//when
		parkingService.unparkVehicle(parkingLot, registrationNo, hoursParked);
		
		//then
	}
	
	@Test
	public void testT15_itShouldUnParkVehicleAndUpdateParkingLot_unparkVehicle() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		int hoursParked = 4;
		int expectedParkingLotSizeAfterUnpark = 1;
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1824");
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1825");
		
		parkingService.unparkVehicle(parkingLot, "MH-45-BY-1824", hoursParked);
		
		//then
		assertEquals(expectedParkingLotSizeAfterUnpark, calculateAvailableParkingLotSize(parkingLot));
	}
	
	@Test
	public void testT16_itShouldGiveSuccess_parkingStatus() {
		//given
		ParkingLot parkingLot = parkingService.createParkingLot(5);
		
		//when
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1824");
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1825");
		parkingService.parkVehicle(parkingLot, "MH-45-BY-1826");
		
		parkingService.unparkVehicle(parkingLot, "MH-45-BY-1824", 4);
		
		Map<Integer, Vehicle> status = parkingService.parkingStatus(parkingLot);
		
		//then
		assertNotNull(status);
		assertEquals(2, status.size());
		
	}
	
	@Test(expected = InvalidParkingException.class)
	public void testT17_itShouldThrowParkingNotCreatedException_parkingStatus() {
		//given
		ParkingLot parkingLot = null;
		
		//when
		
		parkingService.parkingStatus(parkingLot);
		
		//then
	}
	
	private int calculateAvailableParkingLotSize(ParkingLot parkingLot) {
		int count = 0;
		Map<Integer, Vehicle> park = parkingLot.getParkedVehicle();
		Iterator<Entry<Integer, Vehicle>> it = park.entrySet().iterator();
		
		while(it.hasNext()) {
			Vehicle v = it.next().getValue();
			if(null != v) {
				count++;
			}
		}
		return count;
	}
	
}
