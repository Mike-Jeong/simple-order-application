package org.example;

import org.example.exception.CustomException;
import org.example.type.ApplicationMenu;
import org.example.util.InputManager;

public class OrderApplication {

    public void start() {

        while (true) {

            printInitMessage();

            String input = InputManager.scanInput();

            try {

                ApplicationMenu applicationMenu = ApplicationMenu.getApplicationMenu(input);
                applicationMenu.getController().process();

                if (applicationMenu.equals(ApplicationMenu.QUIT)) break;

            } catch (CustomException e) {
                System.out.println(e.getErrorDescription());
            }
        }

        printGoodbyeMessage();
    }

    private void printInitMessage() {

        System.out.printf("입력(%s) : ", ApplicationMenu.getApplicationMenuString());
    }

    private void printGoodbyeMessage() {
        System.out.println("고객님의 주문 감사합니다");
    }

}
