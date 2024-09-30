package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.AdaptedFrom;
import net.timeboxing.spring.adapter.AdaptedFromFactory;
import net.timeboxing.spring.adapter.AdapterLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.*;

public class DefaultAdapterLibrary implements AdapterLibrary {

    private final Logger LOG = LoggerFactory.getLogger(DefaultAdapterLibrary.class);

    private final Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions;

    private final Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> factories;

    public DefaultAdapterLibrary(ApplicationContext context) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(AdaptedFrom.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("net.timeboxing");
        Set<AnnotatedBeanDefinition> annotatedBeanDefinitions = new HashSet<>();
        for (BeanDefinition beanDefinition : definitions) {
            if (AnnotatedBeanDefinition.class.isAssignableFrom(beanDefinition.getClass())) {
                AnnotatedBeanDefinition abd = ((AnnotatedBeanDefinition) beanDefinition);
                annotatedBeanDefinitions.add(abd);
            }
        }
        this.adaptedFromBeanDefinitions = Set.copyOf(annotatedBeanDefinitions);

        Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> enumFactories = new HashMap<>();
        Set<AdaptedFromFactory> adaptedFromFactories = new LinkedHashSet<>();
        for (AnnotatedBeanDefinition definition: adaptedFromBeanDefinitions) {
            Class<?> adaptedFromClass;
            try {
                adaptedFromClass = Class.forName(definition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            AnnotationMetadata annotationMetadata = definition.getMetadata();
            MergedAnnotation<AdaptedFrom> adaptedFrom = annotationMetadata.getAnnotations().get(AdaptedFrom.class);
            Class<?> from = adaptedFrom.getClass("from");
            Class<?> to = adaptedFrom.getClass("to");
            Class<? extends Enum<?>> purposeEnum = (Class<? extends Enum<?>>) adaptedFrom.getClass("purposeEnum");

            String purposeValue = adaptedFrom.getString("purposeValue");
            AdaptedFromFactory factory = new DefaultAdaptedFromFactory(context, adaptedFromClass, from, to, purposeEnum, purposeValue);
            LOG.info("Created AdaptedFromFactory: {}", factory);
            adaptedFromFactories.add(factory);

            enumFactories.putIfAbsent(purposeEnum, new LinkedHashSet<>());
            enumFactories.get(purposeEnum).add(factory);
        }
        this.factories = enumFactories;
    }

    @Override
    public Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions() {
        return adaptedFromBeanDefinitions;
    }

    @Override
    public Map<Class<? extends Enum<?>>, Set<AdaptedFromFactory>> adaptedFromFactories() {
        return factories;
    }
}
