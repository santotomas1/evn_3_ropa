package com.example.evn_3_ropa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateProductoActivity extends AppCompatActivity {

    Button btn_agregar;
    EditText producto, precio,codigo;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_producto);

        this.setTitle("Crear producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_producto");
        mfirestore = FirebaseFirestore.getInstance();

        producto=findViewById(R.id.producto);
        precio=findViewById(R.id.precio);
        codigo=findViewById(R.id.codigo);
        btn_agregar=findViewById(R.id.btn_agregar);

        if(id == null || id == ""){
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nproducto = producto.getText().toString().trim();
                    String nprecio = precio.getText().toString().trim();
                    String ncodigo = codigo.getText().toString().trim();

                    if (nproducto.isEmpty()&&nprecio.isEmpty()&&ncodigo.isEmpty()){
                        Toast.makeText(getApplicationContext(),"ingresar datos",Toast.LENGTH_SHORT).show();

                    }else{
                        postProducto(nproducto, nprecio,ncodigo);
                    }
                }
            });

        }else{
            btn_agregar.setText("Actualizar");
            getProducto(id);
            btn_agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nproducto = producto.getText().toString().trim();
                    String nprecio = precio.getText().toString().trim();
                    String ncodigo = codigo.getText().toString().trim();

                    if (nproducto.isEmpty()&&nprecio.isEmpty()&&ncodigo.isEmpty()){
                        Toast.makeText(getApplicationContext(),"ingresar datos",Toast.LENGTH_SHORT).show();

                    }else{
                        updateProducto(nproducto, nprecio,ncodigo, id);
                    }

                }
            });

        }


    }

    private void updateProducto(String nproducto, String nprecio, String ncodigo, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("producto",nproducto);
        map.put("precio",nprecio);
        map.put("codigo",ncodigo);

        mfirestore.collection("producto").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado con exito",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postProducto(String nproducto, String nprecio, String ncodigo) {
        Map<String, Object> map = new HashMap<>();
        map.put("producto",nproducto);
        map.put("precio",nprecio);
        map.put("codigo",ncodigo);

        mfirestore.collection("producto").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creado con exito",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProducto(String id){
        mfirestore.collection("producto").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nproducto = documentSnapshot.getString("producto");
                String nprecio = documentSnapshot.getString("precio");
                String ncodigo = documentSnapshot.getString("codigo");

                producto.setText(nproducto);
                precio.setText(nprecio);
                codigo.setText(ncodigo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al ingresar los datos",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}