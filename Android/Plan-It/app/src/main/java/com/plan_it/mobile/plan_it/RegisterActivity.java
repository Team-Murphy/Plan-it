package com.plan_it.mobile.plan_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;

import cz.msebera.android.httpclient.Header;


public class RegisterActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText pwd;
    EditText pwdConfirm;
    EditText fname;
    EditText lname;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname = (EditText) findViewById(R.id.editFName);
        // Find Email Edit View control by ID
        lname = (EditText) findViewById(R.id.editLName);
        name = (EditText) findViewById(R.id.editTextUser);
        // Find Email Edit View control by ID
        email = (EditText) findViewById(R.id.editEmailCreate);
        // Find Password Edit View control by ID
        pwd = (EditText) findViewById(R.id.editPass);
        // Find Confirm Password Edit View control by ID
        pwdConfirm=(EditText)findViewById(R.id.editConfirmPass);


    }

    public void register(View v) {
        String fNameReg=fname.getText().toString();
        String lNameReg=lname.getText().toString();
        String nameReg = name.getText().toString();
        String emailReg = email.getText().toString();
        String passwordReg = pwd.getText().toString();
        String confPass=pwdConfirm.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams rdate = new RequestParams();

        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (RegValidation.isNotNull(nameReg) && RegValidation.isNotNull(emailReg) && RegValidation.isNotNull(passwordReg)) {
            // When Email entered is Valid
            if (RegValidation.validate(emailReg)) {
                    if (passwordReg.equals(confPass)) {
                    rdate.put("firstName", fNameReg);
                    rdate.put("lastName", lNameReg);
                    rdate.put("email", emailReg);
                    rdate.put("hashPassword", passwordReg);
                    rdate.put("friendlyName", nameReg);
                        try {
                            invokeWS(rdate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    //When passwords do not match
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
            // When Email is invalid
            else {
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }

    public void setDefaultValues(){
        name.setText("");
        email.setText("");
        pwd.setText("");
    }

    public void invokeWS(RequestParams params)throws JSONException{

            RestClient.post("user",params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(getApplicationContext(),responseBody.toString(),Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(RegisterActivity.this,EventsListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }

        });
    }
}
