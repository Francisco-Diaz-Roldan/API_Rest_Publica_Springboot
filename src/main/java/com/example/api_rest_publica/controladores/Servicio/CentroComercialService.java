package com.example.api_rest_publica.controladores.Servicio;

import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;


/**
 * Clase que proporciona servicios relacionados con los Centros Comerciales.
 */
@Service
public class CentroComercialService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    /**
     * Busca centros comerciales inaugurados antes del año indicado.
     *
     * @param ano Año de inauguración.
     * @return Lista de centros comerciales inaugurados antes del año indicado.
     */
    public List<CentroComercial> buscarCentrosComercialesInauguradosAntesDelAno(Year ano) {
        // Construyo un string con el formato esperado
        String fecha = "01/01/" + ano.toString();
        return centroComercialRepository.findByInauguracionBefore(fecha);
    }

    /**
     * Busca centros comerciales inaugurados después del año indicado.
     *
     * @param ano Año de inauguración.
     * @return Lista de centros comerciales inaugurados después del año indicado.
     */
    public List<CentroComercial> buscarCentrosComercialesInauguradosDespuesDelAno(Year ano) {
        // Construyo un string con el formato esperado
        String fecha = "01/01/" + ano.toString();
        return centroComercialRepository.findByInauguracionAfter(fecha);
    }

    /**
     * Busca centros comerciales con menos de cierto número de plantas.
     *
     * @param numeroPlantas Número máximo de plantas permitido.
     * @return Lista de centros comerciales con menos de cierto número de plantas.
     */
    public List<CentroComercial> buscarCentrosComercialesConMenosDePlantasQue(Integer numeroPlantas) {
        return centroComercialRepository.findCentrosComercialesConMenosDePlantas(numeroPlantas);
    }

    /**
     * Crea un nuevo centro comercial.
     *
     * @param centroComercial Centro comercial a crear.
     * @param token           Token de autenticación.
     * @return ResponseEntity con el centro comercial creado o estado de error.
     */
    //Para crear un nuevo Centro Comercial (Post)
    public ResponseEntity<CentroComercial> crearCentroComercial(CentroComercial centroComercial, String token) {
        if (securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(centroComercialRepository.save(centroComercial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Actualiza un centro comercial existente.
     *
     * @param id                ID del centro comercial a actualizar.
     * @param nuevoCentroComercial Datos actualizados del centro comercial.
     * @param token             Token de autenticación.
     * @return ResponseEntity con el centro comercial actualizado o estado de error.
     */
    //Para actualizar un Centro Comercial (Put)
    public ResponseEntity<CentroComercial> actualizarCentroComercial(Integer id, CentroComercial nuevoCentroComercial,
                                                                     String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            CentroComercial centroComercial;
            Optional<CentroComercial> centroComercialOpcional = centroComercialRepository.findById(id);

            if (centroComercialOpcional.isEmpty()) {
                centroComercial = nuevoCentroComercial;
            } else {
                centroComercial = centroComercialOpcional.get();
                centroComercial.setNombre(nuevoCentroComercial.getNombre());
                centroComercial.setDireccion(nuevoCentroComercial.getDireccion());
                centroComercial.setTelefono(nuevoCentroComercial.getTelefono());
                centroComercial.setHorario(nuevoCentroComercial.getHorario());
                centroComercial.setPlantas(nuevoCentroComercial.getPlantas());
                centroComercial.setParking(nuevoCentroComercial.getParking());
                centroComercial.setInauguracion(nuevoCentroComercial.getInauguracion());
            }
            return new ResponseEntity<>(centroComercialRepository.save(centroComercial), HttpStatus.OK);
        }
    }

    /**
     * Elimina un centro comercial por su ID.
     *
     * @param id    ID del centro comercial a eliminar.
     * @param token Token de autenticación.
     * @return ResponseEntity con el centro comercial eliminado o estado de error.
     */
    //Para eliminar un Centro Comercial (Delete)
    public ResponseEntity<CentroComercial> eliminarCentroComercial(Integer id, String token) {
        ResponseEntity<CentroComercial> respuesta = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (securityService.tokenDeValidacion(token)) {
            CentroComercial salida = new CentroComercial();

            if (centroComercialRepository.existsById(id)) {
                salida = centroComercialRepository.findById(id).get();
                centroComercialRepository.deleteById(id);
                respuesta = new ResponseEntity<>(salida, HttpStatus.OK);
            } else {
                respuesta = new ResponseEntity<>(salida, HttpStatus.NOT_FOUND);
            }
        }
        return respuesta;
    }
}