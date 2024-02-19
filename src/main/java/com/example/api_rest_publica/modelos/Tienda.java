package com.example.api_rest_publica.modelos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tienda")
public class Tienda implements Serializable {//Los atributos deben estar en el mismo orden que en la base de datos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tiendaid;
    private String nombre;
    private String tipo;

    @ManyToOne
    @JsonIgnore //Para que no haya recursividad
    @JoinColumn(name = "centroid")
    private CentroComercial centroid;
    @Transient // Para que no persista, indica que no aparece en la base de datos
    private String centronombre;
    private String planta;
    private String tamano;
    private String precio;

    public String getCentronombre() { return centroid.getNombre(); }
}