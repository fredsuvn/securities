package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.*;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.exception.BusinessException;
import com.tousie.securities.exception.ValidationException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author sunqian
 */
@Component
public class RequestParamValidationInterceptor implements ServiceCallInterceptor {

    private BeanOperator beanOperator = DefaultBeanOperator.INSTANCE;

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 200;
    }

    @Override
    public void doIntercept(Request request, Object[] args, @Nullable Object result, Map<Object, Object> requestLocal) {
        for (Object arg : args) {
            if (arg != null
                    && !arg.getClass().equals(Request.class)
                    && !arg.getClass().equals(Session.class)
                    && !arg.getClass().equals(Channel.class)
                    && !arg.getClass().equals(Client.class)
                    && !beanOperator.isBasicType(arg.getClass())) {
                doValidate(arg);
            }
        }
    }

    private void doValidate(Object arg) {
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (NotEmpty.class.isAssignableFrom(annotation.getClass())) {
                    if (String.class.equals(field.getType())) {
                        String value = beanOperator.getProperty(arg, field.getName(), String.class);
                        if (value == null || value.isEmpty()) {
                            throw new ValidationException(((NotEmpty) annotation).message());
                        }
                    }
                }
                if (Pattern.class.isAssignableFrom(annotation.getClass())) {
                    if (String.class.equals(field.getType())) {
                        String regexp = ((Pattern) annotation).regexp();
                        String value = beanOperator.getProperty(arg, field.getName(), String.class);
                        if (value != null && !value.matches(regexp)) {
                            throw new ValidationException(((Pattern) annotation).message());
                        }
                    }
                }
            }
        }
    }
}
