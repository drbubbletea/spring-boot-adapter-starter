package net.timeboxing.spring.adapter;

public class AdaptNotTriggeredException extends AdapterException {

    public AdaptNotTriggeredException() {
        super("Adaptable invocation did not get caught by our AOP aspect. Perhaps the invoking object is not managed by the container.");
    }
}
