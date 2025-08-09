package vn.kieudon.workwave.util.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.kieudon.workwave.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
    // response exception
    // @ExceptionHandler(value = IdInvalidException.class)
    // public ResponseEntity<String> handleIdException(IdInvalidException
    // idException) {
    // return ResponseEntity.badRequest().body(idException.getMessage());

    // }


    @ExceptionHandler(value = {
        UsernameNotFoundException.class,
        BadCredentialsException.class
    })
    public ResponseEntity<RestResponse<Object>> handleIdException(Exception ex) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("Exception occurs...");
        return ResponseEntity.badRequest().body(res);

    }
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdException(IdInvalidException idException) {
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(idException.getMessage());
        res.setMessage("IdInvalidException");
        return ResponseEntity.badRequest().body(res);

    }

    // khi có lỗi từ valid LoginDTO thì gọi vào đây để format response exception
    //MethodArgumentNotValidException exception valid nhưng annotation 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult(); //BindingResult chứa toàn bộ thông tin về lỗi validation 
        //Một số phương thức hữu ích của bindingResult getFieldErrors()	Danh sách lỗi ở các field
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getBody().getDetail());
        // duyệt từng lỗi và lấy messeage
        List<String> errors = new ArrayList<>();
        for(FieldError fieldError : fieldErrors){
            String error = fieldError.getDefaultMessage();
            errors.add(error);
        }
        res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

}

        