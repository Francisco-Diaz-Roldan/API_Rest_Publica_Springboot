package com.example.api_rest_publica.repositorios;


import com.example.api_rest_publica.modelos.CentroComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CentroComercialRepository extends JpaRepository<CentroComercial, Integer> {

CentroComercial getCentroComercialByCentroid(Integer centroid);
CentroComercial getCentroComercialByNombre(String nombre);
CentroComercial getCentroComercialByDireccion(String direccion);
CentroComercial getCentroComercialByTelefono(String telefono);
List<CentroComercial> getCentroComercialByHorario(String horario);
List<CentroComercial> getCentroComercialByPlantas(Integer plantas);
List<CentroComercial> getCentroComercialByParking(Boolean parking);
List<CentroComercial> findByInauguracionBefore(String fecha);
List<CentroComercial> findByInauguracionAfter(@Param("fecha") String fecha);
@Query("SELECT c FROM CentroComercial c WHERE c.plantas < :numeroPlantas")
List<CentroComercial> findCentrosComercialesConMenosDePlantas(@Param("numeroPlantas") Integer numeroPlantas);
}