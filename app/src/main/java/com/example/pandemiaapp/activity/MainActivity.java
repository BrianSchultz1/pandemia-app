package com.example.pandemiaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pandemiaapp.R;
import com.example.pandemiaapp.util.BancoDados;

public class MainActivity extends AppCompatActivity {
    private EditText editNome, editIdade, editTosse, editTemperatura, editDorCabeca;
    private Spinner spinnerPais;
    private BancoDados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // inicializar as views e o banco de dados
        db = new BancoDados(this);
        editNome = findViewById(R.id.editNome);
        editIdade = findViewById(R.id.editIdade);
        editTosse = findViewById(R.id.editTosse);
        editTemperatura = findViewById(R.id.editTemperatura);
        editDorCabeca = findViewById(R.id.editDorCabeca);
        spinnerPais = findViewById(R.id.spinnerPais);

        ArrayAdapter<Paciente.Pais> adapter = new ArrayAdapter<Paciente.Pais>(this, android.R.layout.simple_spinner_item, Paciente.Pais.values()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(Paciente.Pais.values()[position].name());
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setText(Paciente.Pais.values()[position].name());
                return view;
            }
        };
        spinnerPais.setAdapter(adapter);

        // configurar o botão de status do paciente
        Button buttonStatus = findViewById(R.id.status_button);
        buttonStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText nomeEditText = findViewById(R.id.editNome);
                    EditText idadeEditText = findViewById(R.id.editIdade);
                    EditText temperaturaEditText = findViewById(R.id.editTemperatura);
                    Spinner spinnerPais = findViewById(R.id.spinnerPais);

                    String nome = nomeEditText.getText().toString();
                    int idade = Integer.parseInt(idadeEditText.getText().toString());
                    float temperatura = Float.parseFloat(temperaturaEditText.getText().toString());
                    int diasTosse = Integer.parseInt(editTosse.getText().toString());
                    int diasDorDeCabeca = Integer.parseInt(editDorCabeca.getText().toString());
                    Paciente.Pais paisSelecionado = Paciente.Pais.valueOf(spinnerPais.getSelectedItem().toString());

                    Paciente paciente = new Paciente(nome, idade, temperatura, diasTosse, diasDorDeCabeca, paisSelecionado);
                    paciente.analisar();

                    Toast.makeText(MainActivity.this, "Paciente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    editNome.setText("");
                    editIdade.setText("");
                    editTosse.setText("");
                    editTemperatura.setText("");
                    editDorCabeca.setText("");

                    spinnerPais.setSelection(0);
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("status", paciente.getStatus().toString());
                    startActivity(intent);

                    if (nome.isEmpty()) {
                        editNome.setError("Este campo é obrigatório");
                        return;
                    } else if (idade < 0 || temperatura < 0 || diasTosse < 0 || diasDorDeCabeca < 0) {
                        Toast.makeText(MainActivity.this, "Os valores numéricos devem ser positivos", Toast.LENGTH_LONG).show();
                    } else if (temperatura < 25 || temperatura > 50) {
                        Toast.makeText(MainActivity.this, "A temperatura deve estar entre 25 e 50 graus", Toast.LENGTH_LONG).show();
                    } else {
                        db.addPaciente(paciente);
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Insira valores válidos nos campos acima", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}