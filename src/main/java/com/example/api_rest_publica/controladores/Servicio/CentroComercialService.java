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

@Service
public class CentroComercialService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    public List<CentroComercial> buscarCentrosComercialesInauguradosAntesDelAno(Year year) {
        // Construyo un string con el formato esperado
        String fechaLimite = "01/01/" + year.toString();
        return centroComercialRepository.findByInauguracionBefore(fechaLimite);
    }

    public List<CentroComercial> buscarCentrosComercialesInauguradosDespuesDelAno(Year year) {
        // Construyo un string con el formato esperado
        String fechaLimite = "01/01/" + year.toString();
        return centroComercialRepository.findByInauguracionAfter(fechaLimite);
    }

    public List<CentroComercial> buscarCentrosComercialesConMenosDePlantasQue(Integer numeroPlantas) {
        return centroComercialRepository.findCentrosComercialesConMenosDePlantas(numeroPlantas);
    }

    //Para crear un nuevo Centro Comercial (Post)
    public ResponseEntity<CentroComercial> crearCentroComercial(CentroComercial centroComercial, String token) {
        if (securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(centroComercialRepository.save(centroComercial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //Para actualizar un Centro Comercial (Put)
    public ResponseEntity<CentroComercial> actualizarCentroComercial(Integer id, CentroComercial nuevoCentroComercial, String token) {
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
