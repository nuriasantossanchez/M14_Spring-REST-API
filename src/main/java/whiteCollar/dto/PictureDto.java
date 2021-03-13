package whiteCollar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de la capa de dominio, implementa el patron Data Transfer Object (DTO Pattern) mediante la
 * creacion de un objeto plano (POJO) con una serie de atributos que puedan ser enviados o recuperados
 * del servidor en una sola invocacion (de tal forma que un DTO puede contener informacion de multiples
 * fuentes o tablas y concentrarlas en una unica clase simple, esto es, crear estructuras de datos
 * independientes del modelo de datos, para transmitir informacion entre un cliente y un servidor)
 *
 * Anotaciones:
 * @Component
 * Indica que una clase es un "componente".
 * Estas clases se consideran candidatas para la deteccion automatica cuando se utiliza una configuracion
 * basada en anotaciones y un escaneo de classpath.
 * Tambien se pueden considerar otras anotaciones a nivel de clase como identificacion de un componente,
 * normalmente un tipo especial de componente: por ejemplo, la anotacion @Repository
 */
public class PictureDto extends ResponseDto{

    private Long idPicture;
    private Long idShop;
    private Long shopCapacity;
    private String name;
    private String author;
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private Date entryDate;

    public PictureDto() {
    }

    public Long getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(Long idPicture) {
        this.idPicture = idPicture;
    }

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
        this.idShop = idShop;
    }

    public Long getShopCapacity() {
        return shopCapacity;
    }

    public void setShopCapacity(Long shopCapacity) {
        this.shopCapacity = shopCapacity;
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

    @Override
    public String toString() {
        return "PictureDto {" +
                "idPicture=" + idPicture +
                ", idShop=" + idShop +
                ", shopCapacity=" + shopCapacity +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", entryDate=" + entryDate +
                '}';
    }
}
