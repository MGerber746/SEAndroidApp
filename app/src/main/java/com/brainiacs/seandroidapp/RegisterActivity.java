package com.brainiacs.seandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import utils.HttpURLConnectionHandler;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /** Called when user clicks submit button **/
    public void register(View view) {
        Intent intent = new Intent(this, TeacherLoginActivity.class);
        // Get all of the views of our fields
        EditText mFirstNameView = (EditText) findViewById(R.id.first_name);
        EditText mLastNameView = (EditText) findViewById(R.id.last_name);
        EditText mEmailView = (EditText) findViewById(R.id.email);
        EditText mUsernameView = (EditText) findViewById(R.id.username);
        EditText mPasswordView = (EditText) findViewById(R.id.password);
        EditText mReenterPasswordView = (EditText) findViewById(R.id.reenter_password);
        // Get the String values to create the user
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String reenterPassword = mReenterPasswordView.getText().toString();
        // Check to make sure everything is valid
        boolean cancel = false;

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameView.setError(getString(R.string.empty_first_name));
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.empty_last_name));
            cancel = true;
        }
        if (!this.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_email));
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.empty_username));
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.empty_password));
            cancel = true;
        }
        if (TextUtils.isEmpty(reenterPassword)) {
            mReenterPasswordView.setError(getString(R.string.empty_reenter_password));
            cancel = true;
        }
        if(!password.equals(reenterPassword)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            mReenterPasswordView.setError(getString(R.string.invalid_password));
            cancel = true;
        }

        if(!cancel) {
            // Set up our handler
            HashMap<String, String> params = new HashMap<>();
            params.put("first_name", firstName);
            params.put("last_name", lastName);
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
            params.put("confirm_password", reenterPassword);
            HttpURLConnectionHandler handler = new HttpURLConnectionHandler(
                    "register/", HttpURLConnectionHandler.Method.POST, params, this);
            // Execute the task
            handler.execute((Void) null);
            // Pass user on to the next activity
            startActivity(intent);
        }
    }

    public void returnToLogin(){
        Intent intent = new Intent(this, TeacherLoginActivity.class);
        startActivity(intent);

    }
    private boolean isEmailValid(String email) {
        if (email.contains("@") && email.contains("."))
            return true;
        else
            return false;
    }
}