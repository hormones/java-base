package com.dethan.boot.aop;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.env.Environment;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@SpringBootTest
class SPELParserBootTest {
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BeanFactory beanFactory;

    @Resource
    private Environment environment;

    @Test
    public void parse() {
        StandardEvaluationContext context = new StandardEvaluationContext(applicationContext);
        context.setVariable("env", environment);
        context.setVariable("name", "i am test!");
        context.setBeanResolver(new BeanFactoryResolver(beanFactory));
        // 通过beanResolver获取spring对象解析数据
        System.out.println(PARSER.parseExpression("@environment.getProperty('USERNAME')").getValue(context));
        // 通过setVariable获取spring对象解析数据
        System.out.println(PARSER.parseExpression("#env.getProperty('USERNAME')").getValue(context));
        // 通过setVariable获取普通变量解析数据
        System.out.println(PARSER.parseExpression("#name").getValue(context));
        // 通过setVariable获取不存在的变量解析数据
        System.out.println(PARSER.parseExpression("#unknown").getValue(context));
    }
}