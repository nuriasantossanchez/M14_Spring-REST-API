package whiteCollar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whiteCollar.entity.Shop;
import whiteCollar.repository.IShopRepository;

import java.util.List;
import java.util.Optional;

/**
 * Clase de la capa Service, implementa la interface IShopService
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
public class ShopServiceImpl implements IShopService {

    @Autowired
    IShopRepository iShopRepository;

    @Override
    public List<Shop> listShops() {
        return iShopRepository.findAll();
    }

    @Override
    public Shop saveShop(Shop shop) {
        return iShopRepository.save(shop);
    }

    @Override
    public Optional<Shop> findShopById(Long id) {
        return iShopRepository.findById(id);
    }

    @Override
    public Long currentShopCapacity(Long idShop) {
        return iShopRepository.findById(idShop).get().getPictures().stream().count();
    }
}
