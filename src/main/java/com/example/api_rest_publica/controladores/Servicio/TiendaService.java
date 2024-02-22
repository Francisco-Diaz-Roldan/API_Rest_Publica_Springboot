package com.example.api_rest_publica.controladores.Servicio;

import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.modelos.Tienda;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import com.example.api_rest_publica.repositorios.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TiendaService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    //Para añadir tiendas
    public ResponseEntity<Tienda> crearTienda(Integer id, Tienda tienda, String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CentroComercial centro = centroComercialOptional.get();
        tienda.setCentroid(centro);

        Tienda nuevaTienda = tiendaRepository.save(tienda);

        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    //Para actualizar tiendas
    public ResponseEntity<Tienda> actualizarTienda(Integer id, Integer tiendaid, Tienda nuevaTienda, String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaid);
        if (tiendaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Tienda tiendaExistente = tiendaOptional.get();
        tiendaExistente.setNombre(nuevaTienda.getNombre());
        tiendaExistente.setTipo(nuevaTienda.getTipo());
        tiendaExistente.setCentroid(nuevaTienda.getCentroid());
        tiendaExistente.setPlanta(nuevaTienda.getPlanta());
        tiendaExistente.setTamano(nuevaTienda.getTamano());
        tiendaExistente.setPrecio(nuevaTienda.getPrecio());

        Tienda tiendaActualizada = tiendaRepository.save(tiendaExistente);
        return new ResponseEntity<>(tiendaActualizada, HttpStatus.OK);
    }

    //Para borrar tiendas
    public ResponseEntity<Void> eliminarTienda(Integer id, Integer tiendaid, String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaid);
        if (tiendaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tiendaRepository.deleteById(tiendaid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
