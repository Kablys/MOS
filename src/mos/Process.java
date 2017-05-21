package mos;

import java.util.List;
import java.util.stream.Collectors;

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

public abstract class Process implements Runnable {
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

    public Process(String name, Process parent, int priority) {
        this.name = name;
        this.state = ProcState.READY;
        this.parent = parent;

        Kernel.processes.add(this);
        Thread t1 = new Thread(this, name);
        t1.start();
    }

    @Override
    public void run() {
    };
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
//    }
    public void terminate() throws InterruptedException {
        // Gets all the children of this process
        List<Process> childrenProcs = Kernel.processes.stream()
                .filter(p -> p.parent == this)
                .collect(Collectors.toList());
        for(Process child : childrenProcs){
            child.terminate();
        }
        //TODO do same with resources
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

