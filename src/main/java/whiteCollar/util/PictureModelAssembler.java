package whiteCollar.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import whiteCollar.controller.ShopController;
import whiteCollar.dto.PictureDto;
import whiteCollar.entity.Picture;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
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
 * Utiliza el objeto ModelMapper para realizar el mapeo de objetos de tipo Picture
 * a objetos de tipo PictureDto.
 * Marcada con la anotacion @Autowired, la clase ModelMapper es automaticamente detectada por Spring
 *
 * Anotaciones:
 *
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la detección automatica cuando se utiliza una configuracion
 * basada en anotaciones y un escaneo de classpath.
 * Tambien se pueden considerar otras anotaciones a nivel de clase como identificacion de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotacion @Repository
 *
 */
@Component
public class PictureModelAssembler implements RepresentationModelAssembler<Picture, EntityModel<PictureDto>> {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Metodo abstracto de la interfaz RepresentationModelAssembler
     * Convierte un objeto de tipo Picture en un EntityModel de tipo PictureDto.
     * El objeto EntityModel envuelve un objeto de dominio y le agrega enlaces
     *
     * @param picture, objeto de tipo Picture
     * @return objeto de tipo EntityModel que envuelve a un objeto de tipo PictureDto
     * y le agrega enlaces
     */
    @Override
    public EntityModel<PictureDto> toModel(Picture picture) {
        PictureDto pictureDto = convertToDto(picture);

        return EntityModel.of(pictureDto,
                linkTo(methodOn(ShopController.class).newPicture(picture, picture.getShop().getId())).withSelfRel(),
                linkTo(methodOn(ShopController.class).deletePicturesByShop(picture.getShop().getId())).withRel("delete"),
                linkTo(methodOn(ShopController.class).allPicturesByShop(picture.getShop().getId())).withRel("all"));
    }

    /**
     * Realizar el mapeo de objetos de tipo Picture a objetos de tipo PictureDto, haciendo uso de un objeto
     * de tipo ModelMapper qu es injectado en la clase mediante la anotacion @Autowired
     *
     * @param picture, objeto de tipo Picture
     * @return objeto de tipo PictureDto
     */
    public PictureDto convertToDto(Picture picture){
        PictureDto pictureDto = modelMapper.map(picture, PictureDto.class);
        pictureDto.setIdPicture(picture.getId());
        pictureDto.setShopCapacity(picture.getShop().getCapacity());
        return pictureDto;
    }

}
