package com.example.api_rest_publica.modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "centrocomercial")
public class CentroComercial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer centroid;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horario;
    private Integer plantas;
    private Boolean parking;
    private String inauguracion;

    @OneToMany(mappedBy = "centroid", fetch = FetchType.EAGER)
    private List<Tienda> tiendas = new ArrayList<>();
}