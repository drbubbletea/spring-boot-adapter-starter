package net.timeboxing.spring.adapter;

import java.util.Optional;

/**
 * Interface which allows an object to support the adapter feature.
 */
public interface Adaptable {

    default <T> Optional<T> adaptTo(Class<T> desiredClass, Object purposeValue) {
        throw new AdaptNotTriggeredException();
    }

    default <T> Optional<T> adaptTo(Class<T> desiredClass, Object purposeEnum, Object purposeValue) {
        throw new AdaptNotTriggeredException();
    }
}
