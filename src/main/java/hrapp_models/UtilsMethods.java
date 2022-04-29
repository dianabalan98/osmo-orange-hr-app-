package hrapp_models;

import java.util.ArrayList;

public class UtilsMethods {

    public void checkList(ArrayList<String> testStepsList, String testStep) {
        if (!testStepsList.contains(testStep)) {
            testStepsList.add(testStep);
        }
    }

}
