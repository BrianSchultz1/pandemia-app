package com.example.pandemiaapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pandemiaapp.activity.Paciente;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_PACIENTE = "bd_pacientes";

    private static final String TABELA_PACIENTE = "td_pacientes";

    private static final String COLUNA_CODIGO = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_IDADE = "idade";
    private static final String COLUNA_DIAS_FEBRE = "dias_febre";
    private static final String COLUNA_DIAS_TOSSE = "dias_tosse";
    private static final String COLUNA_DOR_CABECA = "dor_cabeca";
    private static final String COLUNA_VISITADOS = "visitados";


    public BancoDados(@Nullable Context context) {
        super(context, BANCO_PACIENTE, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_PACIENTE + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT UNIQUE, "
                + COLUNA_IDADE + " TEXT, " + COLUNA_DIAS_FEBRE + " INTEGER, "
                + COLUNA_DIAS_TOSSE + " INTEGER, " + COLUNA_DOR_CABECA + " TEXT, "
                + COLUNA_VISITADOS + " INTEGER DEFAULT 0)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addPaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, paciente.getNome());
        values.put(COLUNA_IDADE, paciente.getIdade());
        values.put(COLUNA_DIAS_FEBRE, paciente.getTemperaturaCorporal());
        values.put(COLUNA_DIAS_TOSSE, paciente.getDiasTosse());
        values.put(COLUNA_DOR_CABECA, paciente.getDiasDorDeCabeca());
        values.put(COLUNA_VISITADOS, String.valueOf(paciente.getPais()));

        long newRowId = db.insert(TABELA_PACIENTE, null, values);
        db.close();
        return newRowId;
    }

    void apagarPaciente(Paciente paciente) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_PACIENTE, COLUNA_CODIGO + " = ?", new String[]{String.valueOf(paciente.getCodigo())});
        close();
    }
}
