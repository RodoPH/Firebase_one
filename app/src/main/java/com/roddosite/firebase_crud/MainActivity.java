package com.roddosite.firebase_crud;

import android.app.Person;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roddosite.firebase_crud.modelo.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText nombre, telefono, mail, dir;
    Persona persona = new Persona();
    ListView listView;
    List<Persona> lista = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapter;
    Persona selected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.main_nombre);
        telefono = findViewById(R.id.main_telefono);
        mail = findViewById(R.id.maint_mail);
        dir = findViewById(R.id.main_dirección);
        listView = findViewById(R.id.lista_personas);
        limpiarCampos();
        fireBase();
        mostrarDatos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (Persona) parent.getItemAtPosition(position);
                nombre.setText(selected.getNombre());
                telefono.setText(selected.getTelefono());
                dir.setText(selected.getDirección());
                mail.setText(selected.getMail());

            }
        });
    }

    private void mostrarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Persona p = objSnaptshot.getValue(Persona.class);
                    lista.add(p);
                    arrayAdapter = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1,lista);
                    listView.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void fireBase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eliminar:{
                try{
                    Persona p = new Persona();
                    p.setId(selected.getId());
                    databaseReference.child("Persona").child(p.getId()).removeValue();
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, "Seleccione un contacto", Toast.LENGTH_SHORT).show();
                }

            }break;
            case R.id.actualizar: {
                try {
                    Persona p = new Persona();
                    p.setId(selected.getId());
                    p.setNombre(nombre.getText().toString().trim());
                    p.setMail(mail.getText().toString().trim());
                    p.setTelefono(telefono.getText().toString().trim());
                    p.setDirección(dir.getText().toString().trim());
                    databaseReference.child("Persona").child(p.getId()).setValue(p);
                    Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Seleccione un contacto", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.agregar: {
                try {
                    Boolean v = validar(nombre.getText().toString(), telefono.getText().toString(), mail.getText().toString(), dir.getText().toString());
                    if (v) {
                        persona.setId(UUID.randomUUID().toString());
                        persona.setNombre(nombre.getText().toString());
                        persona.setDirección(dir.getText().toString());
                        persona.setMail(mail.getText().toString());
                        persona.setTelefono(telefono.getText().toString());
                        databaseReference.child("Persona").child(persona.getId()).setValue(persona);
                        Toast.makeText(this, "Agregado con éxito", Toast.LENGTH_LONG).show();
                        limpiarCampos();
                        System.out.println("Verdadero de try");

                    } else {
                        valorsRequired();

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    valorsRequired();
                }
            }
            break;
            case R.id.mostrar: {
                mostrarDatos();
            }break;
            default:
                break;
        }
        return true;
    }

    private void valorsRequired() {
        nombre.setError("Requerido");
        telefono.setError("Requerido");
        mail.setError("Requerido");
        dir.setError("Requerido");
        limpiarCampos();
    }

    private void limpiarCampos() {
        nombre.setText("");
        telefono.setText("");
        mail.setText("");
        dir.setText("");

    }

    private boolean validar(String nombre, String telefono, String mail, String direccion) {
        boolean resultado;

        if (!nombre.equals("") || !telefono.equals("") || !mail.equals("") || !direccion.equals("")) {
            resultado = true;
            System.out.println("falso ");

        } else {
            resultado = false;
        }
        return resultado;
    }
}


