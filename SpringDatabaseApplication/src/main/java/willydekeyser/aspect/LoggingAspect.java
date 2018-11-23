package willydekeyser.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableAspectJAutoProxy
@Aspect
public class LoggingAspect {

	@Before("execution(* willydekeyser.dao.impl.*.*(..)) ")
	public void LoggingAdvice(JoinPoint joitPoint) {
		System.out.println("Advice run DAO: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	@Before("execution(* willydekeyser.service.impl.*.*(..)) ")
	public void AdviceServiceImpl(JoinPoint joitPoint) {
		System.out.println("Advice run service: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
	
	@Before("execution(* willydekeyser.controller.*.*(..)) ")
	public void AdviceController(JoinPoint joitPoint) {
		System.out.println("Advice run controller: " + joitPoint.getSignature().getDeclaringTypeName() + " - " + joitPoint.getSignature().getName());
	}
}
