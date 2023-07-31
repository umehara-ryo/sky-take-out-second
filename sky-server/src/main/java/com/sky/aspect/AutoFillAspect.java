package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {
    }

    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        //1.当該メソッドをゲット、メソッドからアノテーションのバリューのタイプを判明
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationType operationType = signature.getMethod().getAnnotation(AutoFill.class).value();

        //2.joinPoint当該パラメータをゲット
        Object instance = joinPoint.getArgs()[0];

        //3.パラメータのセットメソッドを獲得
        try {
            Method setUpdateUser = instance.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            Method setCreateUser = instance.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateTime = instance.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreateTime = instance.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);

            //4.時間とオペレーターを設定(invokeメソッドで影分身を作る)
            Long currentId = BaseContext.getCurrentId();
            LocalDateTime now = LocalDateTime.now();

            if (operationType.equals(OperationType.INSERT)) {
                setUpdateUser.invoke(instance, currentId);
                setUpdateTime.invoke(instance, now);
                setCreateUser.invoke(instance, currentId);
                setCreateTime.invoke(instance, now);
            }

            if (operationType.equals(OperationType.UPDATE)) {
                setUpdateUser.invoke(instance, currentId);
                setUpdateTime.invoke(instance, now);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
