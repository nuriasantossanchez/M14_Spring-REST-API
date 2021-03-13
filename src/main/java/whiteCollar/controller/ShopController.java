package whiteCollar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whiteCollar.controller.exception.ShopNotFoundException;
import whiteCollar.dto.PictureDto;
import whiteCollar.dto.ShopDto;
import whiteCollar.entity.Picture;
import whiteCollar.entity.Shop;
import whiteCollar.service.IPictureService;
import whiteCollar.service.IShopService;
import whiteCollar.util.PictureModelAssembler;
import whiteCollar.util.ShopModelAssembler;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * Clase de la capa Controller.
 * La anotacion @RestController convierte a la aplicacion en un REST Service, basado en el intercambio
 * de recursos (elementos de informacion) entre componentes de la red, clientes y servidores, que
 * se comunican a traves del protocolo HTTP
 *
 * Anotaciones:
 *
 * @RestController
 * Anotacion que a su vez esta anotada con @Controller y @ResponseBody.
 * Los tipos que llevan esta anotacion se tratan como controladores donde los metodos @RequestMapping
 * asumen la semantica @ResponseBody por defecto
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 * @GetMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.GET).
 * El punto de acceso a la peticion sera http://localhost:8081/{path}, en este caso
 * (el puerto 8181 queda especificado en el archivo application.properties, del directorio resources)
 *
 * @PostMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.POST).
 *
 * @PutMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.PUT).
 *
 * @DeleteMapping
 * Anotacion compuesta que actua como un atajo para @RequestMapping(method = RequestMethod.DELETE).
 */
@RestController
public class ShopController {

    private final IShopService iShopService;
    private final IPictureService iPictureService;
    private final ShopModelAssembler shopModelAssembler;
    private final PictureModelAssembler pictureModelAssembler;

