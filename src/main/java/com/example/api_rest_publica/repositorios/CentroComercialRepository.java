package com.example.api_rest_publica.repositorios;


import com.example.api_rest_publica.modelos.CentroComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** Interfaz de repositorio para la entidad CentroComercial.*/
public interface CentroComercialRepository extends JpaRepository<CentroComercial, Integer> {
    /**
     * Obtiene un centro comercial por su ID.
     *
     * @param centroid ID del centro comercial.
     * @return CentroComercial correspondiente al ID proporcionado.
     */
    CentroComercial getCentroComercialByCentroid(Integer centroid);

    /**
     * Obtiene un centro comercial por su nombre.
     *
     * @param nombre Nombre del centro comercial.
     * @return CentroComercial correspondiente al nombre proporcionado.
     */
    CentroComercial getCentroComercialByNombre(String nombre);

    /**
     * Obtiene un centro comercial por su dirección.
     *
     * @param direccion Dirección del centro comercial.
     * @return CentroComercial correspondiente a la dirección proporcionada.
     */
    CentroComercial getCentroComercialByDireccion(String direccion);

    /**
     * Obtiene un centro comercial por su número de teléfono.
     *
     * @param telefono Número de teléfono del centro comercial.
     * @return CentroComercial correspondiente al número de teléfono proporcionado.
     */
    CentroComercial getCentroComercialByTelefono(String telefono);

    /**
     * Obtiene una lista de centros comerciales por su horario.
     *
     * @param horario Horario del centro comercial.
     * @return Lista de centros comerciales con el horario proporcionado.
     */
    List<CentroComercial> getCentroComercialByHorario(String horario);

    /**
     * Obtiene una lista de centros comerciales por el número de plantas.
     *
     * @param plantas Número de plantas del centro comercial.
     * @return Lista de centros comerciales con el número de plantas proporcionado.
     */
    List<CentroComercial> getCentroComercialByPlantas(Integer plantas);

    /**
     * Obtiene una lista de centros comerciales por la disponibilidad de parking.
     *
     * @param parking Indica si el centro comercial tiene parking.
     * @return Lista de centros comerciales con la disponibilidad de parking proporcionada.
     */
    List<CentroComercial> getCentroComercialByParking(Boolean parking);

    /**
     * Obtiene una lista de centros comerciales inaugurados antes de la fecha proporcionada.
     *
     * @param fecha Fecha límite de inauguración.
     * @return Lista de centros comerciales inaugurados antes de la fecha proporcionada.
     */
    List<CentroComercial> findByInauguracionBefore(String fecha);

    /**
     * Obtiene una lista de centros comerciales inaugurados después de la fecha proporcionada.
     *
     * @param fecha Fecha de inauguración.
     * @return Lista de centros comerciales inaugurados después de la fecha proporcionada.
     */
    @Query("SELECT c FROM CentroComercial c WHERE c.inauguracion > :fecha")
    List<CentroComercial> findByInauguracionAfter(@Param("fecha") String fecha);

    /**
     * Obtiene una lista de centros comerciales con menos de un número específico de plantas.
     *
     * @param numeroPlantas Número máximo de plantas permitido.
     * @return Lista de centros comerciales con menos de plantas proporcionado.
     */
    @Query("SELECT c FROM CentroComercial c WHERE c.plantas < :numeroPlantas")
    List<CentroComercial> findCentrosComercialesConMenosDePlantas(@Param("numeroPlantas") Integer numeroPlantas);
}