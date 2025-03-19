package com.dethan.java.common.util;

public class ClassUtils {

    /**
     * @param name
     * @param classLoader
     * @return
     * @since 3.4.3
     */
    public static Class<?> toClassConfident(String name, ClassLoader classLoader) {
        try {
            return loadClass(name, getClassLoaders(classLoader));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到class：" + name, e);
        }
    }

    private static Class<?> loadClass(String className, ClassLoader[] classLoaders) throws ClassNotFoundException {
        for (ClassLoader classLoader : classLoaders) {
            if (classLoader != null) {
                try {
                    return Class.forName(className, true, classLoader);
                } catch (ClassNotFoundException e) {
                    // ignore
                }
            }
        }
        throw new ClassNotFoundException("Cannot find class: " + className);
    }

    private static ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                Thread.currentThread().getContextClassLoader(),
                ClassUtils.class.getClassLoader(),
                ClassLoader.getSystemClassLoader()};
    }
}

