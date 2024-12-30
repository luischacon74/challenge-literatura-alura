package com.lfch.challenge_literatura_alura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "libro_id"), // Clave foránea a libros
            inverseJoinColumns = @JoinColumn(name = "autor_id") // Clave foránea a autores
    )// Clave foránea a autores
    private List<Autor> autor;
    private String lenguaje;
    private Integer numeroDescargas;


    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", lenguaje=" + lenguaje +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
    public Libro() {
    }

    public Libro(DatosLibro datos){
        this.titulo = datos.titulo();
        this.autor = datos.autor().stream()
                .map(Autor::new) // Usar el constructor de Autor que acepta DatosAutor
                .toList();
        this.lenguaje = datos.lenguaje().get(0).trim();
        this.numeroDescargas = datos.numeroDescargas();

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
