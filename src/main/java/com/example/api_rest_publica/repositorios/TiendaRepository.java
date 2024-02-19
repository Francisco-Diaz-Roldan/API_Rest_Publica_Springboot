package com.example.api_rest_publica.repositorios;

import com.example.api_rest_publica.modelos.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroidId")
    List<Tienda> getTiendasByCentroid(@Param("centroidId") Integer centroidId);

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroId AND t.tiendaid = :tiendaid")
    Tienda getTiendaByCentroIdAndTiendaid(@Param("centroId") Integer centroId, @Param("tiendaid") Integer tiendaid);

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroId AND t.nombre = :nombre")
    Tienda getTiendaByCentroIdAndNombre(@Param("centroId") Integer centroId, @Param("nombre") String nombre);
 /*List<Tienda> getTiendasByCentrocomercial_Centroid(Integer centroid);
    Tienda getTiendaByPlanta(String planta);
    Tienda getTiendaByTamano(Tamano tamano);
    Tienda getTiendaByPrecio(Precio precio);*/

}
