package com.bilgeadam.mobilefoodapp.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.bilgeadam.mobilefoodapp.R;
import com.bilgeadam.mobilefoodapp.activity.MainActivity;


public class LoginSuccessDialog extends Dialog{
    private Activity c;

    public LoginSuccessDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_success_dialog);
        Button mainMenuButton = findViewById(R.id.mainMenuButton);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c.getApplicationContext(), MainActivity.class);
                c.startActivity(intent);
                c.finish();
                dismiss();

            }
        });
    }

    @Override
    protected void onStop() {
        dismiss();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(c.getApplicationContext(), MainActivity.class);
        c.startActivity(intent);
        dismiss();
        super.onBackPressed();
    }

}









