package com.dethan.java.common.util;

import com.dethan.java.common.constant.Constant;
import com.dethan.java.common.sfunction.LambdaMeta;
import com.dethan.java.common.sfunction.SFunction;
import com.dethan.java.common.sfunction.meta.IdeaProxyLambdaMeta;
import com.dethan.java.common.sfunction.meta.ReflectLambdaMeta;
import com.dethan.java.common.sfunction.meta.ShadowLambdaMeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LambdaUtil {

    /**
     * 该缓存可能会在任意不定的时间被清除
     *
     * @param func 需要解析的 lambda 对象
     * @param <T>  类型，被调用的 Function 对象的目标类型
     * @return 返回解析后的结果
     */
    public static <T> LambdaMeta extract(SFunction<T, ?> func) {
        // 1. IDEA 调试模式下 lambda 表达式是一个代理
        if (func instanceof Proxy) {
            return new IdeaProxyLambdaMeta((Proxy) func);
        }
        // 2. 反射读取
        try {
            Method method = func.getClass().getDeclaredMethod(Constant.NAME_METHOD_WRITE_REPLACE);
            method.setAccessible(true);
            return new ReflectLambdaMeta((SerializedLambda) method.invoke(func), func.getClass().getClassLoader());
        } catch (Throwable e) {
            // 3. 反射失败使用序列化的方式读取
            return new ShadowLambdaMeta(extractSerializedLambda(func));
        }
    }

    public static SerializedLambda extractSerializedLambda(Serializable serializable) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(stream)) {
            oos.writeObject(serializable);
            oos.flush();
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(stream.toByteArray())) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                    return super.resolveClass(desc);
                }

            }) {
                return (SerializedLambda) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
