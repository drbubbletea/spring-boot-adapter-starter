package net.timeboxing.spring.adapter;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;

import java.util.Map;
import java.util.Set;

public interface AdapterLibrary {

    Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions();

    Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> adaptedFromFactories();
}
