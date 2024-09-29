package net.timeboxing.spring.adapter;

public interface AdaptedFromFactory {

    boolean supports(Class<?> targetClass, Class<? extends Enum<?>> purposeEnum, Object purpose);

    Object create(Object source);
}
