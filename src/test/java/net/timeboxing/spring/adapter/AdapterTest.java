package net.timeboxing.spring.adapter;

import net.timeboxing.spring.adapter.testimpl.DefaultUser;
import net.timeboxing.spring.adapter.testimpl.Exporter;
import net.timeboxing.spring.adapter.testimpl.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdapterTest {

    @DisplayName("Adapter invocation on non-managed bean throws helpful exception")
    @Test()
    public void unmanagedBeanAdaptInvocationThrowsException() {
        User user = new DefaultUser(1);
        assertThrows(AdaptNotTriggeredException.class, () -> user.adaptTo(Exporter.class));
    }
}
