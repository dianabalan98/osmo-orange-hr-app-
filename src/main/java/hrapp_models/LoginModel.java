package hrapp_models;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.StateCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverageRequirement;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

import java.util.ArrayList;


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

    @TestStep("load_login_page")
    public void loadLoginPage() {
        scripter.step("Load login page.");
        currentState = stateLoginPage;
        initialActionTested = true;
        loginRequirements.covered("Login page reached");
    }

    @TestStep("login_empty_username")
    public void loginEmptyUsername() {
        scripter.step("Login with empty username.");
    }

    @TestStep("login_empty_password")
    public void loginEmptyPassword() {
        scripter.step("Login with empty password.");
    }

    @TestStep("login_invalid_credentials")
    public void loginInvalidCredentials() {
        scripter.step("Login with invalid credentials.");
    }

    @TestStep("login_disabled_user")
    public void loginDisabledUser() {
        scripter.step("Login with disabled user.");
    }

    @TestStep("login_enabled_standard_user")
    public void loginEnabledStandardUser() {
        scripter.step("Login with enabled standard user.");
        isUserLoggedIn = true;
        currentState = stateDashboardPage;
        loginRequirements.covered("Dashboard page reached");
    }

    @TestStep("logout")
    public void logout() {
        scripter.step("Logout.");
        isUserLoggedIn = false;
        currentState = stateLoginPage;
    }

    /*@Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }*/


    public static void main(String[] args) {

        LoginModel loginModel = new LoginModel();
        OSMOTester tester = new OSMOTester();
        ElementCoverageRequirement req;

        tester.addModelObject(loginModel);
        tester.setAlgorithm(new RandomAlgorithm());
        tester.setSuiteEndCondition(new Length(1));

        /**
         * Full step coverage
         */
        UtilsMethods utils = new UtilsMethods();
        ArrayList<String> loginExpectedSteps = utils.getLoginExpectedSteps();
        StepCoverage steps = new StepCoverage();
        for (String step : loginExpectedSteps) {
            steps.addRequiredStep(step);
        }
        tester.setTestEndCondition(steps);

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
        tester.generate(System.currentTimeMillis()); //random seed
    }

}
