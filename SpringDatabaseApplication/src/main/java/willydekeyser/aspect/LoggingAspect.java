package willydekeyser.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LoggingAspect {
	
	@Pointcut("execution(* willydekeyser.model.*.*(..))")
	private void generalPointcut() {
		
	}
	
	@Before("generalPointcut()")
	public void logAll(JoinPoint joitPoint) {
		//System.out.println("log All: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}

	//@Before("execution(* willydekeyser.dao.impl.*.*(..)) ")
	public void LoggingAdvice(JoinPoint joitPoint) {
		//System.out.println("Advice run DAO: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.service.impl.*.*(..)) ")
	public void AdviceServiceImpl(JoinPoint joitPoint) {
		//System.out.println("Advice run service: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(* willydekeyser.controller.*.*(..)) ")
	public void AdviceController(JoinPoint joitPoint) {
		//System.out.println("Advice run controller: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	//@Before("execution(public String getOmschrijving())")
	public void AdviceModel(JoinPoint joitPoint) {
		//System.out.println("Aspect run MODEL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	@Before("execution(* willydekeyser.controller.KasboekController.*(..))")
	public void AdviceAll(JoinPoint joitPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//System.err.println("Aspect run ALL: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
		System.err.println("REQUEST: " + request.getUserPrincipal().getName() + " - " + request.getLocalName());
	}
	
	@Around("execution(* willydekeyser.controller.*.*(..)) && target(service)")
	public Object logServiceAccess(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Controller execution time: " + service.getClass().getSimpleName() + " - " + totalTime + "ms - " + result.toString());
        return result;

    }
	
}
