package hrapp_models;

import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.generator.SingleInstanceModelFactory;
import osmo.tester.generator.algorithm.BalancingAlgorithm;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.structure.ElementCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverageRequirement;
import osmo.tester.generator.endcondition.structure.StepCoverage;

import java.io.IOException;
import java.util.ArrayList;

public class SharedStateExperiments {

    public static void main(String[] args) throws IOException {

        UtilsMethods utils = new UtilsMethods();
        ElementCoverageRequirement req;

        String pathReachedStep = "SharedStateTestCases\\random_shared_state_full_steps_coverage";
        String path = pathReachedStep;
        // create new folder in the given location with the given folder name
        utils.createNewTestOutputDirectory(path);
        // create CSV file for metrics
        utils.initializeCSVFile(path);

        for(int i=1; i<=1; i++) {
            OSMOTester tester = new OSMOTester();
            OSMOConfiguration config = new OSMOConfiguration();
            tester.setConfig(config);
            config.setSuiteEndCondition(new Length(1));
            config.setAlgorithm(new RandomAlgorithm());
            SingleInstanceModelFactory factory = new SingleInstanceModelFactory();
            factory.add(new LoginModel());
            factory.add(new AddProjectModel());
            factory.add(new ReviewPerformanceModel());
            config.setFactory(factory);

            // full steps coverage across all 3 models
            ArrayList<String> loginExpectedSteps = utils.getLoginExpectedSteps();
            ArrayList<String> addProjectExpectedSteps = utils.getAddProjectExpectedSteps();
            ArrayList<String> reviewPerformanceExpectedSteps = utils.getReviewPerformanceExpectedSteps();
            StepCoverage steps = new StepCoverage();
            for (String step : loginExpectedSteps) {
                steps.addRequiredStep(step);
            }
            for (String step : addProjectExpectedSteps) {
                steps.addRequiredStep(step);
            }
            for (String step : reviewPerformanceExpectedSteps) {
                steps.addRequiredStep(step);
            }
            config.setTestEndCondition(steps);


            utils.generateAndSaveOsmoOutput2(i, tester, path);
        }
    }
}
