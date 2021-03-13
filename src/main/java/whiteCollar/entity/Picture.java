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
 * a una base de datos embebida de tipo H2.
 *
 * La anotacion @ManyToOne especifica una asociación de un solo valor a otra clase de entidad
 * que tiene multiplicidad de muchos a uno, en este caso, la asociacion es con la entidad Shop,
 * donde un Picture tiene un unico Shop y un Shop puede estar asociado a multiples Employees
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
    @NotEmpty(message = "author is required")
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
    void preInsert() {
        if (this.entryDate == null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            this.entryDate = date;
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
