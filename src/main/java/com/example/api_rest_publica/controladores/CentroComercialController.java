package com.example.api_rest_publica.controladores;

import com.example.api_rest_publica.controladores.servicios.CentroComercialService;
import com.example.api_rest_publica.controladores.servicios.SecurityService;
import com.example.api_rest_publica.controladores.servicios.TiendaService;
import com.example.api_rest_publica.modelos.CentroComercial;
import com.example.api_rest_publica.modelos.Tienda;
import com.example.api_rest_publica.repositorios.CentroComercialRepository;
import com.example.api_rest_publica.repositorios.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

/**
 * Controlador que maneja las operaciones relacionadas con los Centros Comerciales en la API REST.
 */
@RestController
@RequestMapping("/api/centrocomercial")//centroscomerciales hace referencia a la tabla
public class CentroComercialController {

    @Autowired
    private CentroComercialRepository centroComercialRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private CentroComercialService centroComercialService;

    @Autowired
    private SecurityService securityService;

    /**
     * Obtiene todos los centros comerciales.
     *
     * @return Lista de todos los centros comerciales.
     */
    @GetMapping("/")
    public List<CentroComercial> getAllCentrosComerciales() {
        return centroComercialRepository.findAll();
    }

    //Gets de Centro Comercial
    /**
     * Obtiene un centro comercial por su ID.
     *
     * @param id ID del centro comercial.
     * @return Centro comercial con el ID indicado.
     */
    @GetMapping("/centrocomercial/{id}")//Para todas las tiendas de un centro comercial
    public CentroComercial getCentroComercialByCentroid(@PathVariable Integer id) {
        return centroComercialRepository.getCentroComercialByCentroid(id);
    }

    /**
     * Obtiene un centro comercial por su nombre.
     *
     * @param nombre Nombre del centro comercial.
     * @return Centro comercial con el nombre indicado.
     */
    @GetMapping("/centrocomercial/nombre/{nombre}")
    public CentroComercial getCentroComercialByNombre(@PathVariable String nombre) {
        return centroComercialRepository.getCentroComercialByNombre(nombre);
    }

    /**
     * Obtiene un centro comercial por su direccion.
     *
     * @param direccion Direccion del centro comercial.
     * @return Centro comercial con la direccion indicada.
     */
    @GetMapping("/centrocomercial/direccion/{direccion}")
    public CentroComercial getCentroComercialByDireccion(@PathVariable String direccion) {
        return centroComercialRepository.getCentroComercialByDireccion(direccion);
    }

    /**
     * Obtiene un centro comercial por su telefono.
     *
     * @param telefono Telefono del centro comercial.
     * @return Centro comercial con el telefono indicado.
     */
    @GetMapping("/centrocomercial/telefono/{telefono}")
    public CentroComercial getCentroComercialByTelefono(@PathVariable String telefono) {
        return centroComercialRepository.getCentroComercialByTelefono(telefono);
    }

    /**
     * Obtiene un centro comercial por su horario.
     *
     * @param horario Horario del centro comercial.
     * @return Centro comercial con el horario indicado.
     */
    //Devuelvo una lista por si hay más de un centro comercial con ese horario, para que no de un error 500
    @GetMapping("/centrocomercial/horario/{horario}")
    public List<CentroComercial> getCentroComercialByHorario(@PathVariable String horario) {
        return centroComercialRepository.getCentroComercialByHorario(horario);
    }

    /**
     * Obtiene un centro comercial por su numero de plantas.
     *
     * @param plantas Numero de plantas del centro comercial.
     * @return Centro comercial con la cantidad de plantas indicada.
     */
    @GetMapping("/centrocomercial/plantas/{plantas}")
    public List<CentroComercial> getCentroComercialByPlantas(@PathVariable Integer plantas) {
        return centroComercialRepository.getCentroComercialByPlantas(plantas);
    }

    /**
     * Obtiene una lista de centros comerciales que tienen o no tienen servicio de parking.
     *
     * @param parking Valor booleano que indica si se busca centros con parking (true) o sin él (false).
     * @return Lista de centros comerciales que cumplen con la condición de tener o no tener servicio de parking.
     */
    @GetMapping("/centroscomerciales/parking/{parking}")
    public List<CentroComercial> getCentroComercialByParking(@PathVariable Boolean parking) {
        return centroComercialRepository.getCentroComercialByParking(parking);
    }

    /**
     * Obtiene una lista de centros comerciales inaugurados antes del año especificado.
     *
     * @param ano Año límite de inauguración en formato de cadena.
     * @return Lista de centros comerciales inaugurados antes del año especificado o un ResponseEntity con un mensaje de
     * error si el año no es válido.
     */
    @GetMapping("/centroscomerciales/inauguradosAntesDe/{ano}")
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

