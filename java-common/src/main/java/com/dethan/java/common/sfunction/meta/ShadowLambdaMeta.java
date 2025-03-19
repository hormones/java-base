package com.dethan.java.common.sfunction.meta;

import com.dethan.java.common.constant.Constant;
import com.dethan.java.common.sfunction.LambdaMeta;
import com.dethan.java.common.util.ClassUtils;

import java.lang.invoke.SerializedLambda;

/**
 * 基于 {@link SerializedLambda} 创建的元信息
 */
public class ShadowLambdaMeta implements LambdaMeta {
    private final SerializedLambda lambda;

    public ShadowLambdaMeta(SerializedLambda lambda) {
        this.lambda = lambda;
    }

    @Override
    public String getImplMethodName() {
        return lambda.getImplMethodName();
    }

    @Override
    public Class<?> getInstantiatedClass() {
        String instantiatedMethodType = lambda.getInstantiatedMethodType();
        String instantiatedType = instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(Constant.SEMICOLON)).replace(Constant.SLASH, Constant.DOT);
        return ClassUtils.toClassConfident(instantiatedType, this.getClass().getClassLoader());
    }
}
