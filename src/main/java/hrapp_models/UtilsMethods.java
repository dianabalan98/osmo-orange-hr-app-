package hrapp_models;

import osmo.tester.OSMOTester;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.endcondition.Length;
import osmo.tester.generator.endcondition.structure.StepCoverage;
import osmo.tester.model.Requirements;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsMethods {

    // VARS
    private ArrayList<String> loginExpectedSteps = new ArrayList<>();
    private ArrayList<String> loginStates = new ArrayList<>();
    private ArrayList<String> addProjectExpectedSteps = new ArrayList<>();
    private ArrayList<String> addProjectStates = new ArrayList<>();
    private ArrayList<String> reviewPerformanceExpectedSteps = new ArrayList<>();
    private ArrayList<String> reviewPerformanceStates = new ArrayList<>();
    public String outputPath = "C:\\osmo_disertatie\\osmo_dissertation_hrapp\\src\\main\\java\\hrapp_models\\testcases\\";

    // CONSTRUCTOR
    public UtilsMethods() {
        initializeGetAddProjectExpectedSteps();
        initializeLoginExpectedSteps();
        initializeReviewPerformanceExpectedSteps();
        initializeLoginStates();
        initializeAddProjectStates();
        initializeReviewPerformanceStates();
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

    public ArrayList<String> getReviewPerformanceExpectedSteps() {
        return reviewPerformanceExpectedSteps;
    }

    public ArrayList<String> getLoginStates() {
        return loginStates;
    }

    public ArrayList<String> getAddProjectStates() { return addProjectStates; }

    public ArrayList<String> getReviewPerformanceStates() { return reviewPerformanceStates; }

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
        reviewPerformanceExpectedSteps.add("go_back_to_manage_reviews_page_from_add_review_page");
        reviewPerformanceExpectedSteps.add("go_back_to_manage_reviews_page_from_form_step_2");
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
        reviewPerformanceExpectedSteps.add("activate_with_valid_data_from_form_step_2");
        reviewPerformanceExpectedSteps.add("activate_with_valid_data_from_edit_review_page");
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

    public void initializeLoginStates() {
        loginStates.add("Login page reached");
        loginStates.add("Dashboard page reached");
    }

    public void initializeAddProjectStates() {

    }

    public void initializeReviewPerformanceStates() {

    }


    /**
     * Create new directory
     */
    public void createNewTestOutputDirectory(String folderPath) {
        new File(outputPath + folderPath).mkdirs();
    }

    /**
     * Export metrics results as csv file
     */
    public void initializeCSVFile(String path) throws IOException {

        String filePath = outputPath + "\\" + path + "\\metrics.csv";
        File file = new File(filePath);
        file.createNewFile(); // if file already exists will do nothing

        try {
            PrintWriter pw = new PrintWriter(filePath);
            // Header column values
            StringBuilder builder = new StringBuilder();
            String columns = "Id, Generation time, Nr of test steps";
            builder.append(columns + "\n");
            pw.write(builder.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addValuesToCSVFile(String path, String id, String time, String steps) throws IOException {

        try {
            File file =new File(outputPath + "\\" + path + "\\metrics.csv");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.print(id + "," + time + "," + steps);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirect OSMO output to PrintStream and return the result as string
     */
    public String redirectOutputToString(OSMOTester tester) {

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        // Print some output: goes to your special stream

        // GENERATE TEST CASES
        tester.generate(System.currentTimeMillis()); //random seed

        String result = baos.toString();
        // Put things back
        System.out.flush();
        System.setOut(old);

        return result;
    }

    /**
     * Export results as .txt files
     */
    public void saveResultsToTxtFile(String text, String path, String fileName) throws IOException {

        FileWriter fileWriter = new FileWriter(outputPath + "\\" + path + "\\" + fileName + ".txt");
        PrintWriter out = new PrintWriter(fileWriter);
        out.print(text);
        out.close();

    }

    /**
     * Extract test steps from String output
     */
    public String extractTestSteps(String text) {

        String result = text.substring(text.indexOf("Total steps: ") + 13);
        result = result.substring(0, result.indexOf("Unique steps:"));

        return result;
    }

    /**
     * Run OSMO generator x times and save OSMO output
     */
    public void generateAndSaveOsmoOutput(int nrOfTests, OSMOTester tester, String folderPath) throws IOException {

        // create new folder in the given location with the given folder name
        createNewTestOutputDirectory(folderPath);

        // create CSV file for metrics
        initializeCSVFile(folderPath);

        // start FOR CYCLE up until given number of iterations (e.g. 1 -> 100)
        for(int i = 1; i<=nrOfTests; i++) {

            // start time initialization
            long startTime = System.nanoTime();

            // generate test cases from OSMO tester object + redirect output to a string to be returned
            String osmoResults = redirectOutputToString(tester);

            // calculate total time
            long endTime = System.nanoTime();
            long executionTime = TimeUnit.MILLISECONDS.convert(Duration.ofNanos(endTime - startTime));

            // extract total steps value from OSMO output string
            String testSteps = extractTestSteps(osmoResults);

            // add current id + total time + total steps to metrics csv file, separated by commas
            addValuesToCSVFile(folderPath, String.valueOf(i), String.valueOf(executionTime), String.valueOf(testSteps));

            // save test steps in txt file in the new folder from the OSMO string results
            saveResultsToTxtFile(osmoResults, folderPath, "test" + i);

        }
    }

    public void generateAndSaveOsmoOutput2(int nrOfTests, OSMOTester tester, String folderPath) throws IOException {

        // start time initialization
        long startTime = System.nanoTime();

        // generate test cases from OSMO tester object + redirect output to a string to be returned
        String osmoResults = redirectOutputToString(tester);

        // calculate total time
        long endTime = System.nanoTime();
        long generationTime = TimeUnit.MILLISECONDS.convert(Duration.ofNanos(endTime - startTime));
        System.out.println("Generation time: " + generationTime);

        // extract total steps value from OSMO output string
        String testSteps = extractTestSteps(osmoResults);

        // add current id + total time + total steps to metrics csv file, separated by commas
        // TODO: execution time is incorrect after first iteration ? much shorter
        addValuesToCSVFile(folderPath, String.valueOf(nrOfTests), String.valueOf(generationTime), String.valueOf(testSteps));

        // save test steps in txt file in the new folder from the OSMO string results
        saveResultsToTxtFile(osmoResults, folderPath, "test" + nrOfTests);
    }

}
