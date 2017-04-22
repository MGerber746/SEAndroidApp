package utils.handlers;

import android.content.Context;
import android.content.Intent;

import com.brainiacs.seandroidapp.ActivityCreationActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentHandler extends HttpHandler {
    public AssignmentHandler(String apiEndpoint, String success, String failure,
                           Method method, HashMap<String, String> params,
                           Context context, Intent intent) {
        super(apiEndpoint, success, failure, method, params, context, intent);
    }

    protected String doInBackround(Void... params) {
        ActivityCreationActivity activity = ((ActivityCreationActivity) context);
        while (activity.getQuestionIDs().size() != activity.getQuestions().size()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        this.params.put("questions", generateQuestionIdsString(activity.getQuestionIDs()));
        return super.doInBackground(params);
    }

    private String generateQuestionIdsString(ArrayList<Integer> questionIDs) {
        String questionIdsString = "[";
        for (int i = 0; i < questionIDs.size(); ++i) {
            questionIdsString += questionIDs.get(i).toString();
            if (i != questionIDs.size() - 1) {
                questionIdsString += ",";
            }
        }
        questionIdsString += "]";
        return questionIdsString;
    }
}
