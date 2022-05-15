package hrapp_models;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.BeforeTest;
import osmo.tester.annotation.CoverageValue;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Post;
import osmo.tester.annotation.TestStep;
import osmo.tester.annotation.Variable;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.EndCondition;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.StateCoverage;
import osmo.tester.generator.endcondition.logical.And;
import osmo.tester.generator.testsuite.TestCaseStep;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

import java.util.Collection;
import java.util.Map;

public class LoginModel2 {

    @Variable
    public boolean isUserLoggedIn = false;
    public boolean initialActionTested = false;

    // state variable = requirement
    public Requirements loginRequirements = new Requirements();

    // state guard variables
    public String stateLoginPage = "Login Page";
    public String stateDashboardPage = "Dashboard Page";
    public String currentState = "Fake vertex";
    public String coverageState = "Starting";

    // other variables
    public TestSuite suite;
    public final Scripter scripter;

    public LoginModel2() {
        scripter = new Scripter(System.out);
        loginRequirements.add("Login page reached");
        loginRequirements.add("Dashboard page reached");
    }

    @CoverageValue("login-state")
    public String myState(TestCaseStep step) {
        return coverageState;
    }

    @BeforeTest
    public void start() {
        isUserLoggedIn = false;
        initialActionTested = false;
        currentState = "Fake vertex";
        coverageState = "Start";
        int tests = suite.getAllTestCases().size();
        System.out.println("---------------------------------------------------");
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size();
        System.out.println("Total tests generated: "+tests);
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
        coverageState = "login_enabled_standard_user";
        loginRequirements.covered("Dashboard page reached");
    }

    @TestStep("logout")
    public void logout() {
        scripter.step("Logout.");
        isUserLoggedIn = false;
        currentState = stateLoginPage;
    }

    @Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }


    public static void main(String[] args) {

        LoginModel loginModel = new LoginModel();
        OSMOTester tester = new OSMOTester();

        tester.addModelObject(loginModel);
        tester.setAlgorithm(new RandomAlgorithm());
        tester.setSuiteEndCondition(new Length(1));

        EndCondition el = new Length(2);
        EndCondition state = new StateCoverage("login-state", "login_enabled_standard_user");
        EndCondition full = new And(el, state);
        tester.setTestEndCondition(full);

        // test generation command
        tester.generate(System.currentTimeMillis()); //random seed
        TestSuite suite = tester.getSuite();
        Map<String, Collection<String>> states = suite.getCoverage().getStates();
        System.out.println(states);
    }

}