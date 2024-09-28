package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.Adapter;
import net.timeboxing.spring.adapter.AdapterLibrary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultAdapter implements Adapter {

    private final AdapterLibrary library;

    public DefaultAdapter(AdapterLibrary library) {
        this.library = library;
    }

    @Override
    public <T> Optional<T> adaptTo(Object from, Class<T> desiredClass, Class<? extends Enum<?>> purposeEnum, Object purposeValue) {
        return Optional.empty();
    }
}
