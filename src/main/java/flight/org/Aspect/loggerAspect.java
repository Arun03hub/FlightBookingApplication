package flight.org.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class loggerAspect {
    public static final Logger logger= LoggerFactory.getLogger(loggerAspect.class);

    @Around("execution(* flight.org.Service..*(..))")
    public  Object logMethode(ProceedingJoinPoint joinPoint) throws Throwable{
        String methodName=joinPoint.getSignature().toShortString();
        long startTime=System.currentTimeMillis();
        logger.info("STARTED: {}",methodName);
        Object result=joinPoint.proceed();
        long endTime=System.currentTimeMillis() - startTime;
        logger.info("COMPLETED: {} | Time: {} ms", methodName,endTime);
        return  result;
    }

    @AfterThrowing(pointcut = "execution(* flight.org.Service..*(..))", throwing = "exception")
    public  void logException(JoinPoint joinPoint, Exception exception){
        logger.error("EXCEPTION in {} | Message: {}", joinPoint.getSignature().toShortString(), exception.getMessage());
    }
}
