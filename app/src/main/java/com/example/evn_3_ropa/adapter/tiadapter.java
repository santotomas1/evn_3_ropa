package com.example.evn_3_ropa.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evn_3_ropa.CreateProductoActivity;
import com.example.evn_3_ropa.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.example.evn_3_ropa.modelo.productoT;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tiadapter extends FirestoreRecyclerAdapter<productoT, tiadapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public tiadapter(@NonNull FirestoreRecyclerOptions<productoT> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull productoT model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.producto.setText(model.getProducto());
        viewHolder.precio.setText(model.getPrecio());
        viewHolder.codigo.setText(model.getCodigo());

        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, CreateProductoActivity.class);
                i.putExtra("id_producto",id);
                activity.startActivity(i);

            }
        });

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto(id);

            }
        });

    }

    private void eliminarProducto(String id) {
        mFirestore.collection("producto").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity,"Eliminado Correctamente", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"Error al Elminar", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_producto_singler,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView producto, precio, codigo;
        ImageView btn_delete,btn_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.productoM);
            precio = itemView.findViewById(R.id.precioM);
            codigo = itemView.findViewById(R.id.codigoM);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_edit = itemView.findViewById(R.id.btn_editar);
        }
    }
}
