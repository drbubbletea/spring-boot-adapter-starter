package net.timeboxing.spring.adapter;

import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Set;

public interface AdapterLibrary {

    Set<BeanDefinition> adaptedFromBeanDefinitions();
}
