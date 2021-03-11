package whiteCollar.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase de la capa de dominio.
 *
 * La anotacion @Entity indica que la clase es una entidad.
 *
 * La anotacion @Table indica que la clase sera mapeada a una tabla y persistida, en este caso,
 * a una base de datos embebida de tipo H2.
 */
@Entity
@Table(name="shop") //en caso que la tabla sea diferente
public class Shop implements Serializable {

    //Atributos de entidad Shop
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //busca ultimo valor e incrementa desde id final de db
    private Long id;

    @Column(name = "name")//no hace falta si se llama igual
    @NotEmpty(message = "\"Shop Name\" is required")
    private String name;


    @Column(name = "capacity")
    @NotNull(message = "\"Shop Capacity\" is required")
    private Long capacity;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Picture> pictures;

    public Shop() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }


}
