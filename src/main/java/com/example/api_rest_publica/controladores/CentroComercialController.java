package com.example.api_rest_publica.controladores;

import com.example.api_rest_publica.controladores.Servicio.CentroComercialService;
import com.example.api_rest_publica.controladores.Servicio.TiendaService;
import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.modelos.Tienda;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import com.example.api_rest_publica.repositorios.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/centrocomercial")//centroscomerciales hace referencia a la tabla
public class CentroComercialController {

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private TiendaService tiendaServicio;

    @Autowired
    private CentroComercialService centroComercialService;

    //Get de Centro Comercial
    @GetMapping("/")
    public List<CentroComercial> getAllCentrosComerciales() {
        return centroComercialRepository.findAll();
    }

    @GetMapping("/centrocomercial/{id}")//Para todas las tiendas de un centro comercial
    public CentroComercial getCentroComercialByCentroid(@PathVariable Integer id) {
        return centroComercialRepository.getCentroComercialByCentroid(id);
    }

    @GetMapping("/centrocomercial/nombre/{nombre}")
    public CentroComercial getCentroComercialByNombre(@PathVariable String nombre) {
        return centroComercialRepository.getCentroComercialByNombre(nombre);
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

    @GetMapping("/centroscomerciales/parking/{parking}")
    public List<CentroComercial> getCentroComercialByParking(@PathVariable Boolean parking) {
        return centroComercialRepository.getCentroComercialByParking(parking);
    }

    @GetMapping("/centroscomerciales/inauguradosAntesDelAno/{ano}")
    public List<CentroComercial> centrosComercialesInauguradosAntesDelAno(@PathVariable String ano) {
        try {
            int year = Integer.parseInt(ano);
            Year yearObject = Year.of(year);
            return centroComercialService.buscarCentrosComercialesInauguradosAntesDelAno(yearObject);
        } catch (NumberFormatException e) {
            // Manejo el caso en que 'ano' no sea un número válido
            return (List<CentroComercial>) ResponseEntity.badRequest().body("El año introducido no es válido");
        }
    }

    @GetMapping("/centroscomerciales/inauguradosDespuesDelAno/{ano}")
    public List<CentroComercial> centrosComercialesInauguradosDespuesDelAno(@PathVariable String ano) {
        try {
            int year = Integer.parseInt(ano);
            Year yearObject = Year.of(year);
            return centroComercialService.buscarCentrosComercialesInauguradosDespuesDelAno(yearObject);
        } catch (NumberFormatException e) {
            // Manejo el caso en que 'ano' no sea un número válido
            return (List<CentroComercial>) ResponseEntity.badRequest().body("El año introducido no es válido");
        }
    }

    @GetMapping("/menosDe/{numeroPlantas}/plantas")
    public List<CentroComercial> getCentrosComercialesConMenosDePlantas(@PathVariable Integer numeroPlantas) {
        return centroComercialService.buscarCentrosComercialesConMenosDePlantasQue(numeroPlantas);
    }

    //Post de Centro Comercial
    @PostMapping
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centroComercial, @RequestParam String token) {
        return centroComercialService.crearCentroComercial(centroComercial, token);
    }

    //Put de Centro Comercial
    @PutMapping("/{id}")
    public ResponseEntity<CentroComercial> put(@PathVariable Integer id, @RequestBody CentroComercial nuevoCentroComercial, @RequestParam String token) {
        return centroComercialService.actualizarCentroComercial(id, nuevoCentroComercial, token);
    }

    //Delete de Centro Comercial
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CentroComercial> delete(@PathVariable Integer id, @RequestParam String token) {
        return centroComercialService.eliminarCentroComercial(id, token);
    }


    //Gets de Tienda
    @GetMapping("/centrocomercial/{id}/tiendas")//Para todas las tiendas de un centro comercial
    public List<Tienda> getTiendasByCentroid(@PathVariable Integer id) {
        return tiendaRepository.getTiendasByCentroid(id);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/{tiendaid}")
    public Tienda getTiendaByTiendaid(@PathVariable Integer id, @PathVariable Integer tiendaid) {
        return tiendaRepository.getTiendaByCentroIdAndTiendaid(id, tiendaid);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/nombre/{nombre}")
    public Tienda getTiendaByNombre(@PathVariable Integer id, @PathVariable String nombre) {
        return tiendaRepository.getTiendaByCentroIdAndNombre(id, nombre);
    }

    @GetMapping("/centrocomercial/{id}/tiendas/planta/{planta}")
    public Tienda getTiendaByPlanta(@PathVariable Integer id, @PathVariable Integer planta) {
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

    //Post de Tienda
    @PostMapping("/centrocomercial/{id}")
    public ResponseEntity<Tienda> addTienda(@PathVariable Integer id, @RequestBody Tienda tienda, @RequestParam String token) {
        return tiendaServicio.crearTienda(id, tienda, token);
    }

    //Put de Tienda
    @PutMapping("/centrocomercial/{id}/tiendas/{tiendaid}")
    public ResponseEntity<Tienda> updateTienda(@PathVariable Integer id, @PathVariable Integer tiendaid, @RequestBody Tienda nuevaTienda, @RequestParam String token) {
        return tiendaServicio.actualizarTienda(id, tiendaid, nuevaTienda, token);
    }

    //Delete de Tienda
    @DeleteMapping("/{id}/tiendas/{tiendaid}")
    public ResponseEntity<Void> deleteTienda(@PathVariable Integer id, @PathVariable Integer tiendaid, @RequestParam String token) {
        return tiendaServicio.eliminarTienda(id, tiendaid, token);
    }
}