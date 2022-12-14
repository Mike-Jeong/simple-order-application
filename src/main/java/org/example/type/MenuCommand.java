package org.example.type;

import java.util.Arrays;

class MenuCommand {

    private final String menu;
    private final String[] commands;

    public MenuCommand(String menu, String[] commands) {
        this.menu = menu;
        this.commands = commands;
    }

    public String getCommandString() {
        return commands[0] + "[" + String.join(", ", Arrays.copyOfRange(commands, 1, commands.length)) + "]: " + menu;
    }

    public boolean isContainCommand(String input) {
        return Arrays.asList(commands).contains(input);
    }
}