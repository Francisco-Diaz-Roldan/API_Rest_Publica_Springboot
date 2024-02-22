package com.example.api_rest_publica.modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un centro comercial.
 * Esta clase se utiliza como entidad JPA para mapear a la tabla "centrocomercial" en la base de datos.
 */
@Data
@Entity
@Table(name = "centrocomercial")
public class CentroComercial implements Serializable {
    /** Identificador único del centro comercial.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer centroid;
    /** Nombre del centro comercial.*/
    private String nombre;
    /** Dirección del centro comercial.*/
    private String direccion;
    /** Número de teléfono del centro comercial.*/
    private String telefono;
    /** Horario de operación del centro comercial.*/
    private String horario;
    /** Número de plantas del centro comercial.*/
    private Integer plantas;
    /** Indica si el centro comercial cuenta con estacionamiento.*/
    private Boolean parking;
    /** Fecha de inauguración del centro comercial.*/
    private String inauguracion;

    /** Lista de tiendas asociadas al centro comercial.*/
    @OneToMany(mappedBy = "centroid", fetch = FetchType.EAGER)
    private List<Tienda> tiendas = new ArrayList<>();
}