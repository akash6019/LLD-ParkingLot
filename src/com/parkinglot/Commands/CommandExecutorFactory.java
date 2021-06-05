package com.parkinglot.Commands;

import com.parkinglot.Writer;
import com.parkinglot.models.Command;
import com.parkinglot.service.ParkingLotService;

import java.util.HashMap;
import java.util.Optional;

public class CommandExecutorFactory {

    private HashMap<String,CommandExecutor> commands = new HashMap<>();

    public CommandExecutorFactory(final ParkingLotService parkingLotService, final Writer writer) {
        commands.put(CreateCommandExecutor.COMMAND_NAME, new CreateCommandExecutor(parkingLotService,writer));
        commands.put(DisplayCommandExecutor.COMMAND_NAME, new DisplayCommandExecutor(parkingLotService,writer));
        commands.put(ParkCommandExecutor.COMMAND_NAME, new ParkCommandExecutor(parkingLotService,writer));
        commands.put(UnParkCommandExecutor.COMMAND_NAME, new UnParkCommandExecutor(parkingLotService,writer));
        commands.put(ExitCommandExecutor.COMMAND_NAME, new ExitCommandExecutor(parkingLotService,writer));
    }

    public CommandExecutor getCommandExecutor(final Command command) {
        Optional<CommandExecutor> executor = Optional.ofNullable(commands.get(command.getCommandName()));
        return executor.orElseThrow(RuntimeException::new);
    }
}
