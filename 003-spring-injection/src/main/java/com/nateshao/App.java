package com.nateshao;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                if (j == i) {
                    System.out.println(j + " * " + i + "=" + (j * i) + " ");
                } else {
                    System.out.print(j + " * " + i + "=" + (j * i) + " ");
                }
            }
        }
    }
}
