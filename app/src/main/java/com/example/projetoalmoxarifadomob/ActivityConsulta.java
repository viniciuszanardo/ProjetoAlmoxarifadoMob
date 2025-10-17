package com.example.projetoalmoxarifadomob;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityConsulta extends AppCompatActivity {

    private Button buttonConsultaQr, buttonConsultaId;
    private EditText editTextIdItem;
    private TextView textResultado;
    private static final int SCAN_QR_REQUEST = 101; // código para o scanner

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        buttonConsultaQr = findViewById(R.id.buttonConsultaQr);
        buttonConsultaId = findViewById(R.id.buttonConsultaId);
        editTextIdItem = findViewById(R.id.editTextIdItem);
        textResultado = findViewById(R.id.textResultado);

        // Consulta via QR Code
        buttonConsultaQr.setOnClickListener(v -> abrirLeitorQr());

        // Consulta via ID
        buttonConsultaId.setOnClickListener(v -> {
            String id = editTextIdItem.getText().toString().trim();
            if (id.isEmpty()) {
                Toast.makeText(this, "Digite um ID válido", Toast.LENGTH_SHORT).show();
            } else {
                consultarPorId(id);
            }
        });
    }

    private void abrirLeitorQr() {
        // Aqui você vai abrir a activity do leitor QR
        Intent intent = new Intent(this, ActivityLeitorQr.class);
        startActivityForResult(intent, SCAN_QR_REQUEST);
    }

    private void consultarPorId(String id) {
        // Aqui você pode conectar ao banco ou API
        // Exemplo simples:
        textResultado.setText("Item encontrado!\nID: " + id + "\nNome: Martelo\nEstoque: 15 unidades");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_QR_REQUEST && resultCode == RESULT_OK && data != null) {
            String codigoQR = data.getStringExtra("codigoQR");
            consultarPorId(codigoQR);
        }
    }
}
