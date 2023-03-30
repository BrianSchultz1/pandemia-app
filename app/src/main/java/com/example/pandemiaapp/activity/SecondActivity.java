package com.example.pandemiaapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pandemiaapp.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView statusTextView = findViewById(R.id.status_text);

        String statusString = getIntent().getStringExtra("status");
        Paciente.Status status = Paciente.Status.valueOf(statusString);

        String statusText;
        switch (status) {
            case LIBERADO:
                statusText = "Este paciente está liberado.";
                break;
            case EM_QUARENTENA:
                statusText = "Este paciente deve ser colocado em quarentena.";
                break;
            case TRATAMENTO:
                statusText = "Este paciente deve ser internado para tratamento.";
                break;
            default:
                statusText = "Não foi possível determinar o status do paciente.";
                break;
        }
        statusTextView.setText(statusText);
    }
}