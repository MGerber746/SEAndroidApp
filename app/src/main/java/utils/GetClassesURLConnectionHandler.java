package utils;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by Matthew on 3/9/17.
 */

public class GetClassesURLConnectionHandler extends HttpURLConnectionHandler {
    /**
     * Sets up the needed information to use the handler
     *
     * @param apiEndpoint represents the endpoint on the server this connection
     *                    needs to connect to
     * @param success
     * @param failure
     * @param method      represents whether this is a 'GET', 'POST', 'HEAD', 'OPTIONS',
     *                    'PUT', 'DELETE', or 'TRACE'
     * @param params      required parameters to be put in the url for the given method
     * @param context
     * @param intent
     */
    public GetClassesURLConnectionHandler(String apiEndpoint, String success, String failure, Method method,
                                          HashMap<String, String> params, Context context, Intent intent) {
        super(apiEndpoint, success, failure, method, params, context, intent);
    }
}
