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
 * tanto en una base de datos embebida de tipo H2, como en una base de datos MySQL.
 * (ver application.properties, donde estan definidas ambas conexiones)
 *
 * La anotacion @OneToMany especifica una asociacion de varios valores con multiplicidad de uno a varios,
 * en este caso, la asociacion es con la entidad Picture, donde multiples una unica entidad de tipo Shop
 * puede estar asociada a multiples valores de tipo Picture.
 *
 * Al ser la relacion bidireccional, el elemento mappedBy debe usarse para especificar
 * el campo de relacion o la propiedad de la entidad que es propietaria de la relacion.
 */
@Entity
@Table(name="shop") //en caso que la tabla sea diferente
public class Shop implements Serializable {

    //Atributos de entidad Shop
    @Id
    @Column(name = "id_shop")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //busca ultimo valor e incrementa desde id final de db
    private Long id;

    @Column(name = "name")//no hace falta si se llama igual
    @NotEmpty(message = "name is required")
    private String name;


    @Column(name = "capacity")
    @NotNull(message = "capacity is required")
    private Long capacity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop", fetch = FetchType.LAZY)
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
