package trader.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1) // Aspect의 적용 순서를 지정
@Component
public class PerformanceTraceAdvice {
	private static final Log logger = LogFactory.getLog(PerformanceTraceAdvice.class);
	
	@Around("execution(* trader.BrokerModel*.*(..))")
	public Object trace(ProceedingJoinPoint pjp) throws Throwable {
		String signatureString = null;
		long start = 0, finish = 0;

		if (logger.isDebugEnabled()) {
			signatureString = pjp.getSignature().toShortString();
			logger.debug(signatureString + " 시작");
			start = System.currentTimeMillis();
		}
		try {
			return pjp.proceed();
		} finally {
			if (logger.isDebugEnabled()) {
				finish = System.currentTimeMillis();
				logger.debug(signatureString + " 종료");
				logger.debug(signatureString + " 실행 시간: " + (finish-start) + "ms");
			}
		}
	}
	
}
