package whiteCollar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whiteCollar.entity.Picture;
import whiteCollar.entity.Shop;
import whiteCollar.repository.IPictureRepository;

import java.util.List;

/**
 * Clase de la capa Service, implementa la interface IPictureService
 *
 * Anotaciones:
 * @Service
 * Indica que la clase es un "Servicio", esto es, una operacion ofrecida como una interface que esta solo en el modelo,
 * sin un estado encapsulado.
 *
 * Sirve como una especializacion de @Component, lo que permite que las clases de implementacion se detecten
 * automaticamente a traves del escaneo del classpath
 *
 * @Autowired
 * Marca un constructor, campo, metodo setter o metodo de configuracion para ser detectado
 * automaticamente por la funcionalidad de inyeccion de dependencias de Spring
 *
 */
@Service
public class PictureServiceImpl implements IPictureService {

    @Autowired
    IPictureRepository iPictureRepository;


    @Override
    public List<Picture> listPicturesByShop(Shop shop) {
        return iPictureRepository.findPicturesByShop(shop);
    }

    @Override
    public Picture savePicture(Picture picture) {
        return iPictureRepository.save(picture);
    }

    @Override
    public void firePictures(List<Picture> pictures) {
        iPictureRepository.deleteInBatch(pictures);
    }
}
