package net.timeboxing.spring.adapter.testimpl;

import net.timeboxing.spring.adapter.AdaptedFrom;

@AdaptedFrom(from = User.class, to = Exporter.class)
public class DefaultUserExporter implements Exporter {

    public DefaultUserExporter() {
        /* NOOP */
    }

    @Override
    public String export() {
        return "Testing";
    }
}
