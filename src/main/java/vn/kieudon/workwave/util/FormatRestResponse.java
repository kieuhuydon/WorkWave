package vn.kieudon.workwave.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;
import vn.kieudon.workwave.domain.RestResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice{

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
       return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, // phản hồi chưa format
    MethodParameter returnType, 
    MediaType selectedContentType,
    Class selectedConverterType, 
    ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        RestResponse <Object> res = new RestResponse<Object>();
        res.setStatusCode(status);

        if (status >=400){
            //case error
            return body;

        }else{
            //case success
            res.setData(body);
            res.setMessage("call api success");
        }
       
        return  res;
    
    }
}
