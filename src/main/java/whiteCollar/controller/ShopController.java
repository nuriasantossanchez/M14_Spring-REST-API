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
 * (el puerto 8081 queda especificado en el archivo application.properties, del directorio resources)
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
     * Constructor de la clase, parametrizado con las interfaces IShopService, IPictureService y las clases
     * ShopModelAssembler y PictureModelAssembler, que implementan la interface RepresentationModelAssembler
     * Marcado con la anotacion @Autowired, la clase controlador es automaticamente detectada por Spring
     *  @param iShopService, interfaz de tipo IShopService, implementada por la clase ShopServiceImpl,
     *                      en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param iPictureService, interfaz de tipo IPictureService, implementada por la clase PictureServiceImpl,
     *                      en la que se exponen los servicios o funcionalidades accesibles via HTTP
     * @param shopModelAssembler, instancia de tipo ShopModelAssembler, convierte un objeto de dominio en
     *                          un RepresentationModel, esto es, un EntityModel que envuelve al objeto de dominio
     *                          y lo agrega enlaces
     * @param pictureModelAssembler, instancia de tipo PictureModelAssembler, convierte un objeto de dominio en
     *                            un RepresentationModel, esto es, un EntityModel que envuelve al objeto de dominio
     *                            y lo agrega enlaces
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
     * Representa el mapeo de una peticion HTTP GET, a la URL
     * http://localhost:8081/shops
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del servicio 'listShops()' para recuperar un listado de tipos Shop
     * en forma de ResponseEntity, esto es, modelando el objeto de dominio a objeto DTO
     * y agregando enlaces al objeto de dominio
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos ShopDto,
     * que contiene todos las tiendas que hay en el sistema, junto con enlaces agregados
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
     * Representa el mapeo de una peticion HTTP POST, a la URL
     * http://localhost:8081/shops
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del servicio 'saveShop(newShop)' para salvar el nuevo objeto creado,
     * de tipo Shop, y lo retorna en forma de ResponseEntity, esto es, modelando el objeto
     * de dominio a objeto DTO y agregando enlaces
     *
     * @param newShop, tipo Shop anotado con @RequestBody para indicar que el parametro de metodo
     *                 debe estar vinculada al cuerpo de la solicitud web.
     *                 El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                 Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo ShopDto,
     * que contiene la nueva tienda creada, junto con enlaces agregados
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
     * y hace uso del servicio 'findShopById(shopId)' para recuperar un objeto de tipo Shop
     * cuyo id coincida con el shopId pasado en el PathVariable
     *
     * En caso de que no existiese ningun objeto Shop con el shopId especificado,
     * lanza una exception
     *
     * Despues accede a la capa de servicio PictureServiceImpl mediante su interface
     * IPictureService y hace uso del servicio 'listPicturesByShop(shop)' para recuperar
     * un listado de tipos Picture asociados al objeto de tipo Shop pasado como parametro
     * y cuyo id coincide con el shopId pasado en el PathVariable
     *
     * @param shopId, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *                y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *                Indica el id de la tienda de la que se quiere recuperar el listado de cuadros asociados
     *
     * @return objeto generico de tipo ResponseEntity, formado por un listado de tipos PictureDto,
     * que contiene todos los cuadros disponibles en una tienda determinada, junto con enlaces agregados
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
     * Representa el mapeo de una peticion HTTP POST, a la URL
     * http://localhost:8081/shops/{id}/pictures
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del servicio 'findShopById(shopId)' para recuperar un objeto de tipo Shop
     * cuyo id coincida con el shopId pasado en el PathVariable
     *
     * En caso de que no existiese ningun objeto Shop con el shopId especificado en el PathVariable,
     * lanza una exception
     *
     * Despues accede a la capa de servicio PictureServiceImpl mediante su interface
     * IPictureService y hace uso del servicio 'savePicture(newPicture)' para salvar el nuevo objeto creado
     *
     * Antes de salvar el nuevo objeto Picture, comprueba la capacidad de la tienda,
     * para ello accede al servicio currentShopCapacity(shopId) de la interface IShopService.
     * En caso de que la tienda no tenga capacidad suficiente, informa con el mensaje correspondiente
     * y no hace el salvado del objeto Picture
     *
     * @param newPicture, tipo Picture anotado con @RequestBody para indicar que el parametro de metodo
     *                    debe estar vinculada al cuerpo de la solicitud web.
     *                    El cuerpo de la solicitud se pasa en formato JSON, segun el tipo de contenido de la solicitud.
     *                    Se aplica la validacion automatica anotando el argumento con @Valid
     *
     * @param shopId, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *                y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *                Indica el id de la tienda en la que se quiere salvar un nuevo objeto de tipo Picture
     *
     * @return objeto generico de tipo ResponseEntity, formado por un objeto de tipo PictureDto,
     * que contiene el nuevo cuadro creado, junto con enlaces agregados
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
     * Representa el mapeo de una peticion HTTP DELETE, a la URL
     * http://shops:8081/shops/{id}/pictures}
     *
     * Accede a la capa de servicio ShopServiceImpl mediante su interface IShopService
     * y hace uso del servicio 'findShopById(shopId)' para recuperar un objeto de tipo Shop
     * cuyo id coincida con el shopId pasado en el PathVariable
     *
     * En caso de que no existiese ningun objeto Shop con el shopId especificado en el PathVariable,
     * lanza una exception
     *
     * Despues accede a la capa de servicio PictureServiceImpl mediante su interface IPictureService
     * y hace uso del servicio 'firePictures(shop.getPictures())' para eliminar en lote
     * (deleteInBatch, en una sola query) el listado de cuadros asociados a una determinada tienda,
     * El servicio es parametrizado con el listado de cuadros a eliminar
     *
     * @param shopId, tipo Long anotado con @PathVariable para indicar que es un parametro de metodo
     *            y debe estar vinculado a una variable de tipo plantilla de URI (URI template)
     *            Indica el id de la tienda concreta en la que se eliminaran todos sus cuadros
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
