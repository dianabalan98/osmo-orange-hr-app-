package hrapp_models;
import osmo.tester.OSMOConfiguration;
import osmo.tester.OSMOTester;
import osmo.tester.annotation.*;
import osmo.tester.generator.algorithm.BalancingAlgorithm;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.algorithm.WeightedRandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.LengthProbability;
import osmo.tester.generator.endcondition.structure.ElementCoverage;
import osmo.tester.generator.endcondition.structure.ElementCoverageRequirement;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

import java.io.IOException;
import java.util.ArrayList;

public class ReviewPerformanceModel {

    @Variable

    public String reviewStateInitial = "Not added yet";
    public String reviewStateInactive = "Inactive";
    public String reviewStateActivated = "Active";
    public String reviewStateInProgress = "InProgress";
    public String reviewApproved = "Approved";
    public String currentReviewState;
    public boolean initialActionTested = false;

    // state variable = requirement
    public Requirements reviewPerformanceRequirements = new Requirements();

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


    // other variables
    public TestSuite suite;
    public final Scripter scripter;

    public ReviewPerformanceModel() {
        this.scripter = new Scripter(System.out);
        reviewPerformanceRequirements.add("Dashboard page reached");
        reviewPerformanceRequirements.add("Manage reviews page reached");
        reviewPerformanceRequirements.add("Add review page reached");
        reviewPerformanceRequirements.add("Form step 2 reached");
        reviewPerformanceRequirements.add("Inactive review reached");
        reviewPerformanceRequirements.add("Edit review page reached");
        reviewPerformanceRequirements.add("Activated review reached");
        reviewPerformanceRequirements.add("Evaluate review page reached");
        reviewPerformanceRequirements.add("In progress review reached");
        reviewPerformanceRequirements.add("Complete review pop-up reached");
        reviewPerformanceRequirements.add("Approved review reached");
    }

    @BeforeTest
    public void start() {
        initialActionTested = false;
        currentState = stateDashboardPage;
        reviewPerformanceRequirements.covered("Dashboard page reached");
        currentReviewState = reviewStateInitial;
        int tests = suite.getAllTestCases().size();
        System.out.println("---------------------------------------------------");
        System.out.println("Starting test: "+tests);
    }

