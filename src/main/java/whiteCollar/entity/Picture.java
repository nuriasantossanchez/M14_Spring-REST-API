package whiteCollar.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class Picture implements Serializable {

    //Atributos de entidad Picture
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//busca ultimo valor e incrementa desde id final de db
    private Long id;

    @Column(name = "name")//no hace falta si se llama igual
    @NotEmpty(message = "\"Picture Name\" is required")
    private String name;

    @Column(name = "author")//no hace falta si se llama igual
    @NotEmpty(message = "\"Picture Author\" is required")
    private String author;

    @Column(name = "price")//no hace falta si se llama igual
    @NotEmpty(message = "\"Picture Price\" is required")
    private BigDecimal price;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "entryDate")//no hace falta si se llama igual
    //@NotEmpty(message = "\"Picture Entry Date\" is required")
    private Date entryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    //@NotNull(message = "\"picture\" with a {\"id\"} element is required")
    private Shop shop;

    public Picture() {
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

    @Override
    public String toString() {
        return "\nPicture {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", entryDate=" + entryDate +
                ", shop=" + shop +
                '}';
    }
}
