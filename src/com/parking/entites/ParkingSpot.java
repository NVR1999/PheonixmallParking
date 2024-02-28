package com.parking.entites;
import java.time.LocalDateTime;

import com.parking.util.ParkingSpotSize;

public class ParkingSpot {
    private int spotNumber;
    private ParkingSpotSize size;
    private boolean occupied;
    private Vehicle parkedVehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingSpot() {
    	
    }
    public ParkingSpot(int spotNumber, ParkingSpotSize size) {
        this.spotNumber = spotNumber;
        this.size = size;
        this.occupied = false;
        
        
    }
    
    
    public int getSpotNumber() {
		return spotNumber;
	}


	public void setSpotNumber(int spotNumber) {
		this.spotNumber = spotNumber;
	}


	public Vehicle getParkedVehicle() {
		return parkedVehicle;
	}


	public void setParkedVehicle(Vehicle parkedVehicle) {
		this.parkedVehicle = parkedVehicle;
	}


	public void setSize(ParkingSpotSize size) {
		this.size = size;
	}

	public boolean isOccupied() {
        return occupied;
    }

    public ParkingSpotSize getSize() {
        return size;
    }
    

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}


	public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.occupied = true;
        this.entryTime = LocalDateTime.now();
    }

    public void vacateSpot() {
        this.parkedVehicle = null;
        this.occupied = false;
    }
	public LocalDateTime getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}
	 public LocalDateTime getExitTime() {
	        return exitTime;
	    }
}