    /**
     * Constructor de la clase, parametrizado con las interfaces IEmployeeService, IRoleService y la clase
     * RoleModelAssembler, que implementa la interface RepresentationModelAssembler
     * Marcado con la anotacion @Autowired, la clase controlador es automaticamente detectada por Spring
     *  @param iShopService, interfaz de tipo IEmployeeService, implementada por la clase EmployeeServiceImpl,
     *                          en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param iPictureService, interfaz de tipo IRoleService, implementada por la clase RoleServiceImpl,
     *                      en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param pictureModelAssembler, instancia de tipo EmployeeModelAssembler, convierte un objeto de dominio en un
 *                            RepresentationModel, esto es, un EntityModel que envuelve al objeto de dominio
     * @param shopModelAssembler
     */
    @Autowired
    public ShopController(IShopService iShopService, IPictureService iPictureService,
                          ShopModelAssembler shopModelAssembler, PictureModelAssembler pictureModelAssembler) {
        this.iShopService = iShopService;
        this.iPictureService = iPictureService;
        this.shopModelAssembler = shopModelAssembler;
        this.pictureModelAssembler = pictureModelAssembler;
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL http://localhost:8081/shops
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del metodo 'listShops()' para recuperar un listado de tipos ShopDto
     * en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos ShopDto,
     * que contiene todos los empleados disponibles en el sistema, junto con enlaces agregados
     */
    @GetMapping("/shops")
    public ResponseEntity<?> allShops(){

        List<EntityModel<ShopDto>> shops = iShopService.listShops().stream()
                .map(shopModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ShopDto>> collectionModel =
                CollectionModel.of(shops,
                        linkTo(methodOn(ShopController.class).allShops()).withSelfRel());

        return ResponseEntity
                .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(collectionModel);
    }

    /**
     * Representa el mapeo de una peticion HTTP POST, a la URL http://localhost:8081/shops
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del metodo 'saveShop(newShop)' para salvar el nuevo objeto creado,
     * de tipo ShopDto, en forma de ResponseEntity, esto es, agregando enlaces al objeto
     * de dominio
     *
     * @param newShop, tipo Shop anotado con @RequestBody para indicar que el parametro de metodo
     *                     debe estar vinculada al cuerpo de la solicitud web.
     *                     El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                     Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo EmployeeDto,
     * que contiene el nuevo empleado creado, junto con enlaces agregados
     */
    @PostMapping("/shops")
    public ResponseEntity<?> newShop(@Valid @RequestBody Shop newShop) {

        EntityModel<ShopDto> entityModel = shopModelAssembler.toModel(iShopService.saveShop(newShop));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Representa el mapeo de una peticion HTTP GET, a la URL
     * http://localhost:8081/shops/{id}/pictures
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del metodo 'findShopById(shopId)' para recuperar un objeto de tipo Shop.
     *
     * Despues accede a la capa de servicio PictureServiceImpl mediante su interface
     * IPictureService y hace uso del metodo 'listPicturesByShop(shopId)' para recuperar
     * un listado de tipos PictureDto asociado al {id} pasado en el PathVariable y que se
     * corresponde con un objeto Shop concreto.
     *
     * En caso de que no existiese ningun objeto Shop con el id especificado,
     * lanza una exception
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos ShopDto,
     * que contiene todos los empleados disponibles en el sistema, junto con enlaces agregados
     */
    @GetMapping("/shops/{id}/pictures")
    public ResponseEntity<?> allPicturesByShop(@PathVariable(name="id") Long shopId){

        Shop shop = iShopService.findShopById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        List<Picture> picturesByShop = iPictureService.listPicturesByShop(shop);

        if (!picturesByShop.isEmpty()){
            List<EntityModel<PictureDto>> picturesDto = picturesByShop.stream()
                    .map(pictureModelAssembler::toModel)
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<PictureDto>> collectionModel =
                    CollectionModel.of(picturesDto,
                            linkTo(methodOn(ShopController.class).allPicturesByShop(shopId)).withSelfRel());

            return ResponseEntity
                    .created(collectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(collectionModel);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Representa el mapeo de una peticion HTTP POST, a la URL http://localhost:8081/shops
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del metodo 'saveShop(newShop)' para salvar el nuevo objeto creado,
     * de tipo ShopDto, en forma de ResponseEntity, esto es, agregando enlaces al objeto de dominio
     *
     * @param newPicture, tipo Shop anotado con @RequestBody para indicar que el parametro de metodo
     *                     debe estar vinculada al cuerpo de la solicitud web.
     *                     El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                     Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo EmployeeDto,
     * que contiene el nuevo empleado creado, junto con enlaces agregados
     */
    @PostMapping("/shops/{id}/pictures")
    public ResponseEntity<?> newPicture(@Valid @RequestBody Picture newPicture, @PathVariable(name="id") Long shopId) {

        Shop shop = iShopService.findShopById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        //Long currentCapacity = shop.getPictures().stream().count();
        Long currentCapacity = iShopService.currentShopCapacity(shopId);

        if(shop.getCapacity() > currentCapacity){
            Long maxValue = shop.getPictures().stream().map(p -> p.getId()).max(Comparator.naturalOrder()).orElseGet(()-> 0L);
            newPicture.setId(maxValue+1);
            newPicture.setShop(shop);
            newPicture.setIdShop(shopId);
            EntityModel<PictureDto> entityModel = pictureModelAssembler.toModel(iPictureService.savePicture(newPicture));

            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Problem.create()
                        .withTitle("Please select another shop.")
                        .withDetail("The store does not have enough capacity."));

    }

    /**
     * Representa el mapeo de una peticion HTTP DELETE, a la URL http://localhost:8081/employees/{valor numerico}
     *
     * Accede a la capa de servicio EmployeeServiceImpl mediante su interface IEmployeeService
     * y hace uso de los metodos 'findEmployeeById(id)' para recuperar el objeto de tipo EmployeeDto,
     * y deleteEmployee(id), para eliminar el empleado determinado por el valor numerico pasado en la URL
     *
     * @param shopId, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id del empleado que se quiere eliminar
     *
     * @return objeto generico de tipo ResponseEntity, con una respuesta de operacion valida
     */
    @DeleteMapping("/shops/{id}/pictures")
    public ResponseEntity<?> deletePicturesByShop(@PathVariable(name="id") Long shopId) {
        Shop shop = iShopService.findShopById(shopId)
                .orElseThrow(() -> new ShopNotFoundException(shopId));

        iPictureService.firePictures(shop.getPictures());

        return ResponseEntity.noContent().build();
    }

}
