package hrapp_models;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.algorithm.WeightedRandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.StateCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverageRequirement;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class LoginModel {

    @Variable
    public boolean isUserLoggedIn = false;
    public boolean initialActionTested = false;

    // state variable = requirement
    public Requirements loginRequirements = new Requirements();

    // state guard variables
    public String stateLoginPage = "Login Page";
    public String stateDashboardPage = "Dashboard Page";
    public String currentState = "Fake vertex";

    // other variables
    public TestSuite suite;
    public final Scripter scripter;

    public LoginModel() {
        scripter = new Scripter(System.out);
        loginRequirements.add("Login page reached");
        loginRequirements.add("Dashboard page reached");

    }

    @BeforeTest
    public void start() {
        isUserLoggedIn = false;
        initialActionTested = false;
        currentState = "Fake vertex";
        int tests = suite.getAllTestCases().size();
        System.out.println("---------------------------------------------------");
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size();
    }

    @Guard({"load_login_page", "login_empty_username", "login_empty_password", "login_invalid_credentials",
    "login_disabled_user", "login_enabled_standard_user"})
    public boolean allowPreLoginActions() {
        return !isUserLoggedIn;
    }

    @Guard({"login_empty_username", "login_empty_password", "login_invalid_credentials", "login_disabled_user", "login_enabled_standard_user" })
    public boolean preventActionsUntilLoginPageIsLoaded(){
        return currentState.equals(stateLoginPage);
    }

    @Guard("load_login_page")
    public boolean allowLoadLoginPageOnlyOnce(){
        return !initialActionTested;
    }

    @Guard("logout")
    public boolean allowLogout() {
        return isUserLoggedIn && currentState.equals(stateDashboardPage);
    }

    @TestStep(name="load_login_page", weight=100)
    public void loadLoginPage() {
        scripter.step("Load login page.");
        currentState = stateLoginPage;
        initialActionTested = true;
        loginRequirements.covered("Login page reached");
    }

    @TestStep(name="login_empty_username", weight=10)
    public void loginEmptyUsername() {
        scripter.step("Login with empty username.");
    }

    @TestStep(name="login_empty_password", weight=10)
    public void loginEmptyPassword() {
        scripter.step("Login with empty password.");
    }

    @TestStep(name="login_invalid_credentials", weight=20)
    public void loginInvalidCredentials() {
        scripter.step("Login with invalid credentials.");
    }

    @TestStep(name="login_disabled_user", weight=10)
    public void loginDisabledUser() {
        scripter.step("Login with disabled user.");
    }

    @TestStep(name="login_enabled_standard_user", weight=50)
    public void loginEnabledStandardUser() {
        scripter.step("Login with enabled standard user.");
        isUserLoggedIn = true;
        currentState = stateDashboardPage;
        loginRequirements.covered("Dashboard page reached");
    }

    @TestStep(name="logout", weight=100)
    public void logout() {
        scripter.step("Logout.");
        isUserLoggedIn = false;
        currentState = stateLoginPage;
    }

    /*@Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }*/


    public static void main(String[] args) throws IOException {

        UtilsMethods utils = new UtilsMethods();
        ElementCoverageRequirement req;
        /*OSMOTester tester = new OSMOTester();
        tester.addModelObject(new LoginModel());
        tester.setSuiteEndCondition(new Length(1));*/

        /**
         * Set the algorithm
         */
        //tester.setAlgorithm(new RandomAlgorithm());
        //tester.setAlgorithm(new WeightedRandomAlgorithm());

        /**
         * Full step coverage
         */
        /*UtilsMethods utils = new UtilsMethods();
        ArrayList<String> loginExpectedSteps = utils.getLoginExpectedSteps();
        StepCoverage steps = new StepCoverage();
        for (String step : loginExpectedSteps) {
            steps.addRequiredStep(step);
        }
        tester.setTestEndCondition(steps);*/

        /**
         * Full state coverage (requirements)
         */
        /*req = new ElementCoverageRequirement(0, 0, loginModel.loginRequirements.getRequirements().size());
        tester.setSuiteEndCondition(new ElementCoverage(req));*/

        /**
         * Full step + state coverage -> leave both coverage conditions. Full step coverage already covers
         * all states though.
         */

        /**
         * Random reached logout step
         */
        //tester.setTestEndCondition(new StepCoverage("logout"));


        /**
         * TODO: Random reached dashboard page state -> still not functional
         */
        //tester.setTestEndCondition(new StateCoverage("login_enabled_standard_user"));
        //tester.setTestEndCondition(new StateCoverage("test","login_enabled_standard_user"));

        // test generation command
        //tester.generate(System.currentTimeMillis()); //random seed
        String path = "login-test-cases\\pathTest";

        // create new folder in the given location with the given folder name
        utils.createNewTestOutputDirectory(path);
        // create CSV file for metrics
        utils.initializeCSVFile(path);

        for(int i=1; i<=100; i++) {
            OSMOTester tester = new OSMOTester();
            tester.addModelObject(new LoginModel());
            tester.setSuiteEndCondition(new Length(1));
            tester.setAlgorithm(new RandomAlgorithm());
            tester.setTestEndCondition(new StepCoverage("logout"));

            utils.generateAndSaveOsmoOutput2(i, tester, path);
        }

    }

}
