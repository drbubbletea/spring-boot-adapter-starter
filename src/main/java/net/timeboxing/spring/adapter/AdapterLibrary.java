package net.timeboxing.spring.adapter;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;

import java.util.Set;

public interface AdapterLibrary {

    Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions();
}
