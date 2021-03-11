package whiteCollar.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase de la capa Controller, dentro del paquete Exception
 *
 * La anotacion @ControllerAdvice es una especializacion de @Component para clases que declaran
 * los métodos @ExceptionHandler, @InitBinder o @ModelAttribute para compartir entre varias clases
 * de @Controller.
 *
 * Las clases anotadas con @ControllerAdvice pueden declararse explicitamente como Spring beans o
 * detectarse automaticamente a traves del escaneo de classpath.
 *
 * De forma predeterminada, los metodos en un @ControllerAdvice se aplican globalmente a todos
 * los controladores
 */
@ControllerAdvice
class ShopNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ShopNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(ShopNotFoundException ex) {
        return ex.getMessage();
    }
}
