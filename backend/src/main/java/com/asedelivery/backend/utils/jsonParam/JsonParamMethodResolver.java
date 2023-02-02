package com.asedelivery.backend.utils.jsonParam;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JsonParamMethodResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }

        JsonParam parameterAnnotation = parameter.getParameterAnnotation(JsonParam.class);
        String value = parameterAnnotation.value();
        Class<?> parameterType = parameter.getParameterType();

        String contentType = request.getHeader("Content-Type");
        if(contentType == "application/json"){
            Gson gson = new Gson();
            Map<String, Object> response = gson.fromJson(request.getReader(), new TypeToken<Map<String, Object>>(){}.getType());

            if (response == null) {
                return null;
            }

            return response.get(value);
        } else {
            return null;
        }
    }
}
