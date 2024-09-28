package net.timeboxing.spring.adapter;


import net.timeboxing.spring.adapter.testimpl.ComponentImpl;
import net.timeboxing.spring.adapter.testimpl.Exporter;
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
}
