package br.com.softhouse.dende.dto;

public class Erro {

    private String mensagem;

    public Erro() {

    }

    public Erro(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getmensagem() {
        return this.mensagem;
    }

    public void setmensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
