package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import ru.otus.annotations.Log;

public class Ioc {
    private Ioc() {}

    static TestLoggingInterface createTestLogging() {
        InvocationHandler invocationHandler = new TestLoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                invocationHandler
        );
    }

    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLogging;
        private final Map<Method, Method> classMethods;

        TestLoggingInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
            this.classMethods = this.initClassMethods();
        }

        private Map<Method, Method> initClassMethods() {
            Map<Method, Method> classMethods = new HashMap<>();
            Class<?> clazz = testLogging.getClass();
            try {
                for (Method interfaceMethod: TestLoggingInterface.class.getDeclaredMethods()) {
                    classMethods.put(interfaceMethod, clazz.getDeclaredMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes()));
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return classMethods;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (classMethods.get(method).isAnnotationPresent(Log.class)) {
                String[] argValues = new String[args.length];
                for (int i = 0; i < args.length; i++) {
                    argValues[i] = String.valueOf(args[i]);
                }
                System.out.println(String.format("executed method: %s, param: %s", method.getName(), String.join(", ", argValues)));
            }

            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "TestLoggingInvocationHandler{}";
        }
    }
}
