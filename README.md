
# Spring Boot Adapter Starter
An opinionated implementation of the Adapter pattern that is easy to integrate into a Spring Boot project.

# Usage

## Maven POM Dependency Import
TODO

## Spring-Managed Beans
Supports AOP for beans managed by Spring implementing the Adaptable interface.
```java
public class DefaultUser implements Adaptable {
    //...
}

@Configuration
static class UserFactory {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DefaultUser createUser(Integer id) {
        return new DefaultUser(id);
    }
}

@Inject
UserFactory userFactory;

// usage for Spring-managed beans with Spring AOP
Optional<Exporter> exporter = userFactory.createUser(5).adaptTo(Exporter.class);

```

## Manually created beans
One approach for manually-created beans is injecting the `Adapter` and invoking `adaptTo`.
```java
import net.timeboxing.spring.adapter.AdapterPurpose;
import org.springframework.beans.factory.annotation.Autowired;

@Autowired
private Adapter adapter;

// usage for manually created beans
DefaultUser user = new DefaultUser(5);
Optional<Exporter> exporter = adapter.adaptTo(user, Exporter.class, AdapterPurpose.class, "EXPORT");
```

## Code


## Motivation