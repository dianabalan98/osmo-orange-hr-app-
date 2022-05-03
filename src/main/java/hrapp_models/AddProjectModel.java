package hrapp_models;
import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.generator.testsuite.TestSuite;
import java.util.ArrayList;

public class AddProjectModel {

    @Variable
    public boolean isProjectAdded = false;
    public boolean initialActionTested = false; // go to projects page from dashboard

    // state guard variables
    public String stateDashboardPage = "Dashboard Page";
    public String stateProjectsPage = "Projects Page";
    public String stateAddProjectPage = "Add project Page";
    public String stateAddCustomerPopupOpened = "Customer pop-up opened";
    public String stateProjectAdded = "Project added";
    public String currentState = stateDashboardPage;

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

    public AddProjectModel() {
        this.scripter = new Scripter(System.out);
    }

    @BeforeTest
    public void start() {
        isProjectAdded = false;
        initialActionTested = false;
        coveredSteps.clear();
        currentState = stateDashboardPage;
        int tests = suite.getAllTestCases().size();
        System.out.println("---------------------------------------------------");
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size();
        System.out.println("Total tests generated: "+tests);
    }

    @Guard({"!return_to_projects_page"})
    public boolean allowPreProjectAddedMethods() {
        return !isProjectAdded;
    }

    @Guard("go_to_projects_page")
    public boolean allowGoToProjectsPageFromDashboardOnce() {
        return !initialActionTested;
    }

    @Guard({"go_to_add_project_page"})
    public boolean guardProjectsPageActions() {
        return currentState.equals(stateProjectsPage);
    }

    @Guard({"cancel", "add_project_with_duplicate_project_name", "add_project_with_empty_project_name",
    "add_project_with_empty_customer_name", "add_project_with_nonexistent_customer_name",
    "add_project_with_nonexistent_admin", "add_project_with_duplicate_admins", "open_add_customer_popup",
    "add_project_all_fields_valid_1_admin", "add_project_required_fields_only", "add_project_all_fields_valid_4_admins"})
    public boolean guardAddProjectPageActions() {
        return currentState.equals(stateAddProjectPage);
    }

    @Guard ({"cancel_popup", "add_customer_with_duplicate_name", "add_project_with_newly_added_customer"})
    public boolean guardAddCustomerPopupOpenedActions() {
        return currentState.equals(stateAddCustomerPopupOpened);
    }

    @Guard ("return_to_projects_page")
    public boolean guardProjectAddedActions() {
        return currentState.equals(stateProjectAdded);
    }

    /**
     * Actions from projects page
     */

    @TestStep("go_to_projects_page")
    public void goToProjectsPage() {
        scripter.step("Go to projects page.");
        currentState = stateProjectsPage;
        initialActionTested = true;
        utils.checkList(coveredSteps, "go_to_projects_page");
    }

    @TestStep("go_to_add_project_page")
    public void goToAddProjectPage() {
        scripter.step("Go to add project page.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "go_to_add_project_page");
    }

    @TestStep("cancel")
    public void cancel() {
        scripter.step("Cancel add project.");
        currentState = stateProjectsPage;
        utils.checkList(coveredSteps, "cancel");
    }

    /**
     * Invalid add project cases
     */

    @TestStep("add_project_with_duplicate_project_name")
    public void addProjectWithDuplicateName() {
        scripter.step("Add project with duplicate project name.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_duplicate_project_name");

    }

    @TestStep("add_project_with_empty_project_name")
    public void addProjectWithEmptyProjectName() {
        scripter.step("Add project with empty project name.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_empty_project_name");
    }

    @TestStep("add_project_with_empty_customer_name")
    public void addProjectWithEmptyCustomerName() {
        scripter.step("Add project with empty customer name.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_empty_customer_name");
    }

    @TestStep("add_project_with_nonexistent_customer_name")
    public void addProjectWithNonexistentCustomerName() {
        scripter.step("Add project with nonexistent customer name.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_nonexistent_customer_name");
    }

    @TestStep("add_project_with_nonexistent_admin")
    public void addProjectWithNonexistentAdmin() {
        scripter.step("Add project with nonexistent admin.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_nonexistent_admin");
    }

    @TestStep("add_project_with_duplicate_admins")
    public void addProjectWithDuplicateAdmins() {
        scripter.step("Add project with duplicate admins.");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "add_project_with_duplicate_admins");
    }

    /**
     * Valid add project cases
     */
    @TestStep("add_project_all_fields_valid_1_admin")
    public void addProjectAllFieldsValid1Admin() {
        scripter.step("Add project with all fields valid 1 admin.");
        currentState = stateProjectAdded;
        isProjectAdded = true;
        utils.checkList(coveredSteps, "add_project_all_fields_valid_1_admin");
    }

    @TestStep("open_add_customer_popup")
    public void openAddCustomerPopup() {
        scripter.step("Open add customer pop-up");
        currentState = stateAddCustomerPopupOpened;
        utils.checkList(coveredSteps, "open_add_customer_popup");
    }

    @TestStep("cancel_popup")
    public void cancelPopup() {
        scripter.step("Cancel add customer pop-up");
        currentState = stateAddProjectPage;
        utils.checkList(coveredSteps, "cancel_popup");
    }

    @TestStep("add_customer_with_duplicate_name")
    public void addCustomerWithDuplicateName() {
        scripter.step("Add customer with duplicate name.");
        currentState = stateAddCustomerPopupOpened;
        utils.checkList(coveredSteps, "add_customer_with_duplicate_name");
    }

    @TestStep("add_project_with_newly_added_customer")
    public void addProjectWithNewlyAddedCustomer() {
        scripter.step("Add project with newly added customer.");
        currentState = stateProjectAdded;
        isProjectAdded = true;
        utils.checkList(coveredSteps, "add_project_with_newly_added_customer");
    }

    @TestStep("add_project_required_fields_only")
    public void addProjectRequiredFieldsOnly() {
        scripter.step("Add project with required fields only.");
        currentState = stateProjectAdded;
        isProjectAdded = true;
        utils.checkList(coveredSteps, "add_project_required_fields_only");
    }

    @TestStep("add_project_all_fields_valid_4_admins")
    public void addProjectAllFieldsValid4Admins() {
        scripter.step("Add project with all fields valid 4 admins.");
        currentState = stateProjectAdded;
        isProjectAdded = true;
        utils.checkList(coveredSteps, "add_project_all_fields_valid_4_admins");
    }

    @TestStep("return_to_projects_page")
    public void returnToProjectsPage() {
        scripter.step("Return to projects page.");
        currentState = stateProjectsPage;
        isProjectAdded = false;
        utils.checkList(coveredSteps, "return_to_projects_page");
    }


    @Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }


    public static void main(String[] args) {
        OSMOTester tester = new OSMOTester();
        tester.addModelObject(new AddProjectModel());
        tester.setAlgorithm(new RandomAlgorithm());
        tester.setSuiteEndCondition(new Length(1));

        /**
         * Full step coverage
         */
        UtilsMethods utils = new UtilsMethods();
        ArrayList<String> addProjectExpectedSteps = utils.getAddProjectExpectedSteps();
        StepCoverage steps = new StepCoverage();
        for (String step : addProjectExpectedSteps) {
            steps.addRequiredStep(step);
        }
        tester.setTestEndCondition(steps);


        // test generation command
        tester.generate(System.currentTimeMillis()); //random seed
    }
}
