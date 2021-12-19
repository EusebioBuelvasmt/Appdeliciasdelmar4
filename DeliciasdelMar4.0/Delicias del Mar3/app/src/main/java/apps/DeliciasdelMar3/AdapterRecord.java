package apps.DeliciasdelMar3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import apps.DeliciasdelMar3.proyectsqlite.R;


public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord>{

    //Variables
    private Context context;
    private ArrayList<ModelRecord> recordsList;
    private DbHelper dbHelper;
    //Constructor
    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList){
        this.context = context;
        this.recordsList = recordsList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_record, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder,final int position) {

        // obtener datos, establecer datos, ver clics en el método

        //Obtener datos
        ModelRecord model = recordsList.get(position);
        final String id = model.getId();
        final String name = model.getName();
        final String image = model.getImage();
        final String bio = model.getBio();
        final String price = model.getPrice();

        //Establecer Datos
        holder.nameTv.setText(name);
        holder.priceTv.setText(price);
        holder.bioTv.setText(bio);




        // si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
        // configure una imagen predeterminada en ese caso
        if (image.equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.profileIv.setImageResource(R.drawable.logo_delicias_del_mar);
        }
        else if (image.equals("arroz")){
            holder.profileIv.setImageResource(R.drawable.arroz_marisco);
        }
        else if (image.equals("mojarra")){
            holder.profileIv.setImageResource(R.drawable.pescado_frito);
        }
        else if (image.equals("cazuela")){
            holder.profileIv.setImageResource(R.drawable.cazuela);
        }
        else if (image.equals("pulpo")){
            holder.profileIv.setImageResource(R.drawable.pulpo);
        }
        else {
            // tener imagen en el registro
            holder.profileIv.setImageURI(Uri.parse(image));
        }


        // manejar clicks de elementos (ir a la actividad de registro de detalles)

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass record id to next activity to show details of thet record
                Intent intent = new Intent(context, DetalleRegistroActivity.class);
                intent.putExtra("RECORD_ID", id);
                context.startActivity(intent);
            }
        });

        //manejar clicks de botones (mostrar opciones como editar, eliminar)

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar el menu de opciones
                showMoreDialog(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+price,
                        ""+bio,
                        ""+image);
            }
        });
    }


    public void showMoreDialog( String position, final String id, final String name, final String price, final String bio, final String image){
        //opciones para mostrar en el dialogo
        String [] options = {"Editar", "Eliminar", "Sucursales"};
        //Dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //Agregar elementos al dialogo
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //se hace click en editar
                    //inicie la actividad para actualizar los registros existentes
                    Intent intent = new Intent(context, AgregarRegistroActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("NAME", name);
                    intent.putExtra("PRICE", price);
                    intent.putExtra("BIO", bio);
                    intent.putExtra("IMAGE", image);
                    intent.putExtra("isEditMode", true);//nesecita para establecer datos existente
                    context.startActivity(intent);
                }
                else if (which == 1){

                    //Eliminar Pendiente NO FUNCIONA
                    Toast.makeText(context.getApplicationContext(), "Eliminando", Toast.LENGTH_SHORT).show();

                }

                else if (which == 2){

                    Intent intent = new Intent(context, Sucursales.class);

                    context.startActivity(intent);
                }

            }

        });
        //Mostrar el dialogo
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return recordsList.size();// devuelve el tamaño de la lista / número o registros
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        //vistas
        ImageView profileIv;
        TextView nameTv, priceTv, bioTv;
        ImageButton moreBtn;
        public HolderRecord(@NonNull View itemView){
            super(itemView);

            //Inicializamos la vistas
            profileIv = itemView.findViewById(R.id.profileIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            priceTv = itemView.findViewById(R.id.priceTv);
            bioTv = itemView.findViewById(R.id.bioTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);

        }
    }
}
