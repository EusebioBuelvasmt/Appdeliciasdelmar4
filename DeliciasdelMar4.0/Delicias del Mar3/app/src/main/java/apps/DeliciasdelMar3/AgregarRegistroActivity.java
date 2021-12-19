package apps.DeliciasdelMar3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import apps.DeliciasdelMar3.proyectsqlite.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class AgregarRegistroActivity extends AppCompatActivity {

    //View
    private CircleImageView profileIv;
    private EditText nameEt, priceEt,bioEt;
    private FloatingActionButton saveBtn;

    //Actionbar
    private ActionBar actionBar;
    //Permiso de la clase Constants
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento
    // variables (constain datos para guardar)
    private Uri imageUri;
    private String id, name, price,  bio;

    private boolean isEditMode = false;

    private RelativeLayout RelativeLayout;


    //db helper
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_registro);

        //Inicializacion
        actionBar = getSupportActionBar();
        //Titulo
        actionBar.setTitle("Agregar Producto");
        //Boton Negro
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        profileIv = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        priceEt = findViewById(R.id.priceEt);
        bioEt = findViewById(R.id.bioEt);
        saveBtn = findViewById(R.id.saveBtn);

        RelativeLayout = (RelativeLayout) findViewById(R.id.AgregarLayout);

        //obtener los datos de la intencion
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        //establer la vista de los datos
        if (isEditMode) {

            //Actualizar datos
            actionBar.setTitle("Actualizar Producto");

            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            price = intent.getStringExtra("PRICE");
            bio = intent.getStringExtra("BIO");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));


            //set View data
            nameEt.setText(name);
            priceEt.setText(price);
            bioEt.setText(bio);

            //sino se selecciona una imagen al agregr datos; el valor de la imagen ser "NULL"
            // no hay imagen en el registro, establecer predeterminado
            if (imageUri.toString().equals("null")) {
                profileIv.setImageResource(R.drawable.logo_delicias_del_mar);
            }

            if (imageUri.toString().equals("arroz")) {

                profileIv.setImageResource(R.drawable.arroz_marisco);
            }
            else if (imageUri.toString().equals("mojarra")) {

                profileIv.setImageResource(R.drawable.pescado_frito);
            }
            else if (imageUri.toString().equals("cazuela")) {

                profileIv.setImageResource(R.drawable.cazuela);
            }

            else if (imageUri.toString().equals("pulpo")) {

                profileIv.setImageResource(R.drawable.pulpo);
            } else {

                profileIv.setImageURI(imageUri);
            }
        } else {
            //agregar datos
            actionBar.setTitle("Agregar Nuevo Producto");
        }

        Snackbar snackbar = Snackbar.make(RelativeLayout, "tap en la Imagen para cambiarla", Snackbar.LENGTH_SHORT);
        snackbar.show();


        //Inicializar BD Helper
        dbHelper = new DbHelper(this);

        //Inicializamos Permisos arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }


    private void inputData(){

        //get data
        name = ""+nameEt.getText().toString().trim();
        price = ""+ priceEt.getText().toString().trim();
        bio = ""+bioEt.getText().toString().trim();

        if (isEditMode){
            //actualizar datos

            String timestamp = ""+System.currentTimeMillis();
            dbHelper.updateRecord(
                    ""+id,
                    ""+name,
                    ""+imageUri,
                    ""+bio,
                    ""+ price
                               );

            Toast.makeText(this, "Registro Actualizado. ", Toast.LENGTH_SHORT).show();

        }
        else{
            //new datos
            //guarda en la base de datos
            String timestamp = ""+System.currentTimeMillis();
            long id = dbHelper.insertRecord(
                    ""+name,
                    ""+imageUri,
                    ""+bio,
                    ""+ price);

            Toast.makeText(this, "Registro agregado contra ID: "+id, Toast.LENGTH_SHORT).show();
        }
    }

    private void imagePickDialog(){
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    //click en camara
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }

    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private  void requestStoragePermission(){
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //regrese haciendo clic en el botón de barra de acción
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        // ambos permisos permitidos
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){

                    // si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        // permiso de almacenamiento permitido
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //Picked from camera
                //crop Image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    //set Image
                    profileIv.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
