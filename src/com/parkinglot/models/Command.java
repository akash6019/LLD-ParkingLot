package com.parkinglot.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {

    private final String SPACE = " ";
    private String commandName;
    List<String> params;

    public Command(final String inputLine) {

        final List<String> tokens =
                Arrays.stream(inputLine.trim().split(SPACE))
                .map(String::trim)
                .filter(token -> token.length() > 0 )
                .collect(Collectors.toList());

        if(tokens.size()<=0)
            throw new RuntimeException();

        commandName = tokens.get(0);
        tokens.remove(0);
        params = tokens;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getParams() {
        return params;
    }
}
