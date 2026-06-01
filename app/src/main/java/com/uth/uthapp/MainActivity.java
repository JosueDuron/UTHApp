package com.uth.uthapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.uth.uthapp.adapters.ClienteAdapter;
import com.uth.uthapp.config.SQLiteConexion;
import com.uth.uthapp.config.Transacciones;
import com.uth.uthapp.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etApellido, etEdad, etCorreo;
    private Button btnAgregar;
    private RecyclerView rvClientes;
    private ClienteAdapter adapter;
    private List<Cliente> listaClientes;
    private SQLiteConexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializar conexión
        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

        // Configuración de insets para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEdad = findViewById(R.id.etEdad);
        etCorreo = findViewById(R.id.etCorreo);
        btnAgregar = findViewById(R.id.btnAgregar);
        rvClientes = findViewById(R.id.rvClientes);

        // Configurar RecyclerView
        rvClientes.setLayoutManager(new LinearLayoutManager(this));
        listaClientes = new ArrayList<>();
        adapter = new ClienteAdapter(listaClientes);
        rvClientes.setAdapter(adapter);

        // Configurar evento click
        btnAgregar.setOnClickListener(v -> {
            validarFormulario();
        });

        // Cargar lista inicial
        obtenerClientes();
    }

    private void validarFormulario() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese el nombre");
            etNombre.requestFocus();
        } else if (TextUtils.isEmpty(apellido)) {
            etApellido.setError("Ingrese el apellido");
            etApellido.requestFocus();
        } else if (TextUtils.isEmpty(edad)) {
            etEdad.setError("Ingrese la edad");
            etEdad.requestFocus();
        } else if (TextUtils.isEmpty(correo)) {
            etCorreo.setError("Ingrese el correo");
            etCorreo.requestFocus();
        } else {
            // Todos los campos tienen información, proceder a guardar
            guardarCliente();
        }
    }

    private void guardarCliente() {
        try {
            SQLiteDatabase db = conexion.getWritableDatabase();

            // Preparar los datos a insertar
            ContentValues valores = new ContentValues();
            valores.put(Transacciones.nombres, etNombre.getText().toString().trim());
            valores.put(Transacciones.apellidos, etApellido.getText().toString().trim());
            valores.put(Transacciones.edad, etEdad.getText().toString().trim());
            valores.put(Transacciones.correo, etCorreo.getText().toString().trim());

            // Insertar en la tabla
            Long resultado = db.insert(Transacciones.TablaClientes, Transacciones.id, valores);

            if (resultado > 0) {
                Toast.makeText(this, "Cliente Guardado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                obtenerClientes(); // Actualizar la lista automáticamente
            } else {
                Toast.makeText(this, "Error al guardar el cliente", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } catch (Exception ex) {
            Toast.makeText(this, "Error crítico: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void obtenerClientes() {
        try {
            SQLiteDatabase db = conexion.getReadableDatabase();
            listaClientes = new ArrayList<>();

            // Consultar todos los clientes
            Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.TablaClientes, null);

            while (cursor.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombres(cursor.getString(1));
                cliente.setApellidos(cursor.getString(2));
                cliente.setEdad(cursor.getInt(3));
                cliente.setCorreo(cursor.getString(4));

                listaClientes.add(cliente);
            }
            cursor.close();
            db.close();

            // Actualizar el adaptador con los nuevos datos
            adapter.setClientes(listaClientes);
        } catch (Exception ex) {
            Toast.makeText(this, "Error al obtener clientes: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etCorreo.setText("");
        etNombre.clearFocus();
    }
}