package net.timeboxing.spring.adapter;


import net.timeboxing.spring.adapter.testimpl.ComponentImpl;
import net.timeboxing.spring.adapter.testimpl.Exporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringAOPAspectTest.TestApplication.class)
public class SpringAOPAspectTest {

    @SpringBootApplication
    static class TestApplication {
        /* NOOP */
    }

    @Autowired
    private ComponentImpl component;

    @DisplayName("Adaptable invocation on Spring-managed bean does not throw an exception")
    @Test
    public void aspectTriggersOnSpringManagedBean() {
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class));
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class, AdapterPurpose.class));
        assertDoesNotThrow(() -> component.adaptTo(Exporter.class, AdapterPurpose.class, "DEFAULT"));
    }
}
