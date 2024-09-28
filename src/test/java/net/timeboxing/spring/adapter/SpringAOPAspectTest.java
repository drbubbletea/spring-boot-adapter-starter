package net.timeboxing.spring.adapter;


import net.timeboxing.spring.adapter.testimpl.ComponentImpl;
import net.timeboxing.spring.adapter.testimpl.DefaultUser;
import net.timeboxing.spring.adapter.testimpl.Exporter;
import net.timeboxing.spring.adapter.testimpl.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringAOPAspectTest.TestApplication.class, SpringAOPAspectTest.TestFactory.class})
public class SpringAOPAspectTest {

    @SpringBootApplication
    static class TestApplication {
        /* NOOP */
    }

    @Configuration
    static class TestFactory {

        @Bean
        @Scope(BeanDefinition.SCOPE_PROTOTYPE)
        public User createUser(Integer id) {
            return new DefaultUser(id);
        }
    }

    @Autowired
    private ComponentImpl component;

    @Autowired
    private TestFactory userFactory;

    @Test
    public void aspectTriggersOnOneParameterInvocation() {
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class));
    }

    @Test
    public void aspectTriggersOnTwoParameterInvocation() {
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class, AdapterPurpose.class));
    }

    @Test
    public void aspectTriggersOnThreeParameterInvocation() {
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class, AdapterPurpose.class, "DEFAULT"));
    }

    @DisplayName("Spring prototype type bean triggers adapt")
    @Test
    public void springFactoryPrototypeBeanTriggersAdapt() {
        User user = userFactory.createUser(1);
        assertDoesNotThrow(() -> user.adaptTo(Exporter.class));
    }
}
