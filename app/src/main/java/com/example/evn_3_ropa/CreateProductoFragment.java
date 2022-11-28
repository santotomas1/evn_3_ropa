package com.example.evn_3_ropa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CreateProductoFragment extends DialogFragment {

    Button btn_agregar;
    EditText producto, precio,codigo;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_producto, container, false);
        mfirestore = FirebaseFirestore.getInstance();

        producto=v.findViewById(R.id.producto);
        precio=v.findViewById(R.id.precio);
        codigo=v.findViewById(R.id.codigo);
        btn_agregar=v.findViewById(R.id.btn_agregar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nproducto = producto.getText().toString().trim();
                String nprecio = precio.getText().toString().trim();
                String ncodigo = codigo.getText().toString().trim();

                if (nproducto.isEmpty()&&nprecio.isEmpty()&&ncodigo.isEmpty()){
                    Toast.makeText(getContext(),"ingresar datos",Toast.LENGTH_SHORT).show();

                }else{
                    postProducto(nproducto, nprecio,ncodigo);
                }
            }
        });

        return v;
    }

    private void postProducto(String nproducto, String nprecio, String ncodigo) {
        Map<String, Object> map = new HashMap<>();
        map.put("producto",nproducto);
        map.put("precio",nprecio);
        map.put("codigo",ncodigo);

        mfirestore.collection("producto").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Creado con exito",Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}