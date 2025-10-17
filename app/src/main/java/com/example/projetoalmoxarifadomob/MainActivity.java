package com.example.projetoalmoxarifadomob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonEntrar;
    private EditText editTextLogin, editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Seu layout de login

        // Vinculando os campos
        buttonEntrar = findViewById(R.id.buttonEntrar);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextSenha = findViewById(R.id.editTextSenha);

        // Clique do botão
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString().trim();
                String senha = editTextSenha.getText().toString().trim();

                // Login fixo para teste
                if (login.equals("func") && senha.equals("func123")) {
                    Intent intent = new Intent(MainActivity.this, AcitivityFuncionario.class);
                    startActivity(intent);
                    finish(); // Fecha a tela de login
                } else {
                    Toast.makeText(MainActivity.this, "Login ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
