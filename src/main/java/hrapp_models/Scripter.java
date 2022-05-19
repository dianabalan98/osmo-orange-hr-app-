package hrapp_models;

import java.io.PrintStream;

public class Scripter {
    private PrintStream out;

    public Scripter(PrintStream out) {
        this.out = out;
    }

    public void step(String name) {
        System.out.println("STEP: " + name);
    }
}
