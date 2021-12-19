package apps.DeliciasdelMar3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import apps.DeliciasdelMar3.proyectsqlite.R;


//Action Bar


public class Sucursales extends AppCompatActivity{


    //Action Bar
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Sucursales");

    }
}