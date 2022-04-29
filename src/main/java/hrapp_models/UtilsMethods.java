package hrapp_models;

import java.util.ArrayList;
import java.util.Collections;

public class UtilsMethods {

    // VARS
    public ArrayList<String> addProjectExpectedSteps = new ArrayList<String>();
    public ArrayList<String> loginExpectedSteps = new ArrayList<String>();

    // CONSTRUCTOR
    public UtilsMethods() {
        initializeGetAddProjectExpectedSteps();
        initializeLoginExpectedSteps();
    }

    // METHODS

    public void checkList(ArrayList<String> testStepsList, String testStep) {
        if (!testStepsList.contains(testStep)) {
            testStepsList.add(testStep);
        }
    }

    public ArrayList<String> getAddProjectExpectedSteps() {
        return addProjectExpectedSteps;
    }

    public ArrayList<String> getLoginExpectedSteps() {
        return loginExpectedSteps;
    }

    private void initializeGetAddProjectExpectedSteps() {
        addProjectExpectedSteps.add("go_to_projects_page");
        addProjectExpectedSteps.add("go_to_add_project_page");
        addProjectExpectedSteps.add("cancel");
        addProjectExpectedSteps.add("add_project_with_duplicate_project_name");
        addProjectExpectedSteps.add("add_project_with_empty_project_name");
        addProjectExpectedSteps.add("add_project_with_empty_customer_name");
        addProjectExpectedSteps.add("add_project_with_nonexistent_customer_name");
        addProjectExpectedSteps.add("add_project_with_nonexistent_admin");
        addProjectExpectedSteps.add("add_project_with_duplicate_admins");
        addProjectExpectedSteps.add("add_project_all_fields_valid_1_admin");
        addProjectExpectedSteps.add("open_add_customer_popup");
        addProjectExpectedSteps.add("cancel_popup");
        addProjectExpectedSteps.add("add_customer_with_duplicate_name");
        addProjectExpectedSteps.add("add_project_with_newly_added_customer");
        addProjectExpectedSteps.add("add_project_required_fields_only");
        addProjectExpectedSteps.add("add_project_all_fields_valid_4_admins");
        addProjectExpectedSteps.add("return_to_projects_page");
        Collections.sort(addProjectExpectedSteps);
    }

    private void initializeLoginExpectedSteps() {
        addProjectExpectedSteps.add("load_login_page");
        addProjectExpectedSteps.add("login_empty_username");
        addProjectExpectedSteps.add("login_empty_password");
        addProjectExpectedSteps.add("login_invalid_credentials");
        addProjectExpectedSteps.add("login_disabled_user");
        addProjectExpectedSteps.add("login_enabled_standard_user");
        addProjectExpectedSteps.add("logout");
        Collections.sort(addProjectExpectedSteps);
    }

}
