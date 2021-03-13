package whiteCollar.entity;

import javax.persistence.Column;
import java.io.Serializable;

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
