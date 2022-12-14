package org.example.util;

import java.util.Scanner;

public class InputManager {

    private final static Scanner sc = new Scanner(System.in);

    private InputManager() {
    }

    public static String scanInput() {
        return sc.nextLine();
    }
}
