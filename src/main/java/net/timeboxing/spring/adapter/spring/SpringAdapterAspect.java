package net.timeboxing.spring.adapter.spring;

import net.timeboxing.spring.adapter.Adapter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class SpringAdapterAspect {

    private final Adapter adapter;

    public SpringAdapterAspect(Adapter adapter) {
        this.adapter = adapter;
    }

    private static final Logger LOG = LoggerFactory.getLogger(SpringAdapterAspect.class);

    @Around("within(net.timeboxing.spring.adapter.Adaptable+)  && execution(* adaptTo(..))")
    public Object adaptableInvocation(ProceedingJoinPoint pjp) {
        LOG.debug("Invoked for {}", pjp.getTarget().getClass().getName());
        return Optional.empty();
    }
}
