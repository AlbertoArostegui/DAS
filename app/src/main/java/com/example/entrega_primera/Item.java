package com.example.entrega_primera;

public class Item {
    private String titulo;
    private String contenido;

    public Item(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return this.titulo;
    }
    public String getContenido() {
        return this.contenido;
    }
}
