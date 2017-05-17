package mos;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * Core part of OS
 */

public class Kernel {
    public static final Object CPU = new Object(); // Somethink for processes to block on.
    static List<Process>  processes = new ArrayList<>();
    static List<Resource> resources = new ArrayList<>();

    // Scheduler(processes.filter(state = Ready).orderBy(priority).head)
        //Exception if returns null
    void Scheduler () throws InterruptedException, IndexOutOfBoundsException {

        //bootup starts root process
        Process root = new Process("Root", null, 10, new RootProgram());
        Kernel.processes.add(root);
        Thread t1 = new Thread(root, "Root");
        t1.start();


        //After root process is started we can start scheduler
        for (Integer i = 0;;i++){
            synchronized (CPU){
                if (processes.isEmpty()){
                    break;
                }
                Process procToRun = processes.stream()
                        .filter(p -> p.state == ProcState.READY)
                        .sorted(Comparator.comparing(Process::getPriority))
                        .collect(Collectors.toList())
                        .get(0);// put in try block
                sleep(100); // incase of bug prevents uncontrollable loop
                System.out.println(procToRun.name + i.toString());
                synchronized (procToRun) {procToRun.notify();}
                CPU.wait();
            }
        }
        System.out.println("scheduler loop over");

        //"Shutdown" ends root proc
        root.terminate();
        t1.join();

    }

    public static List<Process> getChildren (Process proc){
        return processes.stream()
                .filter(p -> p.parent == proc)
                .collect(Collectors.toList());
    }

    public static Resource getResByName (String name){
        List<Resource> ress = resources.stream()
                .filter(p -> p.name.equals(name))
                .collect(Collectors.toList());
        if (ress.size() > 1) {
            System.out.println("Found more then one res named: " + name);
        } else if (ress.size() == 0){ //
            System.out.println("There is no resource named: " + name);
            System.exit(1);
        }
        return ress.get(0);

    }
    // Manager(resources
}
