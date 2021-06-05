package com.parkinglot;

import com.parkinglot.mode.InteractiveMode;
import com.parkinglot.Commands.CommandExecutorFactory;
import com.parkinglot.service.ParkingLotService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final ParkingLotService parkingLotService = new ParkingLotService();
        final Writer writer = new Writer();
        final CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingLotService, writer);

        new InteractiveMode(commandExecutorFactory, writer).process();

    }
}