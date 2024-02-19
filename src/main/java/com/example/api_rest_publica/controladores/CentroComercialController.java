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
import java.util.Optional;

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

    //TODO hacer los post y delete de tienda

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

    @GetMapping("/centrocomercial/{id}/tiendas/planta/{planta}")
    public Tienda getTiendaByPlanta(@PathVariable Integer id, @PathVariable String planta) {
        return tiendaRepository.getTiendaByCentroIdAndPlanta(id, planta);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/tamano/{tamano}")
    public List<Tienda> getTiendasByTamano(@PathVariable Integer id, @PathVariable String tamano) {
        return tiendaRepository.getTiendasByCentroIdAndTamano(id, tamano);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/precio/{precio}")
    public List<Tienda> getTiendasByPrecio(@PathVariable Integer id, @PathVariable String precio) {
        return tiendaRepository.getTiendasByCentroIdAndPrecio(id, precio);
    }

    @GetMapping("/tiendas/nombre/{nombre}")
    public List<Tienda> getTiendasByNombre(@PathVariable String nombre) {
        return tiendaRepository.getTiendasByNombre(nombre);
    }

    @GetMapping("/tiendas/tipo/{tipo}")
    public List<Tienda> getTiendasByTipo(@PathVariable String tipo) {
        return tiendaRepository.getTiendasByTipo(tipo);
    }

    @GetMapping("/tiendas/precio/{precio}")
    public List<Tienda> getTiendasByPrecio(@PathVariable String precio) {
        return tiendaRepository.getTiendasByPrecio(precio);
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

    //Añadirle el id del centro comercial para que me po
    /*
    @PostMapping("/tiendas")
    public ResponseEntity<Tienda> crearTienda(@RequestBody Tienda nuevaTienda, @RequestParam String token) {
        if (securityService.tokenDeValidacion(token)) {
            Tienda tiendaCreada = tiendaRepository.save(nuevaTienda);
            return new ResponseEntity<>(tiendaCreada, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }*/



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

    @PostMapping("/centrocomercial/{id}")
    public ResponseEntity<Tienda> addTienda(@PathVariable Integer id, @RequestBody Tienda tienda, @RequestParam String token) {
        if (!securityService.tokenDeValidacion(token)) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CentroComercial centro = (CentroComercial) ((Optional<?>) centroComercialOptional).get();
        tienda.setCentroid(centro);
        Tienda nuevaTienda = tiendaRepository.save(tienda);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }


    /*@PostMapping("/centrocomercial/{id}")
    public ResponseEntity<Tienda> addTienda(@RequestBody Tienda tienda){
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            var result = TiendaServicio.createTiendainCentro(centro, tienda);
            if (result == null) {
                var salida = new HashMap<>();
                salida.put("status","error");
                salida.put("description","Hubo un error al cargar el centro");
                return new ResponseEntity<>(salida,HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Tienda>(result);
            }
        }
        //TODO Comprobar que el id del centro comercial existe
        // Si no existe no puedo crear nada pero si existe creo una tienda nueva y antes de guardarla se la asigno al id
    }*/
/*
    @PostMapping("/centrocomercial/{id}")
    public ResponseEntity<Tienda> addTienda(@RequestBody Tienda tienda){
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            var result = TiendaServicio.createTiendainCentro(centro, tienda);
            if (result == null) {
                var salida = new HashMap<>();
                salida.put("status","error");
                salida.put("description","Hubo un error al cargar el centro");
                return new ResponseEntity<>(salida,HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Tienda>(result);
            }
        }
        //TODO Comprobar que el id del centro comercial existe
        // Si no existe no puedo crear nada pero si existe creo una tienda nueva y antes de guardarla se la asigno al id
    }
*/
//TODO buscar centros comerciales cuyo año de inauguración sea a partir de 2007
//TODO seleccionar total tiendas de centro comercial
    @PutMapping("/centrocomercial/{id}/tiendas/{tiendaid}")
    public ResponseEntity<Tienda> updateTienda(
            @PathVariable Integer id,
            @PathVariable Integer tiendaid,
            @RequestBody Tienda nuevaTienda,
            @RequestParam String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CentroComercial centro = centroComercialOptional.get();

        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaid);
        if (tiendaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Tienda tiendaExistente = tiendaOptional.get();
        tiendaExistente.setNombre(nuevaTienda.getNombre());
        tiendaExistente.setTipo(nuevaTienda.getTipo());

        Tienda tiendaActualizada = tiendaRepository.save(tiendaExistente);
        return new ResponseEntity<>(tiendaActualizada, HttpStatus.OK);
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

    @DeleteMapping("/centrocomercial/{id}/tiendas/{tiendaid}")
    public ResponseEntity<Void> deleteTienda(
            @PathVariable Integer id,
            @PathVariable Integer tiendaid,
            @RequestParam String token
    ) {
        if (!securityService.tokenDeValidacion(token)) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}

        Optional<CentroComercial> centroComercialOptional = centroComercialRepository.findById(id);
        if (centroComercialOptional.isEmpty()) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        Optional<Tienda> tiendaOptional = tiendaRepository.findById(tiendaid);
        if (tiendaOptional.isEmpty()) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        tiendaRepository.deleteById(tiendaid);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}