package com.parking.main;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;
import com.parking.entites.ParkingSpot;
import com.parking.entites.Ticket;
import com.parking.entites.Vehicle;
import com.parking.service.ParkingLotService;
import com.parking.util.VehicleType;

public class ParkingSystem {
    public static void main(String[] args)throws Exception  {
        Scanner input = new Scanner(System.in);
        System.out.println("----->Welcome to the PHEONIX MALL Parking Lot!<-----");

        ParkingLotService parkingLot = new ParkingLotService(200, 250, 100);

        while (true) {
            System.out.println("\n1. Park a vehicle");
            System.out.println("2. Nearest spot for parking");
            System.out.println("3. Check status for parking");
            System.out.println("4. Retrieving Vehicle from Parking");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    parkVehicle(parkingLot, input);
                    break;
                case 2:
                    findSpotForValet(parkingLot,input);
                    break;
                case 3:
                    checkParkingLotStatus(parkingLot);
                    break;
                case 4:
                    retrieveVehicle(parkingLot, input);
                    break;
                case 5:
                    System.out.println("\n<-----Goodbye!,Have A Safe Journey----->");
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice. Please enter a valid option.");
            }
        }
    }

    private static void parkVehicle(ParkingLotService parkingLot, Scanner scanner) {
        System.out.println("\nChoose vehicle type: ");
        System.out.println("1. Bike");
        System.out.println("2. Car");
        System.out.println("3. Bus");
        System.out.print("\nEnter your choice: ");
        int typeChoice = scanner.nextInt();

        VehicleType type;
        switch (typeChoice) {
            case 1:
                type = VehicleType.BIKE;
                break;
            case 2:
                type = VehicleType.CAR;
                break;
            case 3:
                type = VehicleType.BUS;
                break;
            default:
                System.out.println("\nInvalid type choice.");
                return;
        }
        String ticketPrefix;
        int maxSpot;
        switch (type) {
            case BIKE:
                ticketPrefix = "S";
                maxSpot = 200;
                break;
            case CAR:
                ticketPrefix = "M";
                maxSpot = 250;
                break;
            case BUS:
                ticketPrefix = "L";
                maxSpot = 100;
                break;
            default:
                ticketPrefix = "";
                maxSpot = 0;
        }

        System.out.print("\nEnter vehicle license plate: ");
        String licensePlate = scanner.next();

        if (!isValidLicensePlateFormat(licensePlate)) {
            System.out.println("Invalid,Please enter a valid license plate.");
            return;
        }

        Vehicle vehicle = new Vehicle(licensePlate, type);
        ParkingSpot spot = parkingLot.parkVehicle(vehicle);

        if (spot != null) {
            Random random = new Random();
            int rand_ticket = random.nextInt(1000);
            int rand_spot = random.nextInt(maxSpot);
            Ticket ticket = new Ticket("" + rand_ticket, vehicle.getLicensePlate());
            ParkingSpot pSpot = new ParkingSpot();
            pSpot.setSpotNumber(rand_spot);
            pSpot.setParkedVehicle(vehicle);
            pSpot.setOccupied(true);

            System.out.println("\n------- Vehicle Parked Successfully --------------------");
            System.out.println("| Block: "+ticketPrefix+"-Block");
            System.out.println("| Slot No: " +  rand_spot);
            System.out.println("| Ticket No: " + ticket.getTicketId());
            System.out.println("| License No: " + ticket.getVehicleNo().toUpperCase());
            System.out.println("| Entry Time: " + LocalDateTime.now());
            System.out.println("---------------------------------------------------------");

            parkingLot.saveTicketAndParkingSpot(pSpot, ticket);
        } else {
            System.out.println("Parking lot is full. Unable to park the vehicle.");
        }
    }

    private static boolean isValidLicensePlateFormat(String licensePlate) {
        try {
            String regex = "^[A-Za-z]{2}\\d{2}[A-Za-z]{2}\\d{4}$";
            if (licensePlate.matches(regex)) {
                return true;
            } else {
                throw new Exception("Invalid license plate format.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void findSpotForValet(ParkingLotService parkingLot,Scanner scanner) {
    	System.out.println("\nChoose vehicle type: ");
        System.out.println("1. Bike");
        System.out.println("2. Car");
        System.out.println("3. Bus");
        System.out.print("\nEnter your choice: ");
    	int typeChoice = scanner.nextInt();
    	VehicleType type;
    	System.out.println("\nChoose vehicle type: ");
        System.out.println("1. Bike");
        System.out.println("2. Car");
        System.out.println("3. Bus");
        System.out.print("\nEnter your choice: ");
        switch (typeChoice) {
            case 1:
                type = VehicleType.BIKE;
                break;
            case 2:
                type = VehicleType.CAR;
                break;
            case 3:
                type = VehicleType.BUS;
                break;
            default:
                System.out.println("Invalid type choice.");
                return;
        }
        String ticketPrefix;
        int maxSpot;
        switch (type) {
            case BIKE:
                ticketPrefix = "S";
                maxSpot = 200;
                break;
            case CAR:
                ticketPrefix = "M";
                maxSpot = 250;
                break;
            case BUS:
                ticketPrefix = "L";
                maxSpot = 100;
                break;
            default:
                ticketPrefix = "";
                maxSpot = 0;
        }
        Random random = new Random();
        int rand_spot = random.nextInt(maxSpot);
        ParkingSpot spot = parkingLot.nearestSpot();
        if (spot != null) {
            System.out.println("Park a vehicle at spot " + ticketPrefix+"-Block "+rand_spot);
        } else {
            System.out.println("Parking lot is full. No spot available for Valet parking.");
        }
    }

    private static void checkParkingLotStatus(ParkingLotService parkingLot) {
        int occupiedSpots = 0;
        for (ParkingSpot spot : parkingLot.getParkingMap().keySet()) {
            if (spot.isOccupied()) {
                occupiedSpots++;
            }
        }

        if (occupiedSpots > 0) {
            System.out.println("Parking Lot is partially occupied.");
        } else if (parkingLot.isFull()) {
            System.out.println("Parking Lot is Full.");
        } else {
            System.out.println("Parking Lot is Empty.");
        }
    }
    private static void retrieveVehicle(ParkingLotService parkingLot, Scanner input) {
        System.out.print("\nEnter the ticket number: ");
        String ticketNumber = input.next();

        Vehicle retrievedVehicle = parkingLot.retrieveVehicle(ticketNumber);

        if (retrievedVehicle != null) {
            System.out.println("\n------- Vehicle Retrieved Successfully --------------------");
            System.out.println("|Vehicle with license plate: " + retrievedVehicle.getLicensePlate().toUpperCase() + " retrieved successfully.");
            System.out.println("|Exit Time: " + LocalDateTime.now());
            System.out.println("----------------------------------------------------------");

        } else {
            System.out.println("Invalid ticket number. Unable to retrieve the vehicle.");
        }
    }
}
