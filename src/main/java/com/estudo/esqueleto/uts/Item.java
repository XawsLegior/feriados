package com.estudo.esqueleto.uts;

public class Item {
    private String data;
    private String evento;

    public Item(String data, String evento) {
        this.data = data;
        this.evento = evento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

}
