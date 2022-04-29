package hrapp_models;

import java.util.ArrayList;
import java.util.Collections;

public class UtilsMethods {

    // VARS
    public ArrayList<String> addProjectExpectedSteps = new ArrayList<String>();
    public ArrayList<String> loginExpectedSteps = new ArrayList<String>();
    public ArrayList<String> reviewPerformanceExpectedSteps = new ArrayList<String>();

    // CONSTRUCTOR
    public UtilsMethods() {
        initializeGetAddProjectExpectedSteps();
        initializeLoginExpectedSteps();
        initializeReviewPerformanceExpectedSteps();
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

    public ArrayList<String> getReviewPerformanceExpectedStepsExpectedSteps() {
        return reviewPerformanceExpectedSteps;
    }

    private void initializeGetAddProjectExpectedSteps() {  // 17
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

    private void initializeLoginExpectedSteps() {  // 7
        loginExpectedSteps.add("load_login_page");
        loginExpectedSteps.add("login_empty_username");
        loginExpectedSteps.add("login_empty_password");
        loginExpectedSteps.add("login_invalid_credentials");
        loginExpectedSteps.add("login_disabled_user");
        loginExpectedSteps.add("login_enabled_standard_user");
        loginExpectedSteps.add("logout");
        Collections.sort(loginExpectedSteps);
    }

    private void initializeReviewPerformanceExpectedSteps() {  // 39-40
        reviewPerformanceExpectedSteps.add("go_to_manage_review_page");
        reviewPerformanceExpectedSteps.add("go_to_add_review_page");
        reviewPerformanceExpectedSteps.add("go_back_to_manage_reviews_page");
        reviewPerformanceExpectedSteps.add("fill_valid_employee_name");
        reviewPerformanceExpectedSteps.add("fill_nonexistent_employee_name");
        reviewPerformanceExpectedSteps.add("fill_empty_employee_name");
        reviewPerformanceExpectedSteps.add("fill_invalid_supervisor");
        reviewPerformanceExpectedSteps.add("fill_empty_date_fields");
        reviewPerformanceExpectedSteps.add("fill_end_date_less_than_start_date");
        reviewPerformanceExpectedSteps.add("fill_due_date_less_than_start_date");
        reviewPerformanceExpectedSteps.add("fill_duplicate_review_data");
        reviewPerformanceExpectedSteps.add("fill_empty_supervisor");
        reviewPerformanceExpectedSteps.add("save_with_valid_data");
        reviewPerformanceExpectedSteps.add("activate_with_valid_data");
        reviewPerformanceExpectedSteps.add("go_to_edit_review_page");
        reviewPerformanceExpectedSteps.add("go_back_to_inactive_review_state");
        reviewPerformanceExpectedSteps.add("save_edited_review_with_valid_data");
        reviewPerformanceExpectedSteps.add("edit_empty_date_fields");
        reviewPerformanceExpectedSteps.add("edit_due_date_less_than_start_date");
        reviewPerformanceExpectedSteps.add("edit_end_date_less_than_start_date");
        reviewPerformanceExpectedSteps.add("edit_duplicate_review_data");
        reviewPerformanceExpectedSteps.add("evaluate_review");
        reviewPerformanceExpectedSteps.add("complete_review_with_valid_data_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("go_back_to_activated_review_state");
        reviewPerformanceExpectedSteps.add("save_with_valid_data_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("save_with_invalid_rating_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("complete_with_empty_finalization_fields_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("complete_with_invalid_final_rating_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("complete_with_invalid_completed_date_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("complete_review_with_valid_data_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("complete_with_invalid_completed_date_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("complete_with_invalid_final_rating_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("complete_with_invalid_rating_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("complete_with_empty_finalization_fields_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("cancel_complete_review_popup_from_in_progress_review");
        reviewPerformanceExpectedSteps.add("cancel_complete_review_popup_from_evaluate_review_page");
        reviewPerformanceExpectedSteps.add("confirm_review_completion");
        reviewPerformanceExpectedSteps.add("return_to_manage_reviews_page");

        Collections.sort(reviewPerformanceExpectedSteps);
    }

}
