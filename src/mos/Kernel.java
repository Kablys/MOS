package mos;

import com.sun.org.apache.regexp.internal.RE;

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

    void Scheduler () throws InterruptedException, IndexOutOfBoundsException {
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
                        .get(0);// FIXME when there is no more proc to run throws execption that Main class catches
                sleep(100); // incase of bug prevents uncontrollable loop
                System.out.println(i.toString() + " Scheduler selected: " + procToRun.name);
                synchronized (procToRun) {procToRun.notify();}
                CPU.wait();
            }
        }
        System.out.println("scheduler loop over");
    }

    // Manager methods responsible for managing resources.
    public static void createRes (Process creator, String name){
        List<Resource> ress = resources.stream()
                .filter(r -> r.creator == null) // get only requested resources
                .filter(r -> r.name.equals(name))
                .collect(Collectors.toList());
        if (ress.isEmpty()){
            // if no process need this resource add to list
            resources.add(new Resource(name, creator, null));
        } else {
            // if there is process that needs this resource send it
            if (ress.size() > 1){
                // TODO maybe try use priority
                System.out.println("more than 1 resource with same name, what to do?");
                System.out.println(creator + name + ress.toString());
            }
            Resource res = ress.get(0);
            res.creator = creator;
            // TODO add element info
            synchronized (res) {res.notify();}
        }

    }

    public static Resource requestRes (Process requester, String name){
        List<Resource> ress = resources.stream()
                .filter(r -> r.requester == null) // get only created resources
                .filter(r -> r.name.equals(name))
                .collect(Collectors.toList());
        if (ress.isEmpty()){
            // if no process has created this resource
            Resource res = new Resource(name, null, requester);
            resources.add(res);
            synchronized (res){
                try {
                    synchronized (Kernel.CPU) {Kernel.CPU.notify();}
                    res.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return res;
        } else {
            // if there is process that created this resource;
            if (ress.size() > 1){
                // TODO maybe try use priority
                System.out.println("more than 1 resource with same name, what to do?");
                System.out.println(requester + name + ress.toString());
            }
            Resource res = ress.get(0);
            res.requester = requester ;
            // TODO add element info
            return res;
        }
    }
}
