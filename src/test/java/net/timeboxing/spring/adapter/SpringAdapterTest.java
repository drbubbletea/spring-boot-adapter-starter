package net.timeboxing.spring.adapter;

import net.timeboxing.spring.adapter.testimpl.CustomPurpose;
import net.timeboxing.spring.adapter.testimpl.DefaultUser;
import net.timeboxing.spring.adapter.testimpl.Exporter;
import net.timeboxing.spring.adapter.testimpl.User;
import org.junit.jupiter.api.Assertions;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringAdapterTest.TestApplication.class, SpringAdapterTest.TestFactory.class})
public class SpringAdapterTest {

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
    private TestFactory factory;

    @Test
    public void canAdaptDefaultPurposeEnum() {
        User user = factory.createUser(5);
        Exporter result = user.adaptTo(Exporter.class, AdapterPurpose.DEFAULT).orElseThrow();
        Assertions.assertEquals("Testing", result.export());
    }

    @Test
    public void canAdaptCustomPurposeEnum() {
        User user = factory.createUser(5);
        Exporter result = user.adaptTo(Exporter.class, CustomPurpose.FOO).orElseThrow();
        Assertions.assertEquals("CustomFooUser", result.export());
    }
}