    /**
     * Obtiene una lista de centros comerciales inaugurados después del año especificado.
     *
     * @param ano Año límite de inauguración en formato de cadena.
     * @return Lista de centros comerciales inaugurados después del año especificado o un ResponseEntity con un mensaje
     * de error si el año no es válido.
     */
    @GetMapping("/centroscomerciales/inauguradosDespuesDe/{ano}")
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

    /**
     * Obtiene una lista de centros comerciales que tienen menos de un número específico de plantas.
     *
     * @param numeroPlantas Número máximo de plantas permitidas en los centros comerciales.
     * @return Lista de centros comerciales que tienen menos de la cantidad especificada de plantas.
     */
    @GetMapping("/menosDe/{numeroPlantas}/plantas")
    public List<CentroComercial> getCentrosComercialesConMenosDePlantas(@PathVariable Integer numeroPlantas) {
        return centroComercialService.buscarCentrosComercialesConMenosDePlantasQue(numeroPlantas);
    }

    /**
     * Crea un nuevo centro comercial.
     *
     * @param centroComercial Centro comercial a crear.
     * @param token           Token de autenticación.
     * @return ResponseEntity con el centro comercial creado o estado de error.
     */
    //Post de Centro Comercial
    @PostMapping
    public ResponseEntity<CentroComercial> nuevo(@RequestBody CentroComercial centroComercial,
                                                 @RequestParam String token) {
        return centroComercialService.crearCentroComercial(centroComercial, token);
    }

    /**
     * Actualiza un centro comercial existente.
     *
     * @param id               ID del centro comercial a actualizar.
     * @param nuevoCentroComercial Datos actualizados del centro comercial.
     * @param token            Token de autenticación.
     * @return ResponseEntity con el centro comercial actualizado o estado de error.
     */
    //Put de Centro Comercial
    @PutMapping("/{id}")
    public ResponseEntity<CentroComercial> put(@PathVariable Integer id,
                                               @RequestBody CentroComercial nuevoCentroComercial,
                                               @RequestParam String token) {
        return centroComercialService.actualizarCentroComercial(id, nuevoCentroComercial, token);
    }

    /**
     * Elimina un centro comercial por su ID.
     *
     * @param id    ID del centro comercial a eliminar.
     * @param token Token de autenticación.
     * @return ResponseEntity con el estado de la operación.
     */
    //Delete de Centro Comercial
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CentroComercial> delete(@PathVariable Integer id, @RequestParam String token) {
        return centroComercialService.eliminarCentroComercial(id, token);
    }

    //Gets de Tienda
    /**
     * Obtiene una lista de todas las tiendas de un centro comercial específico.
     *
     * @param id ID del centro comercial.
     * @return Lista de tiendas asociadas al centro comercial con el ID especificado.
     */
    @GetMapping("/centrocomercial/{id}/tienda")//Para todas las tiendas de un centro comercial
    public List<Tienda> getTiendasByCentroid(@PathVariable Integer id) {
        return tiendaRepository.getTiendasByCentroid(id);
    }

    /**
     * Obtiene una tienda específica de un centro comercial por su ID de tienda.
     *
     * @param id       ID del centro comercial.
     * @param tiendaid ID de la tienda.
     * @return La tienda con el ID especificado en el centro comercial correspondiente o null si no se encuentra.
     */
    @GetMapping("/centrocomercial/{id}/tienda/{tiendaid}")
    public Tienda getTiendaByTiendaid(@PathVariable Integer id, @PathVariable Integer tiendaid) {
        return tiendaRepository.getTiendaByCentroIdAndTiendaid(id, tiendaid);
    }

    /**
     * Obtiene una tienda específica de un centro comercial por su nombre.
     *
     * @param id     ID del centro comercial.
     * @param nombre Nombre de la tienda.
     * @return La tienda con el nombre especificado en el centro comercial correspondiente o null si no se encuentra.
     */
    @GetMapping("/centrocomercial/{id}/tienda/nombre/{nombre}")
    public Tienda getTiendaByNombre(@PathVariable Integer id, @PathVariable String nombre) {
        return tiendaRepository.getTiendaByCentroIdAndNombre(id, nombre);
    }

    /**
     * Obtiene una tienda específica de un centro comercial por su número de planta.
     *
     * @param id     ID del centro comercial.
     * @param planta Número de planta de la tienda.
     * @return La tienda en la planta especificada en el centro comercial correspondiente o null si no se encuentra.
     */
    @GetMapping("/centrocomercial/{id}/tienda/planta/{planta}")
    public List<Tienda> getTiendaByPlanta(@PathVariable Integer id, @PathVariable Integer planta) {
        return tiendaRepository.getTiendaByCentroIdAndPlanta(id, planta);
    }

