package com.sumit.JobApp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.stereotype.Component;

@Component
//we have to include this to use AOP
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER= LoggerFactory.getLogger(LoggingAspect.class);

    private static final Logger LOGGER1= LoggerFactory.getLogger(PerformanceMonitorInterceptor.class);

    //here we said before the execution of(execution(* com.sumit.spring_boot_rest.service.JobService.*(..))) this method call logMethodCall method
    //return type,class-name.method-name(args)
    //this is a point-cut
    @Before("execution(* com.sumit.spring_boot_rest.service.JobService.getJob(..))")
    public void logMethodCall(JoinPoint joinPoint)
    {
        //here we maintain the log on system
        LOGGER.info("Metthod called " + joinPoint.getSignature().getName());
    }

    //this is called after the execution of the below method
    @After("execution(* com.sumit.spring_boot_rest.service.JobService.getJob(..))||execution(* com.sumit.spring_boot_rest.service.JobService.updateJob(..))")
    public void logMethodExecuted(JoinPoint joinPoint)
    {
        //here we maintain the log on system
        LOGGER.info("Metthod Executed " + joinPoint.getSignature().getName());
    }

    //it only executed when there is error in the below described methods
    @AfterThrowing("execution(* com.sumit.spring_boot_rest.service.JobService.getJob(..))")
    public void logMethodCrash(JoinPoint joinPoint)
    {
        //here we maintain the log on system
        LOGGER.info("Metthod has some issues " + joinPoint.getSignature().getName());
    }

    //it only executed when there is no error after execution of the described methods
    @AfterReturning("execution(* com.sumit.spring_boot_rest.service.JobService.getJob(..))||execution(* com.sumit.spring_boot_rest.service.JobService.updateJob(..))")
    public void logMethodExecutedSuccess(JoinPoint joinPoint)
    {
        //here we maintain the log on system
        LOGGER.info("Metthod Executed Successfully " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.sumit.spring_boot_rest.service.JobService.getJob(..))")
    public Object monitorTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //start the time
        long start=System.currentTimeMillis();
        //after starting the time call the getJob method
        Object obj=joinPoint.proceed(); //if we not take this object here so our output of http://localhost:8080/jobPost/4 is not print on the postman
        //end the time
        long end=System.currentTimeMillis();

        LOGGER1.info("Time taken : "+(end-start));

        return obj;
    }


}
