package whiteCollar.service;

import whiteCollar.entity.Shop;

import java.util.List;
import java.util.Optional;

/**
 * Interface de la capa Service
 *
 */
public interface IShopService {

    List<Shop> listShops(); //List All Shops

    Shop saveShop(Shop shop); //Save a Shop CREATE

    Optional<Shop> findShopById(Long id); //Find a Shop by Id READ

    Long currentShopCapacity(Long idShop); //Get the current capacity from a Shop

}
