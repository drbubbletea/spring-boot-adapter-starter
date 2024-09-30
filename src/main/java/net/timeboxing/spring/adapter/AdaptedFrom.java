package net.timeboxing.spring.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdaptedFrom {

    Class<?> from();

    Class<?> to();

    Class<? extends Enum<?>> purposeEnum() default AdapterPurpose.class;

    String purposeValue();
}