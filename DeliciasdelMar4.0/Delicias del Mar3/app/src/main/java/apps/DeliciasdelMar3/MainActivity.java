package apps.DeliciasdelMar3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import apps.DeliciasdelMar3.proyectsqlite.R;


public class MainActivity extends AppCompatActivity {

    //Views
    private FloatingActionButton addRecordBtn;

    //RecyclerView
    private RecyclerView recordsRv;

    //DB Helper
    private DbHelper dbHelper;

    //Action Bar
    ActionBar actionBar;

    private RelativeLayout RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar Vista
        addRecordBtn = findViewById(R.id.addRecordBtn);
        recordsRv = findViewById(R.id.recordsRv);
        //Inicializamos db helper Clase
        dbHelper = new DbHelper(this);

        //Inicializacion ActionBar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Menú");

        RelativeLayout = (RelativeLayout) findViewById(R.id.MainLayout);

        // Cargando Registros
        loadRecords();


        // Click para Iniciar a añadir y grabar en la activity
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iniciar la Activity
                Intent intent = new Intent(MainActivity.this, AgregarRegistroActivity.class);
                intent.putExtra("isEditMode", false);//desea establecer nuevos datos, set false.
                startActivity(intent);
            }
        });
    }

    private void loadRecords(){
        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this,
                dbHelper.getAllRecords());

        recordsRv.setAdapter(adapterRecord);

        //Establecer el numero de Registros
        actionBar.setSubtitle("Total: "+dbHelper.getRecordsCount());
    }

    private void searchRecords(String query){
        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this,
                dbHelper.searchRecords(query));

        recordsRv.setAdapter(adapterRecord);

    }

    @Override
    protected void onResume(){
        super.onResume();
        loadRecords();// Refresca o actualiza la lista de registros
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //searchView
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // buscar cuando se hace clic en el botón de búsqueda en el teclado
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // busca mientras escribes
                searchRecords(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();

        if (id== R.id.action_mapa){
            Intent intent = new Intent(MainActivity.this, Mapa.class);
            startActivity(intent);

            return true;
        }
        //maneja elementos del menu
        return super.onOptionsItemSelected(item);
    }
}
