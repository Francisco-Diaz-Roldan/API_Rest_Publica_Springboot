package com.example.api_rest_publica.controladores.servicios;

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

    @Autowired
    private TiendaService tiendaService;

    /**
     * Busca centros comerciales inaugurados antes del año indicado.
     *
     * @param ano Año de inauguración.
     * @return Lista de centros comerciales inaugurados antes del año indicado.
     */
    public List<CentroComercial> buscarCentrosComercialesInauguradosAntesDelAno(Year ano) {
        // Creo un string con el formato de fecha 01/01/ano
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
        // Creo un string con el formato de fecha 01/01/ano
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
     * Elimina un centro comercial por su ID.
     *
     * @param id    ID del centro comercial a eliminar.
     * @param token Token de autenticación.
     * @return ResponseEntity con el centro comercial eliminado o estado de error.
     */
    //Para eliminar un Centro Comercial (Delete)
    public ResponseEntity<CentroComercial> eliminarCentroComercial(Integer id, String token) {
        ResponseEntity<CentroComercial> respuesta = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (securityService.validarToken(token)) {
            CentroComercial salida = new CentroComercial();

            if (centroComercialRepository.existsById(id)) {
                salida = centroComercialRepository.findById(id).get();

                tiendaService.eliminarTiendasDeCentroComercial(id);//Esta linea es para borrar todas sus tiendas por id
                centroComercialRepository.deleteById(id);
                respuesta = new ResponseEntity<>(salida, HttpStatus.OK);
            } else {
                respuesta = new ResponseEntity<>(salida, HttpStatus.NOT_FOUND);
            }
        }
        return respuesta;
    }
}