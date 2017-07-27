package com.example.shivanika.sih2017;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
        import android.app.Dialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class MainActivity extends Activity
{
    Button btnSignIn;
    Button btnSignUp;
//    Button next;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

// Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);
//        next = (Button)findViewById(R.id.buttonSignIn);

// Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// TODO Auto-generated method stub

/// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intentSignUP);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                signIn(v);
            }
        });
    }
    // Methods to handleClick Event of Sign In Button
    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");
// get the References of views
        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
        Button next=(Button)dialog.findViewById(R.id.buttonSignIn);
        System.out.println(btnSignIn);
// Set On ClickListener
        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
// get The User name and Password
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

// fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

// check if the Stored password matches with Password entered by user
                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
//                    System.out.println(next);
//                    next.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View view) {
                    //System.out.println("abc");
                    Intent myIntent1 = new Intent(v.getContext(), SecurityHomeActivity.class);
                    startActivityForResult(myIntent1, 0);

                    //Intent myIntent2 = new Intent(view.getContext(), ManagementActivity.class);
                    //startActivityForResult(myIntent2, 0);
                }
//                    });

                else {
                    Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter.close();
    }
}