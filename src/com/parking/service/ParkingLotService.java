package com.parking.service;

import java.util.HashMap;
import java.util.Map;

import com.parking.entites.ParkingSpot;
import com.parking.entites.Ticket;
import com.parking.entites.Vehicle;
import com.parking.util.ParkingSpotSize;

public class ParkingLotService {
	private int SmallSlot;
	private int MediumSlot;
	private int LargeSlot;

	private Map<ParkingSpot, Ticket> parkingMap;

	public ParkingLotService(int SmallSlot, int MediumSlot, int LargeSlot) {
		parkingMap = new HashMap<>();

		for (int i = 1; i <= SmallSlot; i++) {
			parkingMap.put(new ParkingSpot(i, ParkingSpotSize.SMALL), null);
		}

		for (int i = 1; i <= MediumSlot; i++) {
			parkingMap.put(new ParkingSpot(i, ParkingSpotSize.MEDIUM), null);
		}

		for (int i = 1; i <= LargeSlot; i++) {
			parkingMap.put(new ParkingSpot(i, ParkingSpotSize.LARGE), null);
		}
	}

	public void saveTicketAndParkingSpot(ParkingSpot ps, Ticket t) {
		parkingMap.put(ps, t);
	}

	public boolean isEmpty() {
		for (ParkingSpot spot : parkingMap.keySet()) {
			if (!spot.isOccupied()) {
				return true;
			}
		}
		return false;
	}

	public boolean isFull() {
		for (ParkingSpot spot : parkingMap.keySet()) {
			if (!spot.isOccupied()) {
				return false;
			}
		}
		return true;
	}

	public ParkingSpot nearestSpot() {
		for (ParkingSpot spot : parkingMap.keySet()) {
			if (!spot.isOccupied()) {
				return spot;
			}
		}
		return null;
	}

	public Map<ParkingSpot, Ticket> getParkingMap() {
		return parkingMap;
	}

	public ParkingSpot parkVehicle(Vehicle vehicle) {
		for (ParkingSpot spot : parkingMap.keySet()) {
			if (!spot.isOccupied() && canVehicleFitInSpot(vehicle, spot)) {
				spot.parkVehicle(vehicle);
				return spot;
			}
		}
		return null; // No available spot
	}

	public boolean canVehicleFitInSpot(Vehicle vehicle, ParkingSpot spot) {
		switch (vehicle.getSize()) {
		case BIKE:
			return true;
		case CAR:
			return spot.getSize() != ParkingSpotSize.SMALL;
		case BUS:
			return spot.getSize() == ParkingSpotSize.LARGE;
		default:
			return false;
		}
	}

	public Vehicle retrieveVehicle(String ticketNumber) {
		for (Map.Entry<ParkingSpot, Ticket> entry : parkingMap.entrySet()) {
			Ticket ticket = entry.getValue();
			if (ticket != null && ticket.getTicketId().equalsIgnoreCase(ticketNumber)) {
				ParkingSpot spot = entry.getKey();
				Vehicle retrievedVehicle = spot.getParkedVehicle();
				spot.vacateSpot();
				parkingMap.put(spot, null);
				return retrievedVehicle;
			}
		}
		return null;
	}
}
