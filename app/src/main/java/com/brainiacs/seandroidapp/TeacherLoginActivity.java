package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.brainiacs.seandroidapp.TeacherDashboard.TeacherDashboardActivity;
import java.util.HashMap;

import utils.DBTools;
import utils.HttpURLConnectionHandler;
import utils.LoginURLConnectionHandler;

/**
 * A login screen that offers login via username/password.
 */
public class TeacherLoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
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

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // If we have a token we don't need to login
        DBTools dbTools = new DBTools(this);
        // TODO: Remove once logout is implemented
        dbTools.deleteTokens();
        if (!dbTools.getToken().isEmpty()) {
            dbTools.close();
            return;
        }
        dbTools.close();

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean isValid = true;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            isValid = false;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (isValid) {
            // Setup our params for login
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.username), username);
            params.put(getString(R.string.password), password);
            Intent intent = new Intent(this, TeacherDashboardActivity.class);
            LoginURLConnectionHandler handler = new LoginURLConnectionHandler(
                    getString(R.string.login_url), getString(R.string.login_successful),
                    getString(R.string.failed_to_login), HttpURLConnectionHandler.Method.POST,
                    params, this, intent);
            handler.execute((Void) null);
        }
    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
