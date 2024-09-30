package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.AdaptedFromFactory;
import net.timeboxing.spring.adapter.Adapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DefaultAdapter implements Adapter {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAdapter.class);

    private final Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> factories;
    private final Map<AdaptedFromFactoryKey, AdaptedFromFactory> keyToFactory = new HashMap<>();

    public DefaultAdapter(Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> factories) {
        this.factories = factories;
    }

    @Override
    public <T> Optional<T> adaptTo(Object from, Class<T> desiredClass, Class<? extends Enum<?>> purposeEnum, Object purposeValue) {
        AdaptedFromFactoryKey key = new AdaptedFromFactoryKey(from.getClass(), desiredClass, purposeEnum, purposeValue);
        if (!keyToFactory.containsKey(key)) {
            AdaptedFromFactory factory = getClassFactory(from.getClass(), purposeEnum, desiredClass, purposeValue);
            if (null == factory) {
                factory = getInterfaceFactory(from.getClass(), purposeEnum, desiredClass, purposeValue);
            }
            keyToFactory.put(key, factory);
        }
        if (null != keyToFactory.get(key)) {
            return Optional.of((T) keyToFactory.get(key).create(from));
        } else {
            return Optional.empty();
        }
    }

    private AdaptedFromFactory getClassFactory(Class<?> from, Class<? extends Enum<?>> purposeEnum, Class<?> desiredClass, Object purposeValue) {
        if (factories.containsKey(purposeEnum)) {
            for (AdaptedFromFactory factory: factories.get(purposeEnum)) {
                LOG.trace("Checking class {}", from.getName());
                if (factory.supports(from, desiredClass, purposeEnum, purposeValue.toString())) {
                    return factory;
                }
            }
        }
        return null;
    }

    private AdaptedFromFactory getInterfaceFactory(Class<?> from, Class<? extends Enum<?>> purposeEnum, Class<?> desiredClass, Object purposeValue) {
        if (factories.containsKey(purposeEnum)) {
            for (Class<?> fromInterface: from.getInterfaces()) {
                for (AdaptedFromFactory factory: factories.get(purposeEnum)) {
                    LOG.trace("Checking interface {}", fromInterface.getName());
                    if (factory.supports(fromInterface, desiredClass, purposeEnum, purposeValue.toString())) {
                        return factory;
                    }
                }
            }
        }
        return null;
    }

    private static class AdaptedFromFactoryKey {
        private final Class<?> sourceClass;
        private final Class<?> targetClass;
        private final Class<? extends Enum<?>> purposeEnum;
        private final Object purpose;

        public AdaptedFromFactoryKey(Class<?> sourceClass, Class<?> targetClass, Class<? extends Enum<?>> purposeEnum, Object purpose) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
            this.purposeEnum = purposeEnum;
            this.purpose = purpose;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdaptedFromFactoryKey that = (AdaptedFromFactoryKey) o;
            return Objects.equals(sourceClass, that.sourceClass) && Objects.equals(targetClass, that.targetClass) && Objects.equals(purposeEnum, that.purposeEnum) && Objects.equals(purpose, that.purpose);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceClass, targetClass, purposeEnum, purpose);
        }
    }
}
