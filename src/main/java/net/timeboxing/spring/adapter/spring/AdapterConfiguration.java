package net.timeboxing.spring.adapter.spring;

import jakarta.inject.Singleton;
import net.timeboxing.spring.adapter.AdaptedFromFactory;
import net.timeboxing.spring.adapter.AdapterLibrary;
import net.timeboxing.spring.adapter.impl.DefaultAdapterLibrary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AdapterConfiguration {

    @Bean
    @Singleton
    public AdapterLibrary adapterLibrary(ApplicationContext context) {
        return new DefaultAdapterLibrary(context);
    }

    @Bean
    public Set<AdaptedFromFactory> factories(AdapterLibrary library) {
        return library.adaptedFromFactories();
    }
}
