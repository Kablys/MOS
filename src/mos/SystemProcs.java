package mos;

import java.util.Scanner;

public class SystemProcs {
    static public Process getProc (String name, Process parent){
        switch (name.toLowerCase()){
            case "root":
                return new Process(name, parent, 10, new RootProgram());
            case "cli":
                return new Process(name, parent, 9,  new CLIProgram());
            case "readinput":
                return new Process(name, parent, 8,  new ReadInputProgram());
            default:
                return new Process(name, parent, 0, new PlaceholderProgram());
                // TODO add parsing for vm name.

        }
    };

}

class RootProgram implements Program {

    @Override
    public void run(Process proc) throws InterruptedException {
        Process cli         = proc.createProc("CLI");
        Process mainProc    = proc.createProc("MainProc");
        Process readDisk    = proc.createProc("ReadDisk");
        Process writeDisk   = proc.createProc("WriteDisk");
        Process readInput   = proc.createProc("ReadInput");
        Process writeOutput = proc.createProc("WriteOutput");

        proc.getRes("MOS pabaiga");

        cli.terminate();
        mainProc.terminate();
        readDisk.terminate();
        writeDisk.terminate();
        readInput.terminate();
        writeOutput.terminate();

        proc.getRes("Neegzistuojantis");//root will be terminated by scheduler
    }
}

class CLIProgram implements Program {

    @Override
    public void run(Process proc) throws InterruptedException {
        proc.makeRes("input packet");
    //        Scanner sc = new Scanner(System.in); //TODO move this to ReadInput
    //        String name = sc.nextLine();
    //        proc.createProc(name);
        proc.makeRes("MOS pabaiga");
        proc.getRes("Neegzistuojantis");
    }
}

class ReadInputProgram implements Program {

    @Override
    public void run(Process proc) throws InterruptedException {
        //TODO proc.getRes("input packet");
        proc.getRes("Neegzistuojantis");
    }
}
class PlaceholderProgram implements Program {

    @Override
    public void run(Process proc) throws InterruptedException {
        System.out.println("This is placeholder program or you tried create process wrong name");
        proc.getRes("Neegzistuojantis");
    }
}
