package com.github.slfotg.prime.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CachingAspect {

    private Cache<Long> piCache = new Cache<>();
    private Cache<Cache<Long>> phiCache = new Cache<>();

    @Pointcut("execution(public long com.github.slfotg.prime.PrimeCounter.countPrimes(long))")
    public void countPrimes() {}

    @Pointcut("execution(public long com.github.slfotg.prime.PrimeCounter.phi(long, long))")
    public void phi() {}

    @Around("countPrimes()")
    public Long aroundCountPrimes(ProceedingJoinPoint pjp) throws Throwable {
        Long x = (Long) pjp.getArgs()[0];
        return piCache.getOrUpdate(x, () -> execute(pjp));
    }

    @Around("phi()")
    public Long aroundPhi(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Long x = (Long) args[0];
        Long a = (Long) args[1];

        Cache<Long> xCache = phiCache.getOrUpdate(x, () -> new Cache<>());
        return xCache.getOrUpdate(a, () -> execute(pjp));
    }

    public long execute(ProceedingJoinPoint pjp) {
        try {
            return (Long) pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}