package com.numble.reservationsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j // log추가
@Aspect
@Component // Bean객체 등록
public class LoggingAspect {
    // @Before : Advice 이전 실행, 실제로 실행될 코드를 나타냄 <-> @After : 특정 JoinPoint 이후에 Advice 실행
    // @Before.value : PointCut Desinator, 어느 JoinPoint에서 실행될 것인가

    // execution: 메서드 지정

    // @anootation : 어노테이션 지정 -> 일반적인 인증 절차를 벗어난 과정에서 사용
    @Before("@annotation(com.numble.reservationsystem.aop.LogArguments)")
    public void logByAnnotation() {

    }

    // within : 특정 클래스 또는 패키지 지정
    @Before("within(com.numble.reservationsystem.controller..*)")
    public void logParameters(JoinPoint joinPoint) { // JoinPoint: 이 Advice가 실행된 JoinPoint
        // Signature : JoinPoint의 정보를 담은 객체
        Signature signature = joinPoint.getSignature();
        // 메서드 이름 로깅
        log.info(signature.getName());
        // 메서드 인자들 로깅
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length == 0) {
            log.info("no args");
        }
        for (Object argument : arguments) {
            log.info("argument: [{}]",arguments);
        }
    }

    // 어떤 메소드가 실행되는데 걸리는 시간을 기록하고자 함.
    // Around : 해당 메서드 주변에서 실행
    @Around("within(com.numble.reservationsystem.controller..*)")
    public Object logExecutionTime(
        // Advice 내에서 대상 JointPoint가 실행되도록 요구할 수 있다.
        ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        // jointPoint에 해당하는 메소드를 진행
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("{} executed in {}ms", joinPoint.getSignature(),endTime-startTime);

        // jointPoint의 결과값을 잠깐 저장하고 다시 반환해준다.
        return proceed;
    }


}
