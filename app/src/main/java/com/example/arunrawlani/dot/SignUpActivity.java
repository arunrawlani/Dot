package com.example.arunrawlani.dot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends Activity {


    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    protected EditText mChildname;
    protected EditText mChildage;
    protected RadioButton mChildGender;
    protected RadioButton mChildGender2;
    protected RadioButton mChildGender3;
    protected EditText mChilddiagnosis;
    protected EditText mChildmedication;
    protected Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = (EditText)findViewById(R.id.usernameField);
        mPassword= (EditText)findViewById(R.id.passwordField);
        mEmail= (EditText)findViewById(R.id.emailField);
        mChildname= (EditText)findViewById(R.id.child_name);
        mChildage= (EditText)findViewById(R.id.child_age);

        mChildGender = (RadioButton)findViewById(R.id.gender_male);
        mChildGender2 = (RadioButton)findViewById(R.id.gender_female);
        mChildGender3 = (RadioButton) findViewById(R.id.gender_other);
        mChilddiagnosis= (EditText)findViewById(R.id.child_diagnosis);
        mChildmedication= (EditText)findViewById(R.id.child_medication);
        mSignUpButton= (Button)findViewById(R.id.signupButton);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username= mUsername.getText().toString();
                String password= mPassword.getText().toString();
                String email= mEmail.getText().toString();
                String childname= mChildname.getText().toString();
                String childage= mChildage.getText().toString();
                String childdiagnosis= mChilddiagnosis.getText().toString();
                String childmedication= mChildmedication.getText().toString();
                String childGender;
                if(mChildGender.isChecked())
                    childGender = "male";
                else if(mChildGender2.isChecked())
                    childGender = "female";
                else
                    childGender = "other";


                username= username.trim();
                password = password.trim();
                email= email.trim();
                childname= childname.trim();
                childage= childage.trim();
                childdiagnosis=childdiagnosis.trim();

                if(username.isEmpty() || password.isEmpty() || email.isEmpty() ||
                        childname.isEmpty() || childage.isEmpty() || childage.isEmpty() ||
                        childdiagnosis.isEmpty() || childmedication.isEmpty()){
                    AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message1)
                        .setTitle(R.string.signup_error_title)
                        .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog= builder.create();
                    dialog.show();
                }
                else{
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);

                    //other additional fields for child's information
                    newUser.put("ChildName",childname);
                    newUser.put("ChildAge",childage);
                    //insert one for gender
                    newUser.put("DiagnosisDate", childdiagnosis);
                    newUser.put("Medication",childmedication);
                    newUser.put("Gender",childGender);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                           if (e == null){
                               Log.v("signup", "signup success");
                               Intent intent = new Intent(SignUpActivity.this, MainActivity.class); //ask! why not login?
                               //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                               startActivity(intent);
                               return;
                           }
                            else{
                               Log.v("signup","signup failure");
                               AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
                               builder.setMessage(e.getMessage())
                                       .setTitle(R.string.signup_error_title)
                                       .setPositiveButton(android.R.string.ok, null);

                               AlertDialog dialog= builder.create();
                               dialog.show();
                           }
                        }
                    });

                }

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){

            case R.id.action_settings:
            return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
