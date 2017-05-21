package mos;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        //bootup starts root process, but it can't run without scheduler
        Process root = new RootProgram("Root", null, 10);
        try {
            (new Kernel()).Scheduler();
            root.terminate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            //sleep(100);
            System.out.println("No more proc to run system shuting down");
            System.exit(0);
        }
    }
}

