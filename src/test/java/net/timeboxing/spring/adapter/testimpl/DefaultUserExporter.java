package net.timeboxing.spring.adapter.testimpl;

import jakarta.inject.Inject;
import net.timeboxing.spring.adapter.AdaptedFrom;
import net.timeboxing.spring.adapter.AdapterPurpose;
import net.timeboxing.spring.adapter.Purpose;
import net.timeboxing.spring.adapter.Source;

@AdaptedFrom(from = DefaultUser.class, to = Exporter.class, purposeValue = "DEFAULT")
public class DefaultUserExporter implements Exporter {

    private final TestService testService;
    private final User user;
    private final AdapterPurpose purpose;

    @Inject
    public DefaultUserExporter(@Source User user, @Purpose AdapterPurpose purpose, TestService testService) {
        this.testService = testService;
        this.user = user;
        this.purpose = purpose;
    }

    @Override
    public String export() {
        return "Testing";
    }

    public User user() {
        return user;
    }

    public TestService testService() {
        return testService;
    }

    public AdapterPurpose purpose() {
        return purpose;
    }
}
