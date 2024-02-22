package com.example.api_rest_publica.repositorios;

import com.example.api_rest_publica.modelos.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Interfaz de repositorio para la entidad Tienda.
 */
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    /**
     * Obtiene una lista de tiendas por el ID del centro comercial al que pertenecen.
     *
     * @param centroid ID del centro comercial.
     * @return Lista de tiendas pertenecientes al centro comercial con el ID proporcionado.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid")
    List<Tienda> getTiendasByCentroid(@Param("centroid") Integer centroid);

    /**
     * Obtiene una tienda por el ID del centro comercial y el ID de la tienda.
     *
     * @param centroid  ID del centro comercial.
     * @param tiendaid  ID de la tienda.
     * @return Tienda correspondiente al centro comercial y al ID de la tienda proporcionados.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid AND t.tiendaid = :tiendaid")
    Tienda getTiendaByCentroIdAndTiendaid(@Param("centroid") Integer centroid, @Param("tiendaid") Integer tiendaid);

    /**
     * Obtiene una tienda por el ID del centro comercial y el nombre de la tienda.
     *
     * @param centroid ID del centro comercial.
     * @param nombre   Nombre de la tienda.
     * @return Tienda correspondiente al centro comercial y al nombre proporcionados.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid AND t.nombre = :nombre")
    Tienda getTiendaByCentroIdAndNombre(@Param("centroid") Integer centroid, @Param("nombre") String nombre);

    /**
     * Obtiene una tienda por el ID del centro comercial y el número de planta.
     *
     * @param centroid ID del centro comercial.
     * @param planta   Número de planta.
     * @return Tienda correspondiente al centro comercial y al número de planta proporcionados.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid AND t.planta = :planta")
    Tienda getTiendaByCentroIdAndPlanta(@Param("centroid") Integer centroid, @Param("planta") Integer planta);

    /**
     * Obtiene una lista de tiendas por el ID del centro comercial y el tamaño.
     *
     * @param centroid ID del centro comercial.
     * @param tamano   Tamaño de la tienda.
     * @return Lista de tiendas correspondientes al centro comercial y al tamaño proporcionados.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid AND t.tamano = :tamano")
    List<Tienda> getTiendasByCentroIdAndTamano(@Param("centroid") Integer centroid, @Param("tamano") String tamano);

    /**
     * Obtiene una lista de tiendas por el ID del centro comercial y el precio.
     *
     * @param centroid ID del centro comercial.
     * @param precio   Precio de la tienda.
     * @return Lista de tiendas correspondientes al centro comercial y al precio proporcionados.
     */
    @Query("SELECT t FROM Tienda t WHERE t.centroid.centroid = :centroid AND t.precio = :precio")
    List<Tienda> getTiendasByCentroIdAndPrecio(@Param("centroid") Integer centroid, @Param("precio") String precio);

    /**
     * Obtiene una lista de tiendas por el nombre de la tienda.
     *
     * @param nombre Nombre de la tienda.
     * @return Lista de tiendas con el nombre proporcionado.
     */
    @Query("SELECT t FROM Tienda t WHERE t.nombre = :nombre")
    List<Tienda> getTiendasByNombre(@Param("nombre") String nombre);

    /**
     * Obtiene una lista de tiendas por el tipo de tienda.
     *
     * @param tipo Tipo de tienda.
     * @return Lista de tiendas con el tipo proporcionado.
     */
    @Query("SELECT t FROM Tienda t WHERE t.tipo = :tipo")
    List<Tienda> getTiendasByTipo(@Param("tipo") String tipo);

    /**
     * Obtiene una lista de tiendas por el precio de la tienda.
     *
     * @param precio Precio de la tienda.
     * @return Lista de tiendas con el precio proporcionado.
     */
    @Query("SELECT t FROM Tienda t WHERE t.precio = :precio")
    List<Tienda> getTiendasByPrecio(@Param("precio") String precio);
}