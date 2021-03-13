package whiteCollar.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Clase de la capa de dominio.
 *
 * La anotacion @Entity indica que la clase es una entidad.
 *
 * La anotacion @Table indica que la clase sera mapeada a una tabla y persistida, en este caso,
 * tanto en una base de datos embebida de tipo H2, como en una base de datos MySQL.
 * (ver application.properties, donde estan definidas ambas conexiones)
 *
 * La anotacion @IdClass especifica que la clase tiene clave primaria compuesta, es decir, se asigna a varios campos
 * o propiedades de la entidad.
 * Los nombres de los campos o propiedades en la clase de clave primaria, en este caso, PictureShopPk.class,
 * y los campos o propiedades de la clave primaria de la entidad se deben corresponder y sus tipos deben ser los mismos.
 *
 * La anotacion @ManyToOne especifica una asociacion de un solo valor a otra clase de entidad
 * que tiene multiplicidad de muchos a uno, en este caso, la asociacion es con la entidad Shop,
 * donde varios valores de tipo Picture pueden estar asociados a una unica entidad de tipo Shop.
 *
 * La anotacion @JoinColumn indica que la propiedad shop es el campo para crear la relacion de llave foranea
 * y va a tomar la columna id_shop de la tabla PICTURE para crear el join con la tabla padre SHOP
 *
 * La anotacion @PrePersist especifica un metodo de devolucion de llamada para el evento de ciclo de vida
 * correspondiente, en este caso, define ciertos valores por defecto previos a la insercion en base de datos.
 *
 * La anotacion @Temporal debe especificarse para campos persistentes o propiedades de tipo java.util.Date
 * y java.util.Calendar. Solo se puede especificar para campos o propiedades de este tipo.
 */
@Entity
@Table(name="picture") //en caso que la tabla sea diferente
@IdClass(PictureShopPk.class)
public class Picture implements Serializable {

    //Atributos de entidad Picture
    @Id
    @Column(name = "id_shop")
    private Long idShop;

    @Id
    @Column(name = "id_picture")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")//no hace falta si se llama igual
    @NotEmpty(message = "name is required")
    private String name;

    @Column(name = "author")//no hace falta si se llama igual
    @NotNull(message = "author is required")
    private String author;

    @Column(name = "price")//no hace falta si se llama igual
    @NotNull(message = "price is required")
    private BigDecimal price;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "entry_date")
    private Date entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_shop", insertable = false, updatable = false)
    private Shop shop;

    public Picture() {
    }

    @PrePersist
    public void preInsert() {
        if (this.entryDate == null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            this.entryDate = date;
        }
        if (this.author.equals("")) {
            this.author = "ANONYMOUS";
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }



}
