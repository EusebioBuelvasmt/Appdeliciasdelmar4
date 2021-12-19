package apps.DeliciasdelMar3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import apps.DeliciasdelMar3.proyectsqlite.R;


public class splash extends AppCompatActivity {
    private final int Time = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
            }
        },Time);
    }
}