    /**
     * Obtiene una lista de tiendas de un centro comercial que tienen un tamaño específico.
     *
     * @param id     ID del centro comercial.
     * @param tamano Tamaño de las tiendas a buscar.
     * @return Lista de tiendas con el tamaño especificado en el centro comercial correspondiente.
     */
    @GetMapping("/centrocomercial/{id}/tienda/tamano/{tamano}")
    public List<Tienda> getTiendasByTamano(@PathVariable Integer id, @PathVariable String tamano) {
        return tiendaRepository.getTiendasByCentroIdAndTamano(id, tamano);
    }

    /**
     * Obtiene una lista de tiendas de un centro comercial que tienen un precio específico.
     *
     * @param id     ID del centro comercial.
     * @param precio Precio de las tiendas a buscar.
     * @return Lista de tiendas con el precio especificado en el centro comercial correspondiente.
     */
    @GetMapping("/centrocomercial/{id}/tienda/precio/{precio}")
    public List<Tienda> getTiendasByPrecio(@PathVariable Integer id, @PathVariable String precio) {
        return tiendaRepository.getTiendasByCentroIdAndPrecio(id, precio);
    }

    /**
     * Obtiene una lista de tiendas por su nombre en todos los centros comerciales.
     *
     * @param nombre Nombre de las tiendas a buscar.
     * @return Lista de tiendas con el nombre especificado en todos los centros comerciales.
     */
    @GetMapping("/tiendas/nombre/{nombre}")
    public List<Tienda> getTiendasByNombre(@PathVariable String nombre) {
        return tiendaRepository.getTiendasByNombre(nombre);
    }

    /**
     * Obtiene una lista de tiendas por su tipo en todos los centros comerciales.
     *
     * @param tipo Tipo de las tiendas a buscar.
     * @return Lista de tiendas con el tipo especificado en todos los centros comerciales.
     */
    @GetMapping("/tiendas/tipo/{tipo}")
    public List<Tienda> getTiendasByTipo(@PathVariable String tipo) {
        return tiendaRepository.getTiendasByTipo(tipo);
    }

    /**
     * Obtiene una lista de tiendas por su precio en todos los centros comerciales.
     *
     * @param precio Precio de las tiendas a buscar.
     * @return Lista de tiendas con el precio especificado en todos los centros comerciales.
     */
    @GetMapping("/tiendas/precio/{precio}")
    public List<Tienda> getTiendasByPrecio(@PathVariable String precio) {
        return tiendaRepository.getTiendasByPrecio(precio);
    }

    /**
     * Crea una nueva tienda y la asocia a un centro comercial específico.
     *
     * @param id     ID del centro comercial al que se asociará la tienda.
     * @param tienda Tienda a crear y asociar al centro comercial.
     * @param token  Token de autenticación.
     * @return ResponseEntity con la tienda creada y asociada al centro comercial o estado de error.
     */
    //Post de Tienda
    @PostMapping("/centrocomercial/{id}")
    public ResponseEntity<Tienda> addTienda(@PathVariable Integer id, @RequestBody Tienda tienda,
                                            @RequestParam String token) {
        return tiendaService.crearTienda(id, tienda, token);
    }

    /**
     * Actualiza los detalles de una tienda específica en un centro comercial.
     *
     * @param id          ID del centro comercial al que pertenece la tienda.
     * @param tiendaid    ID de la tienda a actualizar.
     * @param nuevaTienda Datos actualizados de la tienda.
     * @param token       Token de autenticación.
     * @return ResponseEntity con la tienda actualizada o estado de error.
     */
    //Put de Tienda
    @PutMapping("/centrocomercial/{id}/tienda/{tiendaid}")
    public ResponseEntity<Tienda> updateTienda(@PathVariable Integer id, @PathVariable Integer tiendaid,
                                               @RequestBody Tienda nuevaTienda, @RequestParam String token) {
        return tiendaService.actualizarTienda(id, tiendaid, nuevaTienda, token);
    }

    /**
     * Elimina una tienda específica de un centro comercial.
     *
     * @param id       ID del centro comercial al que pertenece la tienda.
     * @param tiendaid ID de la tienda a eliminar.
     * @param token    Token de autenticación.
     * @return ResponseEntity con el estado de la operación.
     */
    //Delete de Tienda
    @DeleteMapping("/{id}/tienda/{tiendaid}")
    public ResponseEntity<Void> deleteTienda(@PathVariable Integer id, @PathVariable Integer tiendaid,
                                             @RequestParam String token) {
        return tiendaService.eliminarTienda(id, tiendaid, token);
    }

    /**
     * Elimina todas las tiendas asociadas a un centro comercial.
     *
     * @param centroid ID del centro comercial del cual se eliminarán las tiendas.
     * @param token             Token de autenticación.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/eliminarTodasLasTiendasDelCentroComercial/{centroid}")
    public ResponseEntity<Void> eliminarTiendasPorCentroComercial(
            @PathVariable Integer centroid,
            @RequestParam String token) {
        if (!securityService.tokenDeValidacion(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        tiendaService.eliminarTiendasDeCentroComercial(centroid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}