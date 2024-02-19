package com.example.api_rest_publica.controladores;

import com.example.api_rest_publica.controladores.Servicio.SecurityService;
import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.modelos.Tienda;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import com.example.api_rest_publica.repositorios.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centrocomercial")//centroscomerciales hace referencia a la tabla
public class CentroComercialController {

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/")
    public List<CentroComercial> getAllCentrosComerciales() {
        return centroComercialRepository.findAll();
    }

    @GetMapping("/centrocomercial/{id}")//Para todas las tiendas de un centro comercial
    public CentroComercial getCentroComercialByCentroid(@PathVariable Integer id) {
        return centroComercialRepository.getCentroComercialByCentroid(id);
    }

    @GetMapping("/centrocomercial/nombre/{nombre}")
    public  CentroComercial getCentroComercialByNombre(@PathVariable String nombre){
        return  centroComercialRepository.getCentroComercialByNombre(nombre);
    }

    //TODO hacer las consultas de tienda dentro de la clase Centro Comercial

    @GetMapping("/centrocomercial/{id}/tiendas")//Para todas las tiendas de un centro comercial
    public List<Tienda> getTiendasByCentroid(@PathVariable Integer id) {
        return tiendaRepository.getTiendasByCentroid(id);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/{tiendaid}")
    public Tienda getTiendaByTiendaid(@PathVariable Integer id, @PathVariable Integer tiendaid) {
        return tiendaRepository.getTiendaByCentroIdAndTiendaid(id, tiendaid);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/nombre/{nombre}")
    public Tienda getTiendaByNombre(@PathVariable Integer id, @PathVariable String nombre){
        return tiendaRepository.getTiendaByCentroIdAndNombre(id, nombre);
    }


        @GetMapping("/centrocomercial/direccion/{direccion}")
    public CentroComercial getCentroComercialByDireccion(@PathVariable String direccion) {
        return centroComercialRepository.getCentroComercialByDireccion(direccion);
    }

    @GetMapping("/centrocomercial/telefono/{telefono}")
    public CentroComercial getCentroComercialByTelefono(@PathVariable String telefono) {
        return centroComercialRepository.getCentroComercialByTelefono(telefono);
    }

    //Devuelvo una lista por si hay más de un centro comercial con ese horario, para que no de un error 500
    @GetMapping("/centrocomercial/horario/{horario}")
    public List<CentroComercial> getCentroComercialByHorario(@PathVariable String horario) {
        return centroComercialRepository.getCentroComercialByHorario(horario);
    }

    @GetMapping("/centrocomercial/plantas/{plantas}")
    public List<CentroComercial> getCentroComercialByPlantas(@PathVariable Integer plantas) {
        return centroComercialRepository.getCentroComercialByPlantas(plantas);
    }

    @GetMapping("/centrocomercial/parking/{parking}")
    public List<CentroComercial> getCentroComercialByParking(@PathVariable Boolean parking) {
        return centroComercialRepository.getCentroComercialByParking(parking);
    }

    @PostMapping("/centrocomercial")
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centrocomercial, @RequestParam String token) {
        if (securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(centroComercialRepository.save(centrocomercial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Todo hacer que e devuelva los centros comerciales inaugurados a partir de 2007

    @PutMapping("/centrocomercial/{id}")
    public ResponseEntity<CentroComercial> put(@PathVariable Integer id, @RequestBody CentroComercial
            nuevocentrocomercial, @RequestParam String token){
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            CentroComercial centrocomercial;
            var centrocomercialOpcional = centroComercialRepository.findById(id);
            if (centrocomercialOpcional.isEmpty()) {
                centrocomercial = nuevocentrocomercial;
            } else {
                centrocomercial = centrocomercialOpcional.get();
                centrocomercial.setNombre(nuevocentrocomercial.getNombre());
                centrocomercial.setDireccion(nuevocentrocomercial.getDireccion());
                centrocomercial.setTelefono(nuevocentrocomercial.getTelefono());
                centrocomercial.setHorario(nuevocentrocomercial.getHorario());
                centrocomercial.setPlantas(nuevocentrocomercial.getPlantas());
                centrocomercial.setParking(nuevocentrocomercial.getParking());
                centrocomercial.setInauguracion(nuevocentrocomercial.getInauguracion());
            }
            return new ResponseEntity<>(centroComercialRepository.save(centrocomercial), HttpStatus.OK);
        }
    }

    @DeleteMapping("/centrocomercial/delete/{id}")
    public ResponseEntity<CentroComercial> delete(@PathVariable Integer id,  @RequestParam String token){
        ResponseEntity<CentroComercial> respuesta = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if( securityService.tokenDeValidacion(token) ){
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
