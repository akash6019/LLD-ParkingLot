package com.parkinglot.mode;

import com.parkinglot.models.Command;
import com.parkinglot.Commands.CommandExecutor;
import com.parkinglot.Commands.CommandExecutorFactory;
import com.parkinglot.Writer;

import java.io.IOException;

public abstract class Mode {

    CommandExecutorFactory commandExecutorfactory;
    Writer writer;

    public Mode(final CommandExecutorFactory commandExecutorfactory, final Writer writer) {
        this.commandExecutorfactory = commandExecutorfactory;
        this.writer = writer;
    }

    protected void processCommand(final Command command) {
        final CommandExecutor commandExecutor = commandExecutorfactory.getCommandExecutor(command);
        if(commandExecutor.validate(command)) {
            commandExecutor.execute(command);
        } else {
            throw new RuntimeException();
        }
    }

    public abstract void process() throws IOException;
}