    @AfterSuite
    public void done() {
        int tests = suite.getAllTestCases().size();
        //System.out.println("Total tests generated: "+tests);
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

    @Guard({"go_back_to_manage_reviews_page_from_add_review_page", "go_back_to_manage_reviews_page_from_form_step_2"})
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

    @Guard({"activate_with_valid_data_from_form_step_2", "activate_with_valid_data_from_edit_review_page"})
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

    @TestStep(name="go_to_manage_review_page", weight=100)
    public void goToManageReviewPage() {
        scripter.step("Go to manage review page.");
        currentState = stateManageReviewsPage;
        initialActionTested = true;
        reviewPerformanceRequirements.covered("Manage reviews page reached");
    }

    @TestStep(name="go_to_add_review_page", weight=100)
    public void goToAddReviewPage() {
        scripter.step("Go to add review page.");
        currentState = stateAddReviewPage;
        reviewPerformanceRequirements.covered("Add review page reached");
    }

    /**
     * Add review page test steps: 4
     */

    @TestStep(name="go_back_to_manage_reviews_page_from_add_review_page", weight=10)
    public void goBackToManageReviewsPageFromAddReviewPage() {
        scripter.step("Go back to manage reviews page from add review page.");
        currentState = stateManageReviewsPage;
        reviewPerformanceRequirements.covered("Manage reviews page reached");
    }

    @TestStep(name="go_back_to_manage_reviews_page_from_form_step_2", weight=5)
    public void goBackToManageReviewsPageFromFormStep2() {
        scripter.step("Go back to manage reviews page from form step 2.");
        currentState = stateManageReviewsPage;
        reviewPerformanceRequirements.covered("Manage reviews page reached");
    }

    @TestStep(name="fill_valid_employee_name", weight=50)
    public void fillValidEmployeeName() {
        scripter.step("Fill valid employee name.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_nonexistent_employee_name", weight=20)
    public void fillNonexistentEmployeeName() {
        scripter.step("Fill nonexistent employee name.");
        currentState = stateAddReviewPage;
        reviewPerformanceRequirements.covered("Add review page reached");
    }

    @TestStep(name="fill_empty_employee_name", weight=10)
    public void fillEmptyEmployeeName() {
        scripter.step("Fill empty employee name.");
        currentState = stateAddReviewPage;
        reviewPerformanceRequirements.covered("Add review page reached");
    }

    /**
     * Add form step 2 enabled test steps
     */

    @TestStep(name="fill_invalid_supervisor", weight=10)
    public void fillInvalidSupervisor() {
        scripter.step("Fill invalid supervisor.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_empty_date_fields", weight=5) // from stateFormStep2Enabled
    public void fillEmptyDateFields() {
        scripter.step("Fill empty date fields.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_end_date_less_than_start_date", weight=5) // from stateFormStep2Enabled
    public void fillEndDateLessThanStartDate() {
        scripter.step("Fill end date less than start date.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_due_date_less_than_start_date", weight=5) // from stateFormStep2Enabled
    public void fillDueDateLessThanStartDate() {
        scripter.step("Fill due date less than start date.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_duplicate_review_data", weight=5) // from stateFormStep2Enabled
    public void fillDuplicateReviewData() {
        scripter.step("Fill duplicate review data.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="fill_empty_supervisor", weight=5) // from stateFormStep2Enabled
    public void fillEmptySupervisor() {
        scripter.step("Fill empty supervisor.");
        currentState = stateFormStep2Enabled;
        reviewPerformanceRequirements.covered("Form step 2 reached");
    }

    @TestStep(name="save_with_valid_data", weight=35)
    public void saveWithValidData() {
        scripter.step("Save with valid data.");
        currentState = stateInactiveReview;
        currentReviewState = reviewStateInactive;
        reviewPerformanceRequirements.covered("Inactive review reached");
    }

    @TestStep(name="activate_with_valid_data_from_form_step_2", weight=25)
    public void activateWithValidDataFromFormStep2() {
        scripter.step("Activate with valid data from form step 2.");
        currentState = stateActivatedReview;
        currentReviewState = reviewStateActivated;
        reviewPerformanceRequirements.covered("Activated review reached");
    }

    @TestStep(name="activate_with_valid_data_from_edit_review_page", weight=30)
    public void activateWithValidDataFromEditReviewPage() {
        scripter.step("Activate with valid data from edit review page.");
        currentState = stateActivatedReview;
        currentReviewState = reviewStateActivated;
        reviewPerformanceRequirements.covered("Activated review reached");
    }

    /**
     * Inactive review test steps
     */

    @TestStep(name="go_to_edit_review_page", weight=100)
    public void goToEditReviewPage() {
        scripter.step("Go to edit review page.");
        currentState = stateEditReviewPage;
        reviewPerformanceRequirements.covered("Edit review page reached");
    }

    /**
     * Edit review page test steps
     */

    @TestStep(name="go_back_to_inactive_review_state", weight=10)
    public void goBackToInactiveReviewState() {
        scripter.step("Go back to inactive review state.");
        currentState = stateInactiveReview;
        reviewPerformanceRequirements.covered("Inactive review reached");
    }

    @TestStep(name="save_edited_review_with_valid_data", weight=20)
    public void saveEditedReviewWithValidData() {
        scripter.step("Save edited review with valid data.");
        currentState = stateInactiveReview;
        reviewPerformanceRequirements.covered("Inactive review reached");
    }

    @TestStep(name="edit_empty_date_fields", weight=10)
    public void editEmptyDateFields() {
        scripter.step("Edit with empty date fields.");
        currentState = stateEditReviewPage;
        reviewPerformanceRequirements.covered("Edit review page reached");
    }

    @TestStep(name="edit_due_date_less_than_start_date", weight=10)
    public void editDueDateLessThanStartDate() {
        scripter.step("Edit due date less than start date.");
        currentState = stateEditReviewPage;
        reviewPerformanceRequirements.covered("Edit review page reached");
    }

    @TestStep(name="edit_end_date_less_than_start_date", weight=10)
    public void editEndDateLessThanStartDate() {
        scripter.step("Edit end date less than start date.");
        currentState = stateEditReviewPage;
        reviewPerformanceRequirements.covered("Edit review page reached");
    }

    @TestStep(name="edit_duplicate_review_data", weight=10)
    public void editDuplicateReviewData() {
        scripter.step("Edit duplicate review data.");
        currentState = stateEditReviewPage;
        reviewPerformanceRequirements.covered("Edit review page reached");
    }

    /**
     * Activated review test steps
     */

    @TestStep(name="evaluate_review", weight=100)
    public void evaluateReview() {
        scripter.step("Evaluate review.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    /**
     * Evaluate review page test steps
     */

    @TestStep(name="complete_review_with_valid_data_from_evaluate_review_page", weight=20)
    public void completeReviewWithValidDataFromEvaluateReviewPage() {
        scripter.step("Complete review with valid data from evaluate review page.");
        currentState = stateCompleteReviewPopupOpened;
        reviewPerformanceRequirements.covered("Complete review pop-up reached");
    }

    @TestStep(name="go_back_to_activated_review_state", weight=10)
    public void goBackToActivatedReviewState() {
        scripter.step("Go back to activated review state.");
        currentState = stateActivatedReview;
        reviewPerformanceRequirements.covered("Activated review reached");
    }

    @TestStep(name="save_with_valid_data_from_evaluate_review_page", weight=30)
    public void saveWithValidDataFromEvaluateReviewPage() {
        scripter.step("Save with valid data from evaluate review page.");
        currentState = stateReviewInProgress;
        currentReviewState = reviewStateInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    @TestStep(name="save_with_invalid_rating_from_evaluate_review_page", weight=10)
    public void saveWithInvalidRatingFromEvaluateReviewPage() {
        scripter.step("Save with invalid rating from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    @TestStep(name="complete_with_empty_finalization_fields_from_evaluate_review_page", weight=10)
    public void completeWithEmptyFinalizationFieldsFromEvaluateReviewPage() {
        scripter.step("Complete with empty finalization fields from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    @TestStep(name="complete_with_invalid_final_rating_from_evaluate_review_page", weight=10)
    public void completeWithInvalidFinalRatingFromEvaluateReviewPage() {
        scripter.step("Complete with invalid final rating from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    @TestStep(name="complete_with_invalid_completed_date_from_evaluate_review_page", weight=10)
    public void completeWithInvalidCompletedDateFromEvaluateReviewPage() {
        scripter.step("Complete with invalid completed date from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    /**
     * Review in progress test steps
     */

    @TestStep(name="complete_review_with_valid_data_from_in_progress_review", weight=60)
    public void completeReviewWithValidDataFromInProgressReview() {
        scripter.step("Complete review with valid data from in progress review.");
        currentState = stateCompleteReviewPopupOpened;
        reviewPerformanceRequirements.covered("Complete review pop-up reached");
    }

    @TestStep(name="complete_with_invalid_completed_date_from_in_progress_review", weight=10)
    public void completeReviewWithInvalidCompletedDateFromInProgressReview() {
        scripter.step("Complete review with invalid completed date from in progress review.");
        currentState = stateReviewInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    @TestStep(name="complete_with_invalid_final_rating_from_in_progress_review", weight=10)
    public void completeReviewWithInvalidFinalRatingFromInProgressReview() {
        scripter.step("Complete review with invalid final rating from in progress review.");
        currentState = stateReviewInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    @TestStep(name="complete_with_invalid_rating_from_in_progress_review", weight=10)
    public void completeReviewWithInvalidRatingFromInProgressReview() {
        scripter.step("Complete review with invalid rating from in progress review.");
        currentState = stateReviewInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    @TestStep(name="complete_with_empty_finalization_fields_from_in_progress_review", weight=10)
    public void completeReviewWithEmptyFinalizationFieldsFromInProgressReview() {
        scripter.step("Complete review with empty finalization fields from in progress review.");
        currentState = stateReviewInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    /**
     * Complete review popup opened test steps
     */
    @TestStep(name="cancel_complete_review_popup_from_in_progress_review", weight=20)
    public void cancelCompleteReviewPopupFromInProgressReview() {
        scripter.step("Cancel complete review popup from in progress review.");
        currentState = stateReviewInProgress;
        reviewPerformanceRequirements.covered("In progress review reached");
    }

    @TestStep(name="cancel_complete_review_popup_from_evaluate_review_page", weight=20)
    public void cancelCompleteReviewPopupFromEvaluateReviewPage() {
        scripter.step("Cancel complete review popup from evaluate review page.");
        currentState = stateEvaluateReviewPage;
        reviewPerformanceRequirements.covered("Evaluate review page reached");
    }

    @TestStep(name="confirm_review_completion", weight=60)
    public void confirmReviewCompletion() {
        scripter.step("Confirm review completion.");
        currentState = stateReviewApproved;
        currentReviewState = reviewApproved;
        reviewPerformanceRequirements.covered("Approved review reached");
    }

    /**
     * Approved review test steps
     */

    @TestStep(name="return_to_manage_reviews_page", weight=100)
    public void returnToManageReviewsPage() {
        scripter.step("Return to manage reviews page.");
        currentState = stateManageReviewsPage;
        currentReviewState = reviewStateInitial;
        reviewPerformanceRequirements.covered("Manage reviews page reached");
    }

    /*@Post("all")
    public void checkState() {
        scripter.step("CURRENT STATE: "+currentState+".");
    }*/

    public static void main(String[] args) throws IOException {

        UtilsMethods utils = new UtilsMethods();
        ElementCoverageRequirement req;
        /*ReviewPerformanceModel rpModel = new ReviewPerformanceModel();
        OSMOTester tester = new OSMOTester();
        tester.addModelObject(rpModel);
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
        ArrayList<String> reviewPerformanceExpectedSteps = utils.getReviewPerformanceExpectedSteps();
        StepCoverage steps = new StepCoverage();
        for (String step : reviewPerformanceExpectedSteps) {
            steps.addRequiredStep(step);
        }
        tester.setTestEndCondition(steps);*/

        /**
         * Full state coverage (requirements)
         */
        /*req = new ElementCoverageRequirement(0, 0, rpModel.reviewPerformanceRequirements.getRequirements().size());
        tester.setSuiteEndCondition(new ElementCoverage(req));*/

        /**
         * Random reached step
         */
        //tester.setTestEndCondition(new StepCoverage("save_with_invalid_rating_from_evaluate_review_page"));


        // test generation command
        //tester.generate(System.currentTimeMillis()); //random seed


        String pathReachedStep = "ReviewPerformanceTestCases\\random_reached_step_save_with_invalid_rating";
        String pathStepCoverage = "ReviewPerformanceTestCases\\random_step_coverage_100";
        String pathStateCoverage = "ReviewPerformanceTestCases\\random_state_coverage_100";
        String pathStepAndStateCoverage = "ReviewPerformanceTestCases\\random_step_and_state_coverage_100";

        String pathWeightedReachedStep = "ReviewPerformanceTestCases\\weighted_random_reached_step_save_with_invalid_rating";
        String pathWeightedStepCoverage = "ReviewPerformanceTestCases\\weighted_random_step_coverage_100";
        String pathWeightedStateCoverage = "ReviewPerformanceTestCases\\weighted_random_state_coverage_100";
        String pathWeightedStepAndStateCoverage = "ReviewPerformanceTestCases\\weighted_random_step_and_state_coverage_100";

        String balancingAlgoReachedStep = "ReviewPerformanceTestCases\\balancing_algorithm_reached_step_save_with_invalid_rating";
        String balancingAlgoStepCoverage = "ReviewPerformanceTestCases\\balancing_algorithm_step_coverage_100";
        String balancingAlgoStateCoverage = "ReviewPerformanceTestCases\\balancing_algorithm_state_coverage_100";

        String path = balancingAlgoStateCoverage;

        // create new folder in the given location with the given folder name
        utils.createNewTestOutputDirectory(path);
        // create CSV file for metrics
        utils.initializeCSVFile(path);

        for(int i=1; i<=100; i++) {
            OSMOTester tester = new OSMOTester();
            tester.addModelObject(new ReviewPerformanceModel());
            tester.setSuiteEndCondition(new Length(1));
            //tester.setAlgorithm(new RandomAlgorithm());
            //tester.setAlgorithm(new WeightedRandomAlgorithm());
            tester.setAlgorithm(new BalancingAlgorithm());

            // reached step
            //tester.setTestEndCondition(new StepCoverage("save_with_invalid_rating_from_evaluate_review_page"));

            //full step coverage
            /*ArrayList<String> reviewPerformanceExpectedSteps = utils.getReviewPerformanceExpectedSteps();
            StepCoverage steps = new StepCoverage();
            for (String step : reviewPerformanceExpectedSteps) {
                steps.addRequiredStep(step);
            }
            tester.setTestEndCondition(steps);*/

            // full state coverage
            req = new ElementCoverageRequirement(0, 0, new ReviewPerformanceModel().reviewPerformanceRequirements.getRequirements().size());
            tester.setSuiteEndCondition(new ElementCoverage(req));


            utils.generateAndSaveOsmoOutput2(i, tester, path);
        }

    }
}
