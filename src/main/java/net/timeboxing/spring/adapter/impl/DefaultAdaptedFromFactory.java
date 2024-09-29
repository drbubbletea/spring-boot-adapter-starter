package net.timeboxing.spring.adapter.impl;

import jakarta.inject.Inject;
import net.timeboxing.spring.adapter.AdaptedFromFactory;
import net.timeboxing.spring.adapter.AdapterException;
import net.timeboxing.spring.adapter.Purpose;
import net.timeboxing.spring.adapter.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class DefaultAdaptedFromFactory implements AdaptedFromFactory {

    private final ApplicationContext context;

    private final Class<?> adaptedFromClass;
    private final Class<?> fromClass;
    private final Class<?> toClass;
    private final Class<? extends Enum<?>> purposeEnum;
    private final Object purpose;
    private final Constructor<?> constructor;
    private final Class<?>[] types;
    private final Annotation[][] parameterAnnotations;


    public DefaultAdaptedFromFactory(ApplicationContext context, Class<?> adaptedFromClass, Class<?> fromClass, Class<?> toClass, Class<? extends Enum<?>> purposeEnum, Object purpose) {
        this.context = context;
        this.adaptedFromClass = adaptedFromClass;
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.purposeEnum = purposeEnum;
        this.purpose = purpose;
        this.constructor = getConstructor();
        this.types = constructor.getParameterTypes();
        this.parameterAnnotations = constructor.getParameterAnnotations();
    }

    @Override
    public String toString() {
        return "DefaultAdaptedFromFactory{" +
                "adaptedFromClass=" + adaptedFromClass +
                ", fromClass=" + fromClass +
                ", toClass=" + toClass +
                ", purposeEnum=" + purposeEnum +
                ", purpose=" + purpose +
                '}';
    }

    @Override
    public boolean supports(Class<?> targetClass, Class<? extends Enum<?>> purposeEnum, Object purpose) {
        boolean targetMatch = toClass.isAssignableFrom(targetClass);
        boolean enumMatch = purposeEnum.equals(this.purposeEnum);
        boolean purposeMatch = purpose.equals(this.purpose);
        return targetMatch && enumMatch && purposeMatch;
    }

    @Override
    public Object create(Object source) {
        try {
            Object[] parameters = getParameters(source);
            Object created = constructor.newInstance(parameters);
            // handle constructor injection but autowire after to support autowired field usage@
            context.getAutowireCapableBeanFactory().autowireBean(created);
            // initialize to wrap in a proxy that supports advice which allows our adapter feature to work
            Object advised = context.getAutowireCapableBeanFactory().initializeBean(created, "");
            return advised;
        } catch (Exception e) {
            throw new AdapterException("Failed to create instance", e);
        }
    }

    protected Constructor getConstructor() {
        for (Constructor<?> ctor: adaptedFromClass.getConstructors()) {
            if (ctor.isAnnotationPresent(Inject.class) || ctor.isAnnotationPresent(Autowired.class)) {
                return ctor;
            }
        }
        throw new AdapterException(String.format("No constructor annotated with @Inject or @Autowired found in %s", adaptedFromClass.getName()));
    }

    protected Object[] getParameters(Object source) {
        Object[] parameters = new Object[constructor.getParameterCount()];
        for (int i = 0; i < parameters.length; i++) {
            boolean found = false;
            for (Annotation annotation: parameterAnnotations[i]) {
                if (Source.class == annotation.annotationType()) {
                    parameters[i] = source;
                    found = true;
                    break;
                } else if (Purpose.class == annotation.annotationType()) {
                    Object enumPurposeValue = null;
                    Enum[] values = purposeEnum.getEnumConstants();
                    for (Enum value: values) {
                        if (value.name().equals(purpose)) {
                            enumPurposeValue = value;
                            break;
                        }
                    }
                    if (null == enumPurposeValue) {
                        throw new AdapterException("Failed to find enum with value" + purpose);
                    }
                    parameters[i] = enumPurposeValue;
                    found = true;
                    break;
                }
            }
            if (!found) {
                parameters[i] = context.getBean(types[i]);
            }
        }
        return parameters;
    }
}
