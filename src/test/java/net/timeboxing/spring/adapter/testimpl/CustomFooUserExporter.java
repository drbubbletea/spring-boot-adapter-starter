package net.timeboxing.spring.adapter.testimpl;

import jakarta.inject.Inject;
import net.timeboxing.spring.adapter.AdaptedFrom;
import net.timeboxing.spring.adapter.Purpose;
import net.timeboxing.spring.adapter.Source;

@AdaptedFrom(from = User.class, to = Exporter.class, purposeEnum = CustomPurpose.class, purposeValue = "FOO")
public class CustomFooUserExporter implements Exporter {

    private final TestService testService;
    private final User user;
    private final CustomPurpose purpose;

    @Inject
    public CustomFooUserExporter(@Source User user, @Purpose CustomPurpose purpose, TestService testService) {
        this.testService = testService;
        this.user = user;
        this.purpose = purpose;
    }

    @Override
    public String export() {
        return "CustomFooUser";
    }
}
