package hrapp_models;
import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.testsuite.TestSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class ReviewPerformanceModel {

    @Variable

    public String reviewStateInitial = "Not added yet";
    public String reviewStateInactive = "Inactive";
    public String reviewStateActivated = "Active";
    public String reviewStateInProgress = "InProgress";
    public String reviewApproved = "Approved";
    public String currentReviewState;
    public boolean initialActionTested = false;

    // state guard variables
    public String stateDashboardPage = "Dashboard page";
    public String stateManageReviewsPage = "Manage reviews page";
    public String stateAddReviewPage = "Add review page";
    public String stateFormStep2Enabled = "Add review page - form step 2 enabled";
    public String stateInactiveReview = "Inactive review";
    public String stateEditReviewPage = "Edit review page";
    public String stateActivatedReview = "Activated review";
    public String stateEvaluateReviewPage = "Evaluate review page";
    public String stateReviewInProgress = "Review in progress";
    public String stateCompleteReviewPopupOpened = "Complete review pop-up opened";
    public String stateReviewApproved = "Approved review";

    public String currentState = stateDashboardPage;

    // time vars
    public long startTime = -1;
    public long endTime = -1;
    public long timeElapsed = -1;

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

    public ReviewPerformanceModel() {
        this.scripter = new Scripter(System.out);
    }

    @BeforeTest
    public void start() {
        initialActionTested = false;
        startTime = System.currentTimeMillis();
        endTime = -1;
        timeElapsed = -1;
        coveredSteps.clear();
        currentState = stateDashboardPage;
        currentReviewState = reviewStateInitial;
        int tests = suite.getAllTestCases().size();
        System.out.println("---------------------------------------------------");
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size();
        System.out.println("Total tests generated: "+tests);
    }

    // GUARDS
    @Guard("go_to_manage_review_page")
    public boolean allowGoToManageReviewPageFromDashboardOnce() {
        return !initialActionTested && currentReviewState.equals(reviewStateInitial);
    }

    @Guard("go_to_add_review_page")
    public boolean guardManageReviewsPageActions() {
        return currentState.equals(stateManageReviewsPage) && currentReviewState.equals(reviewStateInitial);
    }

    @Guard({"fill_valid_employee_name", "fill_nonexistent_employee_name", "fill_empty_employee_name"})
    public boolean guardAddReviewPageActions() {
        return currentState.equals(stateAddReviewPage) && currentReviewState.equals(reviewStateInitial);
    }

    @Guard({"go_back_to_manage_reviews_page"})
    public boolean guardGoBackToManageReviewsPageAction() {
        return (currentState.equals(stateAddReviewPage) || currentState.equals(stateFormStep2Enabled)) &&
                currentReviewState.equals(reviewStateInitial);
    }

    @Guard({"fill_invalid_supervisor", "fill_empty_date_fields", "fill_end_date_less_than_start_date",
    "fill_due_date_less_than_start_date", "fill_duplicate_review_data", "fill_empty_supervisor",
    "save_with_valid_data"})
    public boolean guardFormStep2EnabledActions() {
        return currentState.equals(stateFormStep2Enabled) && currentReviewState.equals(reviewStateInitial);
    }

    @Guard("activate_with_valid_data")
    public boolean guardActivateWithValidDataAction() {
        return (currentState.equals(stateFormStep2Enabled) || currentState.equals(stateEditReviewPage));
    }

    @Guard("go_to_edit_review_page")
    public boolean guardInactiveReviewActions() {
        return currentState.equals(stateInactiveReview) && currentReviewState.equals(reviewStateInactive);
    }

    @Guard({"go_back_to_inactive_review_state", "save_edited_review_with_valid_data", "edit_empty_date_fields",
            "edit_due_date_less_than_start_date", "edit_end_date_less_than_start_date", "edit_duplicate_review_data"})
    public boolean guardEditReviewPageActions() {
        return currentState.equals(stateEditReviewPage) && currentReviewState.equals(reviewStateInactive);
    }

    @Guard("evaluate_review")
    public boolean guardActivatedReviewActions() {
        return currentState.equals(stateActivatedReview);
    }

    @Guard({"complete_review_with_valid_data_from_evaluate_review_page", "go_back_to_activated_review_state",
            "save_with_valid_data_from_evaluate_review_page", "save_with_invalid_rating_from_evaluate_review_page",
            "complete_with_empty_finalization_fields_from_evaluate_review_page", "complete_with_invalid_final_rating_from_evaluate_review_page",
            "complete_with_invalid_completed_date_from_evaluate_review_page"})
    public boolean guardEvaluateReviewPageActions() {
        return currentState.equals(stateEvaluateReviewPage) && currentReviewState.equals(reviewStateActivated);
    }

    @Guard({"complete_review_with_valid_data_from_in_progress_review", "complete_with_invalid_completed_date_from_in_progress_review",
            "complete_with_invalid_final_rating_from_in_progress_review", "complete_with_invalid_rating_from_in_progress_review",
            "complete_with_empty_finalization_fields_from_in_progress_review"})
    public boolean guardInProgressReviewActions() {
        return currentState.equals(stateReviewInProgress) && currentReviewState.equals(reviewStateInProgress);
    }

    @Guard({"cancel_complete_review_popup_from_in_progress_review", "cancel_complete_review_popup_from_evaluate_review_page",
            "confirm_review_completion"})
    public boolean guardCompleteReviewPopupActions() {
        return currentState.equals(stateCompleteReviewPopupOpened);
    }

    @Guard({"cancel_complete_review_popup_from_in_progress_review"})
    public boolean guardCancelPopupFromInProgress() {
        return currentReviewState.equals(reviewStateInProgress);
    }

    @Guard({"cancel_complete_review_popup_from_evaluate_review_page"})
    public boolean guardCancelPopupFromEvaluateReviewPage() {
        return currentReviewState.equals(reviewStateActivated);
    }

    @Guard("return_to_manage_reviews_page")
    public boolean guardApprovedReviewActions() {
        return currentState.equals(stateReviewApproved) && currentReviewState.equals(reviewApproved);
    }

    // TEST STEPS

    @TestStep("go_to_manage_review_page")
    public void goToManageReviewPage() {
        scripter.step("Go to manage review page.");
        currentState = stateManageReviewsPage;
        initialActionTested = true;
        utils.checkList(coveredSteps, "go_to_manage_review_page");
    }

    @TestStep("go_to_add_review_page")
    public void goToAddReviewPage() {
        scripter.step("Go to add review page.");
        currentState = stateAddReviewPage;
        utils.checkList(coveredSteps, "go_to_add_review_page");
    }

    /**
     * Add review page test steps: 4
     */

    @TestStep("go_back_to_manage_reviews_page")  // from stateAddReviewPage & stateFormStep2Enabled
    public void goBackToManageReviewsPage() {
        scripter.step("Go back to manage reviews page.");
        currentState = stateManageReviewsPage;
        utils.checkList(coveredSteps, "go_back_to_manage_reviews_page");
    }

    @TestStep("fill_valid_employee_name")
    public void fillValidEmployeeName() {
        scripter.step("Fill valid employee name.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_valid_employee_name");
    }

    @TestStep("fill_nonexistent_employee_name")
    public void fillNonexistentEmployeeName() {
        scripter.step("Fill nonexistent employee name.");
        currentState = stateAddReviewPage;
        utils.checkList(coveredSteps, "fill_nonexistent_employee_name");
    }

    @TestStep("fill_empty_employee_name")
    public void fillEmptyEmployeeName() {
        scripter.step("Fill empty employee name.");
        currentState = stateAddReviewPage;
        utils.checkList(coveredSteps, "fill_empty_employee_name");
    }

    /**
     * Add form step 2 enabled test steps
     */

    @TestStep("fill_invalid_supervisor")
    public void fillInvalidSupervisor() {
        scripter.step("Fill invalid supervisor.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_invalid_supervisor");
    }

    @TestStep("fill_empty_date_fields") // from stateFormStep2Enabled
    public void fillEmptyDateFields() {
        scripter.step("Fill empty date fields.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_empty_date_fields");
    }

    @TestStep("fill_end_date_less_than_start_date") // from stateFormStep2Enabled
    public void fillEndDateLessThanStartDate() {
        scripter.step("Fill end date less than start date.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_end_date_less_than_start_date");
    }

    @TestStep("fill_due_date_less_than_start_date") // from stateFormStep2Enabled
    public void fillDueDateLessThanStartDate() {
        scripter.step("Fill due date less than start date.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_due_date_less_than_start_date");
    }

    @TestStep("fill_duplicate_review_data") // from stateFormStep2Enabled
    public void fillDuplicateReviewData() {
        scripter.step("Fill duplicate review data.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_duplicate_review_data");
    }

    @TestStep("fill_empty_supervisor") // from stateFormStep2Enabled
    public void fillEmptySupervisor() {
        scripter.step("Fill empty supervisor.");
        currentState = stateFormStep2Enabled;
        utils.checkList(coveredSteps, "fill_empty_supervisor");
    }

    @TestStep("save_with_valid_data")
    public void saveWithValidData() {
        scripter.step("Save with valid data.");
        currentState = stateInactiveReview;
        currentReviewState = reviewStateInactive;
        utils.checkList(coveredSteps, "save_with_valid_data");
    }

    @TestStep("activate_with_valid_data") // from stateFormStep2Enabled or stateEditReviewPage
    public void activateWithValidData() {
        scripter.step("Activate with valid data.");
        currentState = stateActivatedReview;
        currentReviewState = reviewStateActivated;
        utils.checkList(coveredSteps, "activate_with_valid_data");
    }

    /**
     * Inactive review test steps
     */

    @TestStep("go_to_edit_review_page")
    public void goToEditReviewPage() {
        scripter.step("Go to edit review page.");
        currentState = stateEditReviewPage;
        utils.checkList(coveredSteps, "go_to_edit_review_page");
    }

    /**
     * Edit review page test steps
     */

    @TestStep("go_back_to_inactive_review_state")
    public void goBackToInactiveReviewState() {
        scripter.step("Go back to inactive review state.");
        currentState = stateInactiveReview;
        utils.checkList(coveredSteps, "go_back_to_inactive_review_state");
    }

    @TestStep("save_edited_review_with_valid_data")
    public void saveEditedReviewWithValidData() {
        scripter.step("Save edited review with valid data.");
        currentState = stateInactiveReview;
        utils.checkList(coveredSteps, "save_edited_review_with_valid_data");
    }

    @TestStep("edit_empty_date_fields")
    public void editEmptyDateFields() {
        scripter.step("Edit with empty date fields.");
        currentState = stateEditReviewPage;
        utils.checkList(coveredSteps, "edit_empty_date_fields");
    }

    @TestStep("edit_due_date_less_than_start_date")
    public void editDueDateLessThanStartDate() {
        scripter.step("Edit due date less than start date.");
        currentState = stateEditReviewPage;
        utils.checkList(coveredSteps, "edit_due_date_less_than_start_date");
    }

    @TestStep("edit_end_date_less_than_start_date")
    public void editEndDateLessThanStartDate() {
        scripter.step("Edit end date less than start date.");
        currentState = stateEditReviewPage;
        utils.checkList(coveredSteps, "edit_end_date_less_than_start_date");
    }

    @TestStep("edit_duplicate_review_data")
    public void editDuplicateReviewData() {
        scripter.step("Edit duplicate review data.");
        currentState = stateEditReviewPage;
        utils.checkList(coveredSteps, "edit_duplicate_review_data");
    }

    /**
     * Activated review test steps
     */

    @TestStep("evaluate_review")
    public void evaluateReview() {
        scripter.step("Evaluate review.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "evaluate_review");
    }

    /**
     * Evaluate review page test steps
     */

    @TestStep("complete_review_with_valid_data_from_evaluate_review_page")
    public void completeReviewWithValidDataFromEvaluateReviewPage() {
        scripter.step("Complete review with valid data from evaluate review page.");
        currentState = stateCompleteReviewPopupOpened;
        utils.checkList(coveredSteps, "complete_review_with_valid_data_from_evaluate_review_page");
    }

    @TestStep("go_back_to_activated_review_state")
    public void goBackToActivatedReviewState() {
        scripter.step("Go back to activated review state.");
        currentState = stateActivatedReview;
        utils.checkList(coveredSteps, "go_back_to_activated_review_state");
    }

    @TestStep("save_with_valid_data_from_evaluate_review_page")
    public void saveWithValidDataFromEvaluateReviewPage() {
        scripter.step("Save with valid data from evaluate review page.");
        currentState = stateReviewInProgress;
        currentReviewState = reviewStateInProgress;
        utils.checkList(coveredSteps, "save_with_valid_data_from_evaluate_review_page");
    }

    @TestStep("save_with_invalid_rating_from_evaluate_review_page")
    public void saveWithInvalidRatingFromEvaluateReviewPage() {
        scripter.step("Save with invalid rating from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "save_with_invalid_rating_from_evaluate_review_page");
    }

    @TestStep("complete_with_empty_finalization_fields_from_evaluate_review_page")
    public void completeWithEmptyFinalizationFieldsFromEvaluateReviewPage() {
        scripter.step("Complete with empty finalization fields from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "complete_with_empty_finalization_fields_from_evaluate_review_page");
    }

    @TestStep("complete_with_invalid_final_rating_from_evaluate_review_page")
    public void completeWithInvalidFinalRatingFromEvaluateReviewPage() {
        scripter.step("Complete with invalid final rating from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "complete_with_invalid_final_rating_from_evaluate_review_page");
    }

    @TestStep("complete_with_invalid_completed_date_from_evaluate_review_page")
    public void completeWithInvalidCompletedDateFromEvaluateReviewPage() {
        scripter.step("Complete with invalid completed date from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "complete_with_invalid_completed_date_from_evaluate_review_page");
    }

    /**
     * Review in progress test steps
     */

    @TestStep("complete_review_with_valid_data_from_in_progress_review")
    public void completeReviewWithValidDataFromInProgressReview() {
        scripter.step("Complete review with valid data from in progress review.");
        currentState = stateCompleteReviewPopupOpened;
        utils.checkList(coveredSteps, "complete_review_with_valid_data_from_in_progress_review");
    }

    @TestStep("complete_with_invalid_completed_date_from_in_progress_review")
    public void completeReviewWithInvalidCompletedDateFromInProgressReview() {
        scripter.step("Complete review with invalid completed date from in progress review.");
        currentState = stateReviewInProgress;
        utils.checkList(coveredSteps, "complete_with_invalid_completed_date_from_in_progress_review");
    }

    @TestStep("complete_with_invalid_final_rating_from_in_progress_review")
    public void completeReviewWithInvalidFinalRatingFromInProgressReview() {
        scripter.step("Complete review with invalid final rating from in progress review.");
        currentState = stateReviewInProgress;
        utils.checkList(coveredSteps, "complete_with_invalid_final_rating_from_in_progress_review");
    }

    @TestStep("complete_with_invalid_rating_from_in_progress_review")
    public void completeReviewWithInvalidRatingFromInProgressReview() {
        scripter.step("Complete review with invalid rating from in progress review.");
        currentState = stateReviewInProgress;
        utils.checkList(coveredSteps, "complete_with_invalid_rating_from_in_progress_review");
    }

    @TestStep("complete_with_empty_finalization_fields_from_in_progress_review")
    public void completeReviewWithEmptyFinalizationFieldsFromInProgressReview() {
        scripter.step("Complete review with empty finalization fields from in progress review.");
        currentState = stateReviewInProgress;
        utils.checkList(coveredSteps, "complete_with_empty_finalization_fields_from_in_progress_review");
    }

    /**
     * Complete review popup opened test steps
     */
    @TestStep("cancel_complete_review_popup_from_in_progress_review")
    public void cancelCompleteReviewPopupFromInProgressReview() {
        scripter.step("Cancel complete review popup from in progress review.");
        currentState = stateReviewInProgress;
        utils.checkList(coveredSteps, "cancel_complete_review_popup_from_in_progress_review");
    }

    @TestStep("cancel_complete_review_popup_from_evaluate_review_page")
    public void cancelCompleteReviewPopupFromEvaluateReviewPage() {
        scripter.step("Cancel complete review popup from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        utils.checkList(coveredSteps, "cancel_complete_review_popup_from_evaluate_review_page");
    }

    @TestStep("confirm_review_completion")
    public void confirmReviewCompletion() {
        scripter.step("Confirm review completion.");
        currentState = stateReviewApproved;
        currentReviewState = reviewApproved;
        utils.checkList(coveredSteps, "confirm_review_completion");
    }

    /**
     * Approved review test steps
     */

    @TestStep("return_to_manage_reviews_page")
    public void returnToManageReviewsPage() {
        scripter.step("Return to manage reviews page.");
        currentState = stateManageReviewsPage;
        currentReviewState = reviewStateInitial;
        utils.checkList(coveredSteps, "return_to_manage_reviews_page");
    }

    @Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }

    @EndCondition
    public boolean end() {
        scripter.step("TOTAL covered steps: " +coveredSteps.size()+ " | ELEMENTS: " +coveredSteps.toString());
        Collections.sort(coveredSteps);
        if(utils.getReviewPerformanceExpectedStepsExpectedSteps().equals(coveredSteps)) {
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            scripter.step("TOTAL TIME FOR TEST: " + TimeUnit.MILLISECONDS.toSeconds(timeElapsed));
            return true;
        }
        return false;
    }



    public static void main(String[] args) {
        OSMOConfiguration config = new OSMOConfiguration();
        OSMOTester tester = new OSMOTester();
        tester.addModelObject(new ReviewPerformanceModel());
        tester.setAlgorithm(new RandomAlgorithm());
        config.setSuiteEndCondition(new Length(10));
        tester.generate(System.currentTimeMillis());
    }
}
