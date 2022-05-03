package hrapp_models;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.generator.testsuite.TestSuite;
import java.util.ArrayList;


public class LoginModel {

    @Variable
    public boolean isUserLoggedIn = false;
    public boolean initialActionTested = false;

    // state guard variables
    public String stateLoginPage = "Login Page";
    public String stateDashboardPage = "Dashboard Page";
    public String currentState = "Fake vertex";

    // coverage variable
    /**
     * create a String array of all the tested actions and add them to it only once
     * final number of actions in array will be used as endcondition
     */
    public ArrayList<String> coveredSteps = new ArrayList<String>();

    // other variables
    public TestSuite suite;
    public final Scripter scripter;
    public final UtilsMethods utils = new UtilsMethods();

    public LoginModel() {
        scripter = new Scripter(System.out);
    }

    @BeforeTest
    public void start() {
        isUserLoggedIn = false;
        initialActionTested = false;
        currentState = "Fake vertex";
        coveredSteps.clear();
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
        utils.checkList(coveredSteps, "load_login_page");
    }

    @TestStep("login_empty_username")
    public void loginEmptyUsername() {
        scripter.step("Login with empty username.");
        utils.checkList(coveredSteps, "login_empty_username");
    }

    @TestStep("login_empty_password")
    public void loginEmptyPassword() {
        scripter.step("Login with empty password.");
        utils.checkList(coveredSteps, "login_empty_password");
    }

    @TestStep("login_invalid_credentials")
    public void loginInvalidCredentials() {
        scripter.step("Login with invalid credentials.");
        utils.checkList(coveredSteps, "login_invalid_credentials");
    }

    @TestStep("login_disabled_user")
    public void loginDisabledUser() {
        scripter.step("Login with disabled user.");
        utils.checkList(coveredSteps, "login_disabled_user");
    }

    @TestStep("login_enabled_standard_user")
    public void loginEnabledStandardUser() {
        scripter.step("Login with enabled standard user.");
        isUserLoggedIn = true;
        currentState = stateDashboardPage;
        utils.checkList(coveredSteps, "login_enabled_standard_user");
    }

    @TestStep("logout")
    public void logout() {
        scripter.step("Logout.");
        isUserLoggedIn = false;
        currentState = stateLoginPage;
        utils.checkList(coveredSteps, "logout");
    }

    @Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }


    public static void main(String[] args) {
        OSMOTester tester = new OSMOTester();
        tester.addModelObject(new LoginModel());
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


        // test generation command
        tester.generate(System.currentTimeMillis()); //random seed
    }

}
