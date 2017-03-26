package utils;

import org.json.JSONObject;

/**
 * Created by Matthew on 3/25/17.
 */

public class JSONTools {
    private JSONObject json;

    public JSONTools(){
        this.json = new JSONObject();
    }

    public void setJSON(JSONObject json){
        this.json = json;
    }

    public JSONObject getJSON(){
        return json;
    }

}
