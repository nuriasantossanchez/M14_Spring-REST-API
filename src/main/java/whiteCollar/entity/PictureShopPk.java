package whiteCollar.entity;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Clase de la capa de dominio.
 *
 * Clase de clave primaria, utilizada para representar una clave primaria compuesta.
 *
 * Sus campos o propiedades deben ser declarados exactamente igual que los campos o propiedades
 * de la entidad que contiene la clave primaria compuesta, en este caso, la entidad Picture, anotada
 * con @IdClass(PictureShopPk.class)
 */
public class PictureShopPk implements Serializable {

    @Column(name = "id_shop")
    private Long idShop;

    @Column(name = "id_picture")
    private Long id;

    public PictureShopPk() {
    }

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
        this.idShop = idShop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
