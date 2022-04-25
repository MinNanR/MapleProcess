package site.minnan.mp.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.minnan.mp.domain.aggregate.Character;
import site.minnan.mp.infrastructure.annotations.CharacterRequired;
import site.minnan.mp.infrastructure.exception.EntityNotExistException;
import site.minnan.mp.userinterface.response.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 控制器必要条件检查
 *
 * @author Minnan on 2022/04/15
 */
@Aspect
@Component
@Slf4j
public class ControllerCheck {


    @Pointcut("execution(public * site.minnan.mp.applicaiton.service.impl.*..*(..))")
    private void service() {
    }

    @Autowired
    private HttpSession session;

    @Around("service()")
    public Object checkAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CharacterRequired characterRequired = method.getAnnotation(CharacterRequired.class);

        if (characterRequired == null) {
            return proceedingJoinPoint.proceed();
        }

        Character character = (Character) session.getAttribute("currentCharacter");
        if (character == null || character.getId() == null) {
            throw new EntityNotExistException("未指定使用角色");
        } else {
            return proceedingJoinPoint.proceed();
        }
    }

}
