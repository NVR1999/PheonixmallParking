package com.parking.entites;

public class Ticket {
    private String ticketId;
    private String vehicleNo;

    public Ticket(String ticketId, String vehicleNo) {
        this.ticketId = ticketId;
        this.vehicleNo = vehicleNo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }
}