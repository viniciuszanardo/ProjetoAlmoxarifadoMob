package com.example.projetoalmoxarifadomob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AcitivityFuncionario extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    private ImageView imgFuncionario;
    private Button btnCadastro;
    private Button btnConsultar;
    private TextView txtFuncionario;
    private EditText etFuncionario;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NOME = "nomeFuncionario";
    private static final String KEY_FOTO = "fotoFuncionario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        imgFuncionario = findViewById(R.id.imgIconeUsuario);
        btnCadastro = findViewById(R.id.btnCadastro);
        txtFuncionario = findViewById(R.id.txtFuncionario);
        etFuncionario = findViewById(R.id.etFuncionario);
        btnConsultar = findViewById(R.id.btnConsultar);

        // Inicializa SharedPreferences
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Carrega nome salvo
        String nomeSalvo = prefs.getString(KEY_NOME, null);
        if (nomeSalvo != null) {
            txtFuncionario.setText(nomeSalvo);
        }

        // Carrega foto salva
        String fotoBase64 = prefs.getString(KEY_FOTO, null);
        if (fotoBase64 != null) {
            Bitmap bitmap = decodeBase64ToBitmap(fotoBase64);
            if (bitmap != null) {
                imgFuncionario.setImageBitmap(bitmap);
            }
        }

        // Ao clicar no nome, exibe EditText
        txtFuncionario.setOnClickListener(v -> {
            etFuncionario.setText(txtFuncionario.getText().toString());
            txtFuncionario.setVisibility(View.GONE);
            etFuncionario.setVisibility(View.VISIBLE);
            etFuncionario.requestFocus();
        });

        // Quando o usuário termina (Enter ou Done)
        etFuncionario.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String novoNome = etFuncionario.getText().toString().trim();
                if (!novoNome.isEmpty()) {
                    txtFuncionario.setText(novoNome);
                    prefs.edit().putString(KEY_NOME, novoNome).apply();
                }

                etFuncionario.setVisibility(View.GONE);
                txtFuncionario.setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        });

        // Abrir galeria
        imgFuncionario.setOnClickListener(v -> abrirGaleria());

        // Botão de Cadastro de Itens
        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(AcitivityFuncionario.this, ActivityCadastroItens.class);
            startActivity(intent);
        });

        // Botão de Consulta de Itens
        btnConsultar.setOnClickListener(v -> {
            Intent intent = new Intent(AcitivityFuncionario.this, ActivityConsulta.class);
            startActivity(intent);
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgFuncionario.setImageBitmap(bitmap);

                // Salvar foto no SharedPreferences
                String fotoBase64 = encodeBitmapToBase64(bitmap);
                prefs.edit().putString(KEY_FOTO, fotoBase64).apply();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Converte Bitmap para Base64
    private String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Converte Base64 para Bitmap
    private Bitmap decodeBase64ToBitmap(String base64) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
