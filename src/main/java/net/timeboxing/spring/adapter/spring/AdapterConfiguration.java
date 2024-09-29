package net.timeboxing.spring.adapter.spring;

import net.timeboxing.spring.adapter.AdapterLibrary;
import net.timeboxing.spring.adapter.impl.DefaultAdapterLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfiguration {

    @Bean
    public AdapterLibrary adapterLibrary() {
        return new DefaultAdapterLibrary();
    }
}
