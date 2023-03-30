package com.example.pandemiaapp.activity;

public class Paciente {
    private int codigo;
    private String nome;
    private int idade;
    private float temperaturaCorporal;
    private int diasTosse;
    private int diasDorDeCabeca;
    private Status status;
    private Pais pais;


    public Paciente(int codigo, String nome, int idade, float temperaturaCorporal, int diasTosse, int diasDorDeCabeca, Pais pais) {
        this.codigo = codigo;
        this.nome = nome;
        this.idade = idade;
        this.temperaturaCorporal = temperaturaCorporal;
        this.diasTosse = diasTosse;
        this.diasDorDeCabeca = diasDorDeCabeca;
        this.status = Status.AGUARDANDO_ANALISE;
        this.pais = pais;
    }

    public Paciente(String nome, int idade, float temperaturaCorporal, int diasTosse, int diasDorDeCabeca, Pais pais) {
        this(0, nome, idade, temperaturaCorporal, diasTosse, diasDorDeCabeca, pais);
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getTemperaturaCorporal() {
        return temperaturaCorporal;
    }

    public void setTemperaturaCorporal(float temperaturaCorporal) {
        this.temperaturaCorporal = temperaturaCorporal;
    }

    public int getDiasTosse() {
        return diasTosse;
    }

    public void setDiasTosse(int diasTosse) {
        this.diasTosse = diasTosse;
    }

    public int getDiasDorDeCabeca() {
        return diasDorDeCabeca;
    }

    public void setDiasDorDeCabeca(int diasDorDeCabeca) {
        this.diasDorDeCabeca = diasDorDeCabeca;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    private boolean temFebre() {
        return temperaturaCorporal > 37.0F;
    }

    public void analisar() {
        if (this.pais.isDeRisco()) {
            if (temFebre() && getDiasTosse() > 5 && getDiasDorDeCabeca() > 5) {
                this.status = Status.TRATAMENTO;
            } else if ((this.getIdade() < 10 || this.getIdade() > 60) && (temFebre() || getDiasDorDeCabeca() > 3 || getDiasTosse() > 5)) {
                this.status = Status.EM_QUARENTENA;
            } else if ((this.getIdade() >= 10 && this.getIdade() <= 60) && temFebre() && getDiasDorDeCabeca() > 5 && getDiasTosse() > 5) {
                this.status = Status.EM_QUARENTENA;
            } else {
                this.status = Status.LIBERADO;
            }
        } else if (temFebre() || getDiasDorDeCabeca() > 3 || getDiasTosse() > 5) {
            this.status = Status.EM_QUARENTENA;
        } else {
            this.status = Status.LIBERADO;
        }
    }

    public enum Pais {
        Nenhum(false),
        China(true),
        EUA(true),
        Italia(true),
        Indonesia(true),
        Portugal(true);

        private boolean deRisco;

        Pais(boolean deRisco) {
            this.deRisco = deRisco;
        }

        public boolean isDeRisco() {
            return deRisco;
        }
    }

    public enum Status {
        AGUARDANDO_ANALISE,
        LIBERADO,
        EM_QUARENTENA,
        TRATAMENTO
    }
}
