package net.timeboxing.spring.adapter;

import net.timeboxing.spring.adapter.testimpl.DefaultUser;
import net.timeboxing.spring.adapter.testimpl.Exporter;
import net.timeboxing.spring.adapter.testimpl.TestService;
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


//    @Configuration
//    static class TestConfiguration {
//
//        @Bean
//        public AdaptedFromFactory factory(ApplicationContext context) {
//            return new DefaultAdaptedFromFactory(context, DefaultUserExporter.class, User.class, Exporter.class, AdapterPurpose.class, "DEFAULT");
//        }
//    }

    @Autowired
    private TestFactory factory;

    @Autowired
    private TestService testService;

    @Test
    public void canAdapt() {
        User user = factory.createUser(5);
        Exporter result = user.adaptTo(Exporter.class, AdapterPurpose.DEFAULT).orElseThrow();
        Assertions.assertEquals("Testing", result.export());
    }
}
