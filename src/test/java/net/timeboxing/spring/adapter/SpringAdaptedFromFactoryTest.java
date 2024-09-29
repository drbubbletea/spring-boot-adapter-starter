package net.timeboxing.spring.adapter;

import net.timeboxing.spring.adapter.impl.DefaultAdaptedFromFactory;
import net.timeboxing.spring.adapter.testimpl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ClassUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringAdaptedFromFactoryTest.TestApplication.class, SpringAdaptedFromFactoryTest.TestConfiguration.class})
public class SpringAdaptedFromFactoryTest {

    @SpringBootApplication
    static class TestApplication {
        /* NOOP */
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public AdaptedFromFactory factory(ApplicationContext context) {
            return new DefaultAdaptedFromFactory(context, DefaultUserExporter.class, User.class, Exporter.class, AdapterPurpose.class, "DEFAULT");
        }
    }

    @Autowired
    private AdaptedFromFactory factory;

    @Autowired
    private TestService testService;

    @Test
    public void canConstructInstance() {
        User user = new DefaultUser(1);
        DefaultUserExporter result = (DefaultUserExporter) factory.create(user);
        Assertions.assertTrue(Enhancer.isEnhanced(result.getClass()));
        Assertions.assertEquals(DefaultUserExporter.class, ClassUtils.getUserClass(result));
        // @Source-annotated object
        Assertions.assertEquals(user, result.user());
        // @Purpose-annotated object
        Assertions.assertEquals(AdapterPurpose.DEFAULT, result.purpose());
        // injected from Spring
        Assertions.assertEquals(testService, result.testService());
    }
}
