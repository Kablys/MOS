package mos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            (new Kernel()).Scheduler();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No more proc to run system shuting down");
            System.exit(0);
        }
    }
}

