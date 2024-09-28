package net.timeboxing.spring.adapter;

import java.util.Optional;

public interface Adapter {

    <T>Optional<T> adaptTo(Object from, Class<T> desiredClass, Class<? extends Enum<?>> purposeEnum, Object purposeValue);
}
