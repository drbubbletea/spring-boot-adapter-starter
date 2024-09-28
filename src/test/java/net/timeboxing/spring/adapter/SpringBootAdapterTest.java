package net.timeboxing.spring.adapter;

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
@SpringBootTest(classes = {SpringBootAdapterTest.TestApplication.class, SpringBootAdapterTest.TestFactory.class})
public class SpringBootAdapterTest {

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
    private TestFactory userFactory;

    @DisplayName("Spring prototype type bean triggers adapt")
    @Test
    public void springFactoryPrototypeBeanTriggersAdapt() {
        User user = userFactory.createUser(1);
        assertDoesNotThrow(() -> user.adaptTo(Exporter.class));
    }
}
