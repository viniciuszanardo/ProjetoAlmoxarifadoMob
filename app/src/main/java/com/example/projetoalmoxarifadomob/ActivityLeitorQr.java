package com.example.projetoalmoxarifadomob;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityLeitorQr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicia a leitura do QR Code assim que a activity é aberta
        iniciarLeituraQr();
    }

    private void iniciarLeituraQr() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Aponte para o QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Intent retorno = new Intent();
            if (result.getContents() != null) {
                retorno.putExtra("qr_result", result.getContents());
                setResult(RESULT_OK, retorno);
            } else {
                setResult(RESULT_CANCELED);
            }
            finish();
        }
    }
}
