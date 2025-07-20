package vn.kieudon.workwave.service.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.kieudon.workwave.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
    //response exception
    // @ExceptionHandler(value = IdInvalidException.class)
    // public ResponseEntity<String> handleIdException(IdInvalidException idException) {
    //     return ResponseEntity.badRequest().body(idException.getMessage());

    // }

    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity< RestResponse <Object> > handleIdException(IdInvalidException idException) {
        RestResponse <Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(idException.getMessage());
        res.setMessage("IdInvalidException");
        return ResponseEntity.badRequest().body(res);

    }
}
