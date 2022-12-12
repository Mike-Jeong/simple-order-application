package org.example.type;

import org.example.controller.Controller;
import org.example.controller.ProductOrderController;
import org.example.controller.QuitController;
import org.example.exception.CustomException;

import java.util.Arrays;

public enum ApplicationMenu {

    ORDER(new MenuCommand("주문", new String[]{"o", "order"}), new ProductOrderController()),
    QUIT(new MenuCommand("종료", new String[]{"q", "quit"}), new QuitController());

    private final MenuCommand menuCommand;
    private final Controller controller;

    ApplicationMenu(MenuCommand menuCommand, Controller controller) {
        this.menuCommand = menuCommand;
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public static ApplicationMenu getApplicationMenu(String input) {
        return Arrays.stream(values())
                .filter(e -> e.menuCommand.isContainCommand(input))
                .findFirst()
                .orElseThrow(() -> new CustomException(CustomError.COMMAND_NOT_FOUND));
    }

    public static String getApplicationMenuString() {

        String[] commandStrings = Arrays.stream(values())
                .map(e -> e.menuCommand.getCommandString())
                .toArray(String[]::new);

        return String.join(", ", commandStrings);
    }
}

