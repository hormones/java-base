package com.dethan.java.common.sfunction.meta;


import com.dethan.java.common.constant.Constant;
import com.dethan.java.common.sfunction.LambdaMeta;
import com.dethan.java.common.util.ClassUtils;

import java.lang.invoke.SerializedLambda;

public class ReflectLambdaMeta implements LambdaMeta {
    private final SerializedLambda lambda;

    private final ClassLoader classLoader;

    public ReflectLambdaMeta(SerializedLambda lambda, ClassLoader classLoader) {
        this.lambda = lambda;
        this.classLoader = classLoader;
    }

    @Override
    public String getImplMethodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> getInstantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(
                2, instantiatedMethodType.indexOf(Constant.SEMICOLON)).replace(Constant.SLASH, Constant.DOT);
        return ClassUtils.toClassConfident(instantiatedType, this.classLoader);
    }
}

