package com.dethan.java.common.sfunction;

import com.dethan.java.common.util.LambdaUtil;
import org.junit.jupiter.api.Test;

class SFunctionTest {

    @Test
    void extractSFunction() {
        SFunction<TestClass, String> nameFunction = TestClass::getName;
        SFunction<TestClass, String> name2Function = TestClass::getName;
        SFunction<TestClass, Integer> ageFunction = TestClass::getAge;
        SFunction<TestClass, Integer> heightFunction = TestClass::getHeight;
        System.out.println(nameFunction);
        System.out.println(name2Function);
        System.out.println(ageFunction);
        System.out.println(heightFunction);

        LambdaMeta meta = LambdaUtil.extract(nameFunction);
        System.out.println(meta.getInstantiatedClass());
        System.out.println(meta.getImplMethodName());
    }

    public static class TestClass {

        private String name;

        private Integer age;

        private int height;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}