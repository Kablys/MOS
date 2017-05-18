package mos;

import java.util.List;

//Vykdomas procesas tampa:
    //Blokuotu, paprašius resurso kurio jis nesulaukia
    //Pasiruošusiu, praradus procesorių (ne dėl kito resurso trūkumo priežasties)
//Blokuotas procesas tampa:
    //Pasiruošusiu, kai jam suteikiamas reikalingas resursas
    //Blokuotu-sustabdytu, jei einamasis procesas jį sustabdo
//Pasiruošęs procesas tampa:
    //Vykdomu, kai gaunamas procesoriaus resursas
    //Pasiruošusiu-sustabdytu, kai einamasis procesas jį sustabdo
//Blokuotas-sustabdytas tampa:
    //Blokuotu, jei einamasis procesas aktyvuoja procesą
    //Pasiruošusiu-sustabdytu,  jei procesui suteikiamas resursas dėl kurio jis užsiblokavo
//Pasiruošęs-sustabdytas tampa:
    //Pasiruošusiu, jei einamasis procesas aktyvuoja procesą
enum ProcState{
    READY, ONGOING, BLOCKED, READY_STOPPED, BLOCKED_STOPPED
}

interface Program {
    void run(Process proc) throws InterruptedException;
}

public class Process implements Runnable {
    //descriptor
    int id;         //ID - išorinis vardas
    String name;
    Process parent; //Tėvas (procesas)
    ProcState state;//Būsena
    //Procesoriaus būsena
        //Režimas (SUPER/USER)
        //Registrų reikšmės (TMP, IC, C)
        //maybe ID's
    //List<Process> children;     //Vaikų (procesų) sąrašas
    //List<Resource> createdRes;  //Sukurtų resursų sąrašas
    //List<Resource> receivedRes; //Pasiekiamų (gautų) resursų sąrašas

    private Integer priority;//Prioritetas
    public Integer getPriority() {
        return priority;
    }

    private Program program;

    public Process(String name, Process parent, int priority, final Program program) {
        //this.id = should be auto generated
        this.name = name;
        this.state = ProcState.READY;
        this.parent = parent;
        if (0 <= priority && priority <= 10) { // TODO what should be range of priority
            this.priority = priority;
        } else {
            this.priority = 0; // TODO error
        }

        this.program = program;
    }

    @Override
    public void run() {
        try {
            synchronized (this){
                this.wait(); //Stops and waits for scheduler
                program.run(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public Process createProc (String name) {
        Process child = SystemProcs.getProc(name, this);
        Kernel.processes.add(child);
        Thread t1 = new Thread(child, name);
        t1.start();
        return child;
        //Kurti procesą
            //Argumentai:
            //Tėvinis procesas DONE
            //Pradinė procesoriaus būsena NO NEED
            //Prioritetas DONE
            //Perduodamų elementų sąrašas
            //Išorinis vardas. DONE
            //Iš argumentų sukuriamas proceso deskriptorius DONE
            //Procesas pridedamas prie bendro procesų sąrašo DONE
            //Procesas pridedamas prie tėvinio proceso vaikų sąrašo NO NEED
    }
    public void terminate() throws InterruptedException {
        List<Process> children = Kernel.getChildren(this);
        for(Process child : children){
            child.terminate();
        }
        System.out.println("terminating proc: " + this.name);
        Kernel.processes.remove(this);
        //Naikinti procesą
        //Rekursyviai naikinami proceso sukurti resursai ir vaikai
        //Pašalinamas iš tėvinio proceso vaikų sąrašo
        //Atlaisvinami visi gauti resursai
        //Procesas pašalinamas iš bendro procesų sąrašo
        //Jei sunaikinto proceso būsena buvo vykdomas - kviečiamas planuotojas
    }
    public Resource getRes(String resName) throws InterruptedException {
        this.state = ProcState.BLOCKED;
        System.out.println(this.name + " requested resource: " + resName);
//        if (resName.equals("Neegzistuojantis")) {
//            synchronized (Kernel.CPU) {Kernel.CPU.notify();}
//            System.out.println(this.name + " laukia \"Neegzistuojantis\" resurso");
//            synchronized (this) {wait();}
//            System.out.println("you should not be here");
//            assert true;
//        }
//        Resource res = new Resource(resName, this);
//        Kernel.resources.add(res);
//        synchronized (res){
//            System.out.println(this.name + " is waiting for " + resName);
//            synchronized (Kernel.CPU) {Kernel.CPU.notify();}
//            res.wait();
//        }
        Resource res = Kernel.requestRes(this, resName);
        System.out.println(this.name + " got resource: " + resName);
        this.state = ProcState.READY;
        synchronized (this) {this.wait();}
        return res;
    }
    public void makeRes(String resName){
        Kernel.createRes(this, resName);
        System.out.println(this.name + " created resource: " + resName);
    }
    //Stabdyti procesą
        //Proceso būsena keičiama iš blokuotas į blokuotas-sustabdytas arba iš pasiruošęs į pasiruošęs-sustabdytas.
    //Aktyvuoti procesą
        //Proceso būsena keičiama iš blokuotas-sustabdytas į blokuotas arba iš pasiruošęs-sustabdytas į pasiruošęs.
        //Kviečiamas planuotojas
    @Override
    public String toString() {
        return super.toString();
    }
}

