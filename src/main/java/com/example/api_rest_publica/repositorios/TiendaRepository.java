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

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroId AND t.planta = :planta")
    Tienda getTiendaByCentroIdAndPlanta(@Param("centroId") Integer centroId, @Param("planta") String planta);

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroId AND t.tamano = :tamano")
    List<Tienda> getTiendasByCentroIdAndTamano(@Param("centroId") Integer centroId, @Param("tamano") String tamano);

    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroId AND t.precio = :precio")
    List<Tienda> getTiendasByCentroIdAndPrecio(@Param("centroId") Integer centroId, @Param("precio") String precio);

    @Query("SELECT t FROM Tienda t WHERE t.nombre = :nombre")
    List<Tienda> getTiendasByNombre(@Param("nombre") String nombre);

    @Query("SELECT t FROM Tienda t WHERE t.tipo = :tipo")
    List<Tienda> getTiendasByTipo(@Param("tipo") String tipo);

    @Query("SELECT t FROM Tienda t WHERE t.precio = :precio")
    List<Tienda> getTiendasByPrecio(@Param("precio") String precio);
}