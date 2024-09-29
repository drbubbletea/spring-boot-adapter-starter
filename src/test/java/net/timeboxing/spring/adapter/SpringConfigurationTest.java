package net.timeboxing.spring.adapter;

import net.timeboxing.spring.adapter.testimpl.DefaultUserExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringConfigurationTest.TestApplication.class})
public class SpringConfigurationTest {

    @SpringBootApplication
    static class TestApplication {
        /* NOOP */
    }

    @Autowired
    private AdapterLibrary library;

    @DisplayName("Spring configuration startup finds AdaptedFrom classes")
    @Test
    public void canScanForAdaptedFromClasses() {
        Set<String> names = library.adaptedFromBeanDefinitions().stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toSet());
        Assertions.assertTrue(names.contains(DefaultUserExporter.class.getCanonicalName()));
    }

    @DisplayName("AdaptedFrom 'to' targeting Spring-managed bean throws exception")
    @Test
    public void targetSpringManagedBeanFails() {
        Assertions.fail();
    }
}
