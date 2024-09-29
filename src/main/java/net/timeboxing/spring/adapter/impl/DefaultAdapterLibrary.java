package net.timeboxing.spring.adapter.impl;

import net.timeboxing.spring.adapter.AdaptedFrom;
import net.timeboxing.spring.adapter.AdapterLibrary;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
import java.util.Set;

public class DefaultAdapterLibrary implements AdapterLibrary {

    private final Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions;

    public DefaultAdapterLibrary() {
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
    }

    @Override
    public Set<AnnotatedBeanDefinition> adaptedFromBeanDefinitions() {
        return adaptedFromBeanDefinitions;
    }

//                AnnotationMetadata annotationMetadata = abd.getMetadata();
//                MergedAnnotation<AdaptedFrom> adaptedFrom = annotationMetadata.getAnnotations().get(AdaptedFrom.class);
//                Class<?> from = adaptedFrom.getClass("from");
//                Class<?> to = adaptedFrom.getClass("to");
}
