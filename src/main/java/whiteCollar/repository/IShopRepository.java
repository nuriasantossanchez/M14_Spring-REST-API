package whiteCollar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import whiteCollar.entity.Shop;


/**
 * Interface de la capa Repository, extiende CrudRepository
 *
 */
@Repository
@Transactional
public interface IShopRepository extends JpaRepository<Shop, Long> {

}
