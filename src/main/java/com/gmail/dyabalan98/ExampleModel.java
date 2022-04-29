package com.gmail.dyabalan98;
import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;
import osmo.tester.annotation.Variable;
import osmo.tester.coverage.ScoreConfiguration;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.LengthProbability;
import osmo.tester.generator.listener.TracePrinter;
import osmo.tester.model.Requirements;

public class ExampleModel {
    private final Requirements req = new Requirements();
    @Variable
    private int counter = 0;

    private static final String REQ_INCREASE = "REQ_INCREASE";
    private static final String REQ_DECREASE = "REQ_DECREASE";

    public ExampleModel() {
        req.add(REQ_INCREASE);
        req.add(REQ_DECREASE);
    }

    @BeforeTest
    public void start() {
        counter = 0;
    }

    @Guard("decrease")
    public boolean toDecreaseOrNot() {
        return counter > 1;
    }

    @TestStep("decrease")
    public void decreaseState() {
        req.covered(REQ_DECREASE);
        counter--;
        System.out.println("- " + counter);
    }

    @Guard("increase")
    public boolean shallWeIncrease() {
        return counter >= 0;
    }

    @TestStep("increase")
    public void increaseState() {
        req.covered(REQ_INCREASE);
        counter++;
        System.out.println("+ " + counter);
    }

    public static void main(String[] args) {
        OSMOTester tester = new OSMOTester();
        OSMOConfiguration oc = tester.getConfig();
        oc.addListener(new TracePrinter());
        oc.setTestEndCondition(new LengthProbability(10, 20, 0.2d));
        oc.setSuiteEndCondition(new Length(10));
        oc.setFactory(models -> models.add(new ExampleModel()));
        ScoreConfiguration config = new ScoreConfiguration();
        tester.generate(112);
    }
}