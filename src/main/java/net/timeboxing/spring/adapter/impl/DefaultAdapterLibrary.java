package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.AdapterLibrary;
import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Set;

public class DefaultAdapterLibrary implements AdapterLibrary {

    private final Set<BeanDefinition> adaptedFromBeanDefinitions;

    public DefaultAdapterLibrary(Set<BeanDefinition> adaptedFromBeanDefinitions) {
        this.adaptedFromBeanDefinitions = Set.copyOf(adaptedFromBeanDefinitions);
    }

    @Override
    public Set<BeanDefinition> adaptedFromBeanDefinitions() {
        return adaptedFromBeanDefinitions;
    }
}
