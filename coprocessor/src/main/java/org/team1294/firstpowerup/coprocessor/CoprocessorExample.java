package org.team1294.firstpowerup.coprocessor;

public class CoprocessorExample {
    public static void main(String[] args) {
        System.out.println("Hello World! I am a coprocessor!");
        System.out.println("2 + 2 = " + exampleAdd(2, 2));
    }

    public static int exampleAdd(int x, int y) {
        return x + y;
    }
}