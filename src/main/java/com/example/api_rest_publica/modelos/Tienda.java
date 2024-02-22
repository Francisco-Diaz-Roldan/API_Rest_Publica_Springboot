package com.example.api_rest_publica.modelos;


import com.example.api_rest_publica.enumerados.Precio;
import com.example.api_rest_publica.enumerados.Tamano;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Clase que representa una tienda.
 * Esta clase se utiliza como entidad JPA para mapear a la tabla "tienda" en la base de datos.
 */
@Data
@Entity
@Table(name = "tienda")
public class Tienda implements Serializable {//Los atributos deben estar en el mismo orden que en la base de datos

    /** Identificador único de la tienda.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tiendaid;
    /** Nombre de la tienda.*/
    private String nombre;
    /** Tipo de la tienda.*/
    private String tipo;

    /** Centro comercial al que pertenece la tienda.*/
    @ManyToOne
    @JsonIgnore //Para que no haya recursividad
    @JoinColumn(name = "centroid")
    private CentroComercial centroid;

    /** Nombre del centro comercial (transient, no persistente en la base de datos).*/
    @Transient // Para que no persista, indica que no aparece en la base de datos
    private String centronombre;
    /** Planta en la que se encuentra la tienda.*/
    private Integer planta;
    /** Tamaño de la tienda (pequena, mediana, grande).*/
    @Enumerated(EnumType.STRING)
    private Tamano tamano;
    /** Nivel de precio de la tienda (alto, medio, bajo).*/
    @Enumerated(EnumType.STRING)
    private Precio precio;

    /**
     * Obtiene el nombre del centro comercial al que pertenece la tienda.
     *
     * @return Nombre del centro comercial.
     */
    public String getCentronombre() { return centroid.getNombre(); }
}