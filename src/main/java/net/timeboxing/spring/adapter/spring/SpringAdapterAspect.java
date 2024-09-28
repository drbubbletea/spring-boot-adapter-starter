package net.timeboxing.spring.adapter.spring;

import net.timeboxing.spring.adapter.Adapter;
import net.timeboxing.spring.adapter.AdapterException;
import net.timeboxing.spring.adapter.AdapterPurpose;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Listen for invocations of Adaptable method invocations on Spring-managed beans through Spring AOP. Recall that Spring
 * AOP advice uses proxies so this will not work by default when manually constructing new bean instances.
 */
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
        int arguments = pjp.getArgs().length;
        if (1 == arguments) {
            return adapter.adaptTo(pjp.getTarget(), (Class<?>) pjp.getArgs()[0], AdapterPurpose.class, "DEFAULT");
        } else if (2 == arguments) {
            return adapter.adaptTo(pjp.getTarget(), (Class<?>) pjp.getArgs()[0], AdapterPurpose.class, pjp.getArgs()[1]);
        } else if (3 == arguments) {
            // TODO: check cast
            return adapter.adaptTo(pjp.getTarget(), (Class<?>) pjp.getArgs()[0], (Class<? extends Enum<?>>) pjp.getArgs()[1], pjp.getArgs()[2]);
        }else {
            throw new AdapterException("Unsupported number of arguments: " + arguments);
        }
    }
}
