package whiteCollar.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import whiteCollar.controller.ShopController;
import whiteCollar.dto.ShopDto;
import whiteCollar.entity.Shop;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Clase de la capa Controller
 *
 * Implemente la interfaz RepresentationModelAssembler
 * (pertenece al modulo de Spring HATEOAS, org.springframework.hateoas.server)
 *
 * Convierte un objeto de dominio en un RepresentationModel, que es una clase base
 * para que los DTO recopilen enlaces, un EntityModel simple que envuelve un objeto
 * de dominio y le agrega enlaces.
 *
 * Utiliza el objeto ModelMapper para realizar el mapeo de objetos de tipo Shop
 * a objetos de tipo ShopDto.
 * Marcada con la anotacion @Autowired, la clase ModelMapper es automaticamente detectada por Spring
 *
 * Anotaciones:
 *
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la detección automática cuando se utiliza una configuración
 * basada en anotaciones y un escaneo de classpath.
 * También se pueden considerar otras anotaciones a nivel de clase como identificación de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotación @Repository
 *
 */
@Component
public class ShopModelAssembler implements RepresentationModelAssembler<Shop, EntityModel<ShopDto>> {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Metodo abstracto de la interfaz RepresentationModelAssembler
     * Convierte un objeto de tipo Shop en un EntityModel de tipos ShopDto.
     * El objeto EntityModel envuelve un objeto de dominio y le agrega enlaces
     *
     * @param shop, objeto de tipo Shop
     * @return objeto de tipo EntityModel que envuelve a un objeto de tipo ShopDto
     * y le agrega enlaces
     */
    public EntityModel<ShopDto> toModel(Shop shop) {

        ShopDto shopDto = convertToDto(shop);

        return EntityModel.of(shopDto,
                WebMvcLinkBuilder.
                        linkTo(methodOn(ShopController.class).allShops()).withRel("all"));
    }

    public ShopDto convertToDto(Shop shop) {
        ShopDto shopDto = modelMapper.map(shop, ShopDto.class);
        return shopDto;
    }

}
