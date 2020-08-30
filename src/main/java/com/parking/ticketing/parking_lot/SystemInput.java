package com.parking.ticketing.parking_lot;

import static com.parking.ticketing.parking_lot.utility.MessageUtil.ALLOTED_SLOT;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.CREATED_PARKING_SLOT;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.FILE_NOT_FOUND;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.INVALID_INPUT;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.PARK_STATUS;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.PARSE_ERROR;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.SOMETHING_WENT_WRONG;
import static com.parking.ticketing.parking_lot.utility.MessageUtil.UNPARK_SLOT_MESSAGE;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.CMD_CREATE_PARKING;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.CMD_PARK_VEHICLE;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.CMD_STATUS;
import static com.parking.ticketing.parking_lot.utility.PropertyUtil.CMD_UNPARK_VEHICLE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.parking.ticketing.parking_lot.bean.ParkingLot;
import com.parking.ticketing.parking_lot.bean.Vehicle;
import com.parking.ticketing.parking_lot.exception.InvalidParkingException;
import com.parking.ticketing.parking_lot.exception.InvalidVehicleException;
import com.parking.ticketing.parking_lot.service.ParkingService;
import com.parking.ticketing.parking_lot.service.impl.ParkingServiceImpl;

public class SystemInput {
	
	private ParkingService parkingService = new ParkingServiceImpl();
	private ParkingLot parkingLot = null;
	
	public void parseFileInput(String filePath) {
        // Assuming input to be a valid file path.
        File inputFile = new File(filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
            	parseTextInput(line.trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND);
            e.printStackTrace();
        } catch (IOException ex) {
        	System.out.println(INVALID_INPUT);
            ex.printStackTrace();
        } catch(NumberFormatException e) {
        	System.out.println(PARSE_ERROR);
		} catch (Exception e) {
        	System.out.println(SOMETHING_WENT_WRONG);
        	e.printStackTrace();
        }
    }

	private void parseTextInput(String line) {
		String[] inputs = line.split(" ");
		try {
			if(inputs.length == 1 && inputs[0].equalsIgnoreCase(CMD_STATUS)) {
				invokeParkingStatus();
			} else if(inputs.length == 2) {
				if(inputs[0].equalsIgnoreCase(CMD_CREATE_PARKING)) {
					invokeCreateParkingLot(inputs[1]);
				} else if(inputs[0].equalsIgnoreCase(CMD_PARK_VEHICLE)) {
					invokeParkingVehicle(inputs[1]);
				}
			} else if(inputs.length == 3 && inputs[0].equalsIgnoreCase(CMD_UNPARK_VEHICLE)) {
				invokeUnparkVehicle(inputs[1], inputs[2]);
			} else {
			
			}
		} catch (InvalidParkingException | InvalidVehicleException e) {
			System.out.println(e.getMessage());
		}
	}

	private void invokeUnparkVehicle(String registrationNumber, String hours) {
		Vehicle vehicle = parkingService.unparkVehicle(parkingLot, registrationNumber, Integer.parseInt(hours));
		System.out.println(String.format(UNPARK_SLOT_MESSAGE, vehicle.getRegistrationNo(), vehicle.getSlotNumber(), vehicle.getCharges()));
	}

	private void invokeCreateParkingLot(String parkingCapacity) {
		parkingLot = parkingService.createParkingLot(Integer.parseInt(parkingCapacity));
		System.out.println(String.format(CREATED_PARKING_SLOT, parkingCapacity));
	}

	private void invokeParkingStatus() {
		Map<Integer, Vehicle> parkedVehicle = parkingService.parkingStatus(parkingLot);
		System.out.println(PARK_STATUS);
		
		Set<Integer> slots = parkingLot.getParkedVehicle().keySet();
		for(Integer slot : slots) {
			if(null != parkedVehicle.get(slot))
				System.out.println(slot + "\t" + parkedVehicle.get(slot).getRegistrationNo());
		}
	}
	
	private void invokeParkingVehicle(String registrationNumber) {
		Vehicle vehicle = parkingService.parkVehicle(parkingLot, registrationNumber);
		System.out.println(ALLOTED_SLOT + vehicle.getSlotNumber());
	}
	
}
