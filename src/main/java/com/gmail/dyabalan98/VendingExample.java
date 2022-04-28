package com.gmail.dyabalan98;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.EndCondition;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Post;
import osmo.tester.annotation.TestStep;
import osmo.tester.generator.testsuite.TestSuite;

import java.io.PrintStream;

public class VendingExample {
    private final Scripter scripter;
    private int cents = 0;
    private int items = 10;
    private TestSuite suite;

    public VendingExample() {
        scripter = new Scripter(System.out);
    }

    @Guard("all")
    public boolean gotItems() {
        return items > 0;
    }

    @BeforeTest
    public void start() {
        cents = 0;
        items = 10;
        int tests = suite.getAllTestCases().size()+1;
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size()+1;
        System.out.println("Total tests generated: "+tests);
    }

    @TestStep("10c")
    public void insert10cents() {
        scripter.step("INSERT 10");
        cents += 10;
    }

    @TestStep("20c")
    public void insert20cents() {
        scripter.step("INSERT 20");
        cents += 20;
    }

    @TestStep("50c")
    public void insert50cents() {
        scripter.step("INSERT 50");
        cents += 50;
    }

    @Guard("vend")
    public boolean allowVend() {
        return cents >= 100;
    }

    @TestStep("vend")
    public void vend() {
        scripter.step("VEND ("+items+")");
        cents -= 100;
        items--;
    }

    @EndCondition
    public boolean end() {
        return items <= 0;
    }

    @Post("all")
    public void checkState() {
        scripter.step("CHECK(items == "+items+")");
        scripter.step("CHECK(cents == "+ cents +")");
    }

    public static void main(String[] args) {
        OSMOTester tester = new OSMOTester();
        tester.addModelObject(new VendingExample());
        tester.generate(55);
    }

    private static class Scripter {
        private PrintStream out;

        public Scripter(PrintStream out) {
            this.out = out;
        }

        public void step(String name) {
            out.println("STEP: "+name);
        }
    }
}