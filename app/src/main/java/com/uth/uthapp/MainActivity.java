package com.uth.uthapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etApellido, etEdad, etCorreo;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
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

        // Configurar evento click
        btnAgregar.setOnClickListener(v -> {
            validarFormulario();
        });
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
            // Todos los campos tienen información
            Toast.makeText(this, "Cliente Guardado", Toast.LENGTH_LONG).show();
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etCorreo.setText("");
        etNombre.requestFocus();
    }
}