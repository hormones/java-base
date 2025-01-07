package com.dethan.boot.aop;

import com.dethan.boot.annotation.SPELAnnotation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Aspect
@Component
public class SPELAnnotationAspect {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final StandardEvaluationContext CONTEXT_BASIC = new StandardEvaluationContext();

    @Resource
    private BeanFactory beanFactory;

    @PostConstruct
    public void init() {
        CONTEXT_BASIC.setBeanResolver(new BeanFactoryResolver(beanFactory));
    }

    @Pointcut("@annotation(spelAnnotation)")
    public void configValuePointcut(SPELAnnotation spelAnnotation) {
    }

    @Before(value = "configValuePointcut(spelAnnotation)", argNames = "joinPoint,spelAnnotation")
    public void before(JoinPoint joinPoint, SPELAnnotation spelAnnotation) {
        String expression = spelAnnotation.value();  // 获取注解中的 SpEL 表达式

        // 创建 SpEL 表达式解析器
        EvaluationContext context = createEvaluationContext(joinPoint);

        // 解析 SpEL 表达式并获取计算结果
        Object resolvedValue = PARSER.parseExpression(expression).getValue(context);

        log.info("Resolved SpEL expression result: {}", resolvedValue);
    }

    private static EvaluationContext createEvaluationContext(JoinPoint joinPoint) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 赋予访问spring bean的能力
        CONTEXT_BASIC.applyDelegatesTo(context);
        // 赋予访问方法参数的能力
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = signature.getParameterNames();
        if (Objects.nonNull(parameterNames) && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return context;
    }
}
