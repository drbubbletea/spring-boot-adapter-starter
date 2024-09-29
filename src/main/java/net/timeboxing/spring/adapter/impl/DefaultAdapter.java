package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.AdaptedFromFactory;
import net.timeboxing.spring.adapter.Adapter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class DefaultAdapter implements Adapter {

    private final Set<AdaptedFromFactory> factories;

    public DefaultAdapter(Set<AdaptedFromFactory> factories) {
        this.factories = Set.copyOf(factories);
    }

    @Override
    public <T> Optional<T> adaptTo(Object from, Class<T> desiredClass, Class<? extends Enum<?>> purposeEnum, Object purposeValue) {
        for (AdaptedFromFactory factory: factories) {
            if (factory.supports(desiredClass, purposeEnum, purposeValue.toString())) {
                return Optional.of((T) factory.create(from));
            }
        }
        return Optional.empty();
    }
}
