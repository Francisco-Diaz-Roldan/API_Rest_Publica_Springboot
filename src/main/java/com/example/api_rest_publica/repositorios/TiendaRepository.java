package com.example.api_rest_publica.repositorios;

import com.example.api_rest_publica.modelos.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroidId")
    List<Tienda> getTiendasByCentroid(@Param("centroidId") Integer centroidId);

   Tienda getTiendaByTiendaid(Integer tiendaid);

    /*Tienda getTiendaByTiendaid(Integer tiendaid);
    Tienda getTiendaByNombre(String nombre);
    List<Tienda> getTiendasByCentrocomercial_Centroid(Integer centroid);
    Tienda getTiendaByPlanta(String planta);
    Tienda getTiendaByTamano(Tamano tamano);
    Tienda getTiendaByPrecio(Precio precio);*/

}
