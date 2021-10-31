package com.skin.skinapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skin.skinapp2.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sign_up_page extends AppCompatActivity {
    Window window;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText et_firstName, et_lastName, et_email, et_password;
    String firstName, lastName, email, password;
    Button btn_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        if(Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_color));
        }
        et_firstName = findViewById(R.id.edit_text_first_name);
        et_lastName = findViewById(R.id.edit_text_last_name);
        et_email = findViewById(R.id.edit_text_email_sign_up);
        et_password = findViewById(R.id.edit_text_password_sign_up);
        btn_create = findViewById(R.id.ButtonCreateAccount);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = et_firstName.getText().toString();
                lastName = et_lastName.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                if(firstName.isEmpty() || firstName.equals("")){
                    Toast.makeText(sign_up_page.this, "First Name required", Toast.LENGTH_LONG).show();
                } else if(lastName.isEmpty() || lastName.equals("")) {
                    Toast.makeText(sign_up_page.this, "Last Name required", Toast.LENGTH_LONG).show();
                } else if(email.isEmpty() || email.equals("")) {
                    Toast.makeText(sign_up_page.this, "Email required", Toast.LENGTH_LONG).show();
                } else if(password.isEmpty() || password.equals("")) {
                    Toast.makeText(sign_up_page.this, "Password required", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User(firstName, lastName, email, password);
                    System.out.println("User: "  + user.getFirstName());
                    MainApplication.apiManager.createUser(user, new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            System.out.println("On Response");
                            User responseUser = response.body();
                            sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            System.out.println("Response body" + response.body());
                            if(response.isSuccessful() && responseUser != null){
                                Toast.makeText(sign_up_page.this,"Sign Up Successful!", Toast.LENGTH_LONG).show();
                                editor.putBoolean("IsSignInSuccessful", true);
                                editor.commit();
                                Intent intent = new Intent(sign_up_page.this, SignInPage.class);
                                startActivity(intent);

                            } else {
                                System.out.println("Response  = " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            System.out.println("On Failure " + t);

                        }
                    });
                }
            }
        });
    }
}