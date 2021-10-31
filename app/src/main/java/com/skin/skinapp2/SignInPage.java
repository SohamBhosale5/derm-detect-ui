package com.skin.skinapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skin.skinapp2.models.SignIn;
import com.skin.skinapp2.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPage extends AppCompatActivity {
    Window window;
    Button btn_login;
    EditText et_email;
    EditText et_password;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        et_email = findViewById(R.id.edit_text_email);
        et_password = findViewById(R.id.edit_text_password);
        if(Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_color));
        }
        btn_login = (Button)findViewById(R.id.ButtonLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                if(email.isEmpty() || email.equals("")){
                    Toast.makeText(SignInPage.this, "Email required", Toast.LENGTH_LONG).show();
                } else if(password.isEmpty() || password.equals("")) {
                    Toast.makeText(SignInPage.this, "Password required", Toast.LENGTH_LONG).show();
                } else {
                    SignIn signIn = new SignIn(email, password);
                    //System.out.println("User: "  + user.getFirstName());
                    MainApplication.apiManager.signInUser(signIn, new Callback<SignIn>() {
                        @Override
                        public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                            System.out.println("On Response");
                            SignIn responseUser = response.body();
//                            sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
//                            editor = sharedPreferences.edit();
                            System.out.println("Response body" + response.body());
                            if(response.isSuccessful() && responseUser != null){
                                Toast.makeText(SignInPage.this,"Sign In Successful!", Toast.LENGTH_LONG).show();
                                //editor.putBoolean("IsSignInSuccessful", true);
                                //editor.commit();
                                //Intent intent = new Intent(sign_up_page.this, SignInPage.class);
                                //startActivity(intent);
                                Intent intent = new Intent(SignInPage.this, navigation_drawer.class);
                                startActivity(intent);

                            } else {
                                System.out.println("Response  = " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<SignIn> call, Throwable t) {
                            System.out.println("On Failure " + t);

                        }
                    });
                }
            }
        });
    }
}