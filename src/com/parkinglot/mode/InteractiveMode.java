package com.parkinglot.mode;

import com.parkinglot.models.Command;
import com.parkinglot.Commands.CommandExecutorFactory;
import com.parkinglot.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveMode extends Mode{

    public InteractiveMode(final CommandExecutorFactory commandExecutorFactory, final Writer writer) {
        super(commandExecutorFactory,writer);
    }

    @Override
    public void process() throws IOException {
        writer.welcome();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            final String input = reader.readLine();
            final Command command = new Command(input);
            processCommand(command);
        }
    }
}
