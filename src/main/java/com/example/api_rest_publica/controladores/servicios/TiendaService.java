package com.example.api_rest_publica.controladores.servicios;

import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.modelos.Tienda;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import com.example.api_rest_publica.repositorios.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Clase que proporciona servicios relacionados con las Tiendas en un Centro Comercial.
 */
@Service
public class TiendaService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    /**
     * Crea una nueva tienda en un centro comercial específico.
     *
     * @param id     ID del centro comercial.
     * @param tienda Datos de la tienda a crear.
     * @param token  Token de autenticación.
     * @return ResponseEntity con la tienda creada o estado de error.
     */
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

    /**
     * Actualiza una tienda en un centro comercial específico.
     *
     * @param id        ID del centro comercial.
     * @param tiendaid  ID de la tienda a actualizar.
     * @param nuevaTienda Datos actualizados de la tienda.
     * @param token     Token de autenticación.
     * @return ResponseEntity con la tienda actualizada o estado de error.
     */
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

        // Comprueba que el nuevo centro comercial sea válido antes de establecerlo
        CentroComercial nuevoCentroComercial = centroComercialOptional.get();
        if (nuevoCentroComercial != null) {
            tiendaExistente.setCentroid(nuevoCentroComercial);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Guarda la tienda actualizada
        Tienda tiendaActualizada = tiendaRepository.save(tiendaExistente);

        // Comprueba el estado persistente de la tienda actualizada (para propósitos de depuración)
        System.out.println("Tienda actualizada con ID: " + tiendaActualizada.getTiendaid());

        return new ResponseEntity<>(tiendaActualizada, HttpStatus.OK);
    }

    /**
     * Elimina una tienda en un centro comercial específico.
     *
     * @param id       ID del centro comercial.
     * @param tiendaid ID de la tienda a eliminar.
     * @param token    Token de autenticación.
     * @return ResponseEntity con el estado de la operación.
     */
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

    /**
     * Elimina todas las tiendas asociadas a un centro comercial.
     *
     * @param centroid ID del centro comercial del cual se eliminarán las tiendas.
     */
    public void eliminarTiendasDeCentroComercial(Integer centroid) {
        tiendaRepository.deleteByCentroidCentroid(centroid);
    }
}
