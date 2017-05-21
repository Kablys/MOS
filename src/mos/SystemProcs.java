package mos;

class RootProgram extends Process {

    public RootProgram(String name, Process parent, int priority) {
        super(name, parent, priority);
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait(); //Stops and waits for scheduler
                Process cli = new CLIProgram("CLI", this, 9);
                //Process readInput = new ReadInputProgram("ReadInput", this, 8);

                getRes("MOS pabaiga");

                cli.terminate();
                //readInput.terminate();

                getRes("Neegzistuojantis");//root will be terminated by scheduler
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CLIProgram extends Process {

    public CLIProgram(String name, Process parent, int priority) {
        super(name, parent, priority);
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait();
                makeRes("input packet");
                //        scanner sc = new scanner(system.in); //todo move this to readinput
                //        string name = sc.nextline();
                //        createproc(name);
                makeRes("MOS pabaiga");
                getRes("neegzistuojantis");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ReadInputProgram extends Process {

    public ReadInputProgram(String name, Process parent, int priority) {
        super(name, parent, priority);
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait();
                //TODO getRes("input packet");
                getRes("Neegzistuojantis");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class PlaceholderProgram extends Process {

    public PlaceholderProgram(String name, Process parent, int priority) {
        super(name, parent, priority);
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                this.wait();
                System.out.println("This is placeholder program or you tried create process wrong name");
                getRes("Neegzistuojantis");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
