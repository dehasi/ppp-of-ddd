package gambling.applicationServices.frameworkHooks;

import gambling.applicationServices.utils.Transaction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
class TransactionAspect {

    @Before("execution(* me.dehasi.aspect..*.*Controller*.*(..))")
    public void logBefore(JoinPoint joinPoint) {}

    @Around("execution(* me.dehasi.aspect..*.*Controller*.*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        try (var t = Transaction.start()) {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            throw e;
        }
    }
}
