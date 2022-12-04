package ru.otus.annotations;

import java.lang.annotation.Annotation;
import ru.otus.annotations.Test;
import ru.otus.annotations.Before;
import ru.otus.annotations.After;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public static void main(String[] args) {
        runTests("ru.otus.annotations.MainTest");
    }
    public static void runTests(String testClassName) {
        Class<?> testClazz = getClassByName(testClassName);
        List<Method> methods = getMethods(testClazz);
        Map<String, List<Method>> categorizedMethods = categorizeMethods(methods);
        List<Exception> failedTestInfo = new ArrayList<>();

        for (Method method : categorizedMethods.get("test")) {
            Object classInstance = instantiate(testClazz);
            for (Method beforeMethod : categorizedMethods.get("before")) {
                callMethod(classInstance, beforeMethod.getName());
            }
            try {
                callMethod(classInstance, method.getName());
            } catch (Exception ex) {
                failedTestInfo.add(ex);
            }

            for (Method beforeMethod : categorizedMethods.get("after")) {
                callMethod(classInstance, beforeMethod.getName());
            }
        }

        outputResults(failedTestInfo, categorizedMethods.get("test").size());

    }

    private static Map<String, List<Method>> categorizeMethods(List<Method> methods) {
        Map<String, List<Method>> annotatedMethods = new HashMap<>();
        annotatedMethods.put("before", new ArrayList<>());
        annotatedMethods.put("after", new ArrayList<>());
        annotatedMethods.put("test", new ArrayList<>());

        for (int i = 0; i < methods.size(); i++) {
            List<Annotation> annotations = List.of(methods.get(i).getDeclaredAnnotations());

            if (annotations.size() > 1) {
                throw new RuntimeException("More than one annotation per method.");
            }

            Method currentMethod = methods.get(i);
            if (currentMethod.isAnnotationPresent(Test.class)) {
                annotatedMethods.get("test").add(methods.get(i));
            } else if (currentMethod.isAnnotationPresent(Before.class)) {
                annotatedMethods.get("before").add(methods.get(i));
            } else if (currentMethod.isAnnotationPresent(After.class)) {
                annotatedMethods.get("after").add(methods.get(i));
            }
        }

        return annotatedMethods;
    }

    private static Object callMethod(Object object, String name, Object... args) {
        try {
            Method method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Class<?> getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<Method> getMethods(Class<?> clazz) {
        try {
            return List.of(clazz.getDeclaredMethods());
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    private static void outputResults(List<Exception> errors, int totalTests) {
        final int FAILS_NUMBER = errors.size();
        final int PASSED_NUMBER = totalTests - FAILS_NUMBER;
        for (Exception error : errors) {
            System.out.println(error.getCause());
            System.out.println(error.getStackTrace());
        }
        System.out.println(String.format("Passed: %d test(s), failed: %d test(s), total: %d test(s)", PASSED_NUMBER, FAILS_NUMBER, totalTests));
    }


}


