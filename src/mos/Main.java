package mos;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //bootup starts root process, but it can't run without scheduler
        Process root = new Process("Root", null, 10, new RootProgram());
        Kernel.processes.add(root);
        Thread t1 = new Thread(root, "Root");
        t1.start();
        try {
            (new Kernel()).Scheduler();
            root.terminate();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            //sleep(100);
            System.out.println("No more proc to run system shuting down");
            System.exit(0);
        }
    }
}

