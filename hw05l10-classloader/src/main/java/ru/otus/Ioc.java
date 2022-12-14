package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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

        TestLoggingInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method classMethod = this.testLogging.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (classMethod.isAnnotationPresent(Log.class)) {
                String[] argValues = new String[args.length];
                for (int i = 0; i < args.length; i++) {
                    argValues[i] = String.valueOf(args[i]);
                }
                System.out.println(String.format("executed method: %s, param: %s", method.getName(), String.join(", ", argValues)));
            }

            return method.invoke(this.testLogging, args);
        }
    }
}
