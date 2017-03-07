package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import utils.HttpURLConnectionHandler;
import utils.LoginURLConnectionHandler;

/**
 * A login screen that offers login via Username/password.
 */
public class StudentLoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {// Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean isValid = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            isValid = false;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        //Check for a password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (isValid) {
            // Setup our params for login
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.username), username);
            params.put(getString(R.string.password), password);
            Intent intent = new Intent(this, this.getClass());
            LoginURLConnectionHandler handler = new LoginURLConnectionHandler(
                    getString(R.string.login_url), getString(R.string.login_successful),
                    getString(R.string.failed_to_login), HttpURLConnectionHandler.Method.POST,
                    params, this, intent);
            handler.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
