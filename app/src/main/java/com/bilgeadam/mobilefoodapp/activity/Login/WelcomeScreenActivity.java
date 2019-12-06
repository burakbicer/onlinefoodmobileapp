package com.bilgeadam.mobilefoodapp.activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bilgeadam.mobilefoodapp.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "Welcome Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button sign_up_button = findViewById(R.id.btn_sign_up);
        Button sign_in_button = findViewById(R.id.btn_sign_in);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up_intent = new Intent(getApplicationContext(), LoginActivity.class);
                sign_up_intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                sign_up_intent.putExtra("mode", "sign_up");
                startActivity(sign_up_intent);
                finish();
            }
        });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_in_intent = new Intent(getApplicationContext(), LoginActivity.class);
                sign_in_intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                sign_in_intent.putExtra("mode", "log_in");
                startActivity(sign_in_intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }
}