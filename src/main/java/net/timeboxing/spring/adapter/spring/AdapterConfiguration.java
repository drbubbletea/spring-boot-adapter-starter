package net.timeboxing.spring.adapter.spring;

import net.timeboxing.spring.adapter.AdaptedFrom;
import net.timeboxing.spring.adapter.AdapterLibrary;
import net.timeboxing.spring.adapter.impl.DefaultAdapterLibrary;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

@Configuration
public class AdapterConfiguration {

    @Bean
    public AdapterLibrary adapterLibrary() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(AdaptedFrom.class));
        Set<BeanDefinition> definitions = scanner.findCandidateComponents("net.timeboxing");
        return new DefaultAdapterLibrary(definitions);
    }
}
