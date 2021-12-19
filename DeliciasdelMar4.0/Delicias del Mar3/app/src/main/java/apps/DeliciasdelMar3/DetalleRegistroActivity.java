package apps.DeliciasdelMar3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import apps.DeliciasdelMar3.proyectsqlite.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleRegistroActivity extends AppCompatActivity {

    //vistas
    private CircleImageView profileIv;
    private TextView bioTv, nameTv, priceTv;

    //ActionBar
    private ActionBar actionBar;

    //
    private String recordID;

    //BDHelper
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_registro);

        //setting up actionbar with title and back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Detalle del Producto");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //obtener la identificación de registro del adaptador mediante la intención
        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        //Inicializacion BD Helper Clase
        dbHelper = new DbHelper(this);

        //Inicializamos las vistas
        profileIv = findViewById(R.id.profileIv);
        bioTv = findViewById(R.id.bioTv);
        nameTv = findViewById(R.id.nameTv);
        priceTv = findViewById(R.id.priceTv);

        showRecordDetails();
    }

    private void showRecordDetails() {
        //obtener detalles de registro
        //consulta para seleccionar el registro basado en la identificación del registro
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID +" =\""+ recordID+"\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // mantener comprobando toda la base de datos para ese registro
        if (cursor.moveToFirst()){
            do {

                //Obtenner datos
                String id = ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String name = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_NAME));
                String image = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));
                String bio = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_BIO));
                String price = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_PRICE));


                //Establecer datos
                nameTv.setText(name);
                bioTv.setText(bio);
                priceTv.setText(price);




                // si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
                // configure una imagen predeterminada en ese caso
                if (image.equals("null")){
                    // no hay imagen en el registro, establecer predeterminado
                    profileIv.setImageResource(R.drawable.logo_delicias_del_mar);
                }

                else if(image.equals("arroz")) {
                    // no hay imagen en el registro, establecer predeterminado
                    profileIv.setImageResource(R.drawable.arroz_marisco);

                }else if(image.equals("mojarra")) {
                        // no hay imagen en el registro, establecer predeterminado
                        profileIv.setImageResource(R.drawable.pescado_frito);

                }

                else if(image.equals("cazuela")) {
                    // no hay imagen en el registro, establecer predeterminado
                    profileIv.setImageResource(R.drawable.cazuela);

                }
                else if(image.equals("pulpo")) {
                    // no hay imagen en el registro, establecer predeterminado
                    profileIv.setImageResource(R.drawable.pulpo);

                } else {
                    // tener imagen en el registro
                    profileIv.setImageURI(Uri.parse(image));
                }


            }while(cursor.moveToNext());
        }
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();//ir a la actividad anterior
        return super.onSupportNavigateUp();
    }
}
