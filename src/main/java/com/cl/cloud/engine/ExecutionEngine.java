package com.cl.cloud.engine;

import com.cl.cloud.annotations.Language;
import com.cl.cloud.domain.CodeRequest;
import com.cl.cloud.domain.ExecutionResult;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ling Cao on 2016/10/11.
 */
public class ExecutionEngine {

    private static final ExecutionEngine INSTANCE = new ExecutionEngine();

    private static  final Stopwatch stopwatch = Stopwatch.createStarted();


    private static final Map<String, CodeEngine> engineMap = Maps.newConcurrentMap();

    static {

        /**
         * initialize all engines
         */
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassPath classPath = ClassPath.from(classLoader);
            // scan all classes in the same package
            ImmutableSet<ClassPath.ClassInfo> classInfos =
                    classPath.getTopLevelClasses(ExecutionEngine.class.getPackage().getName());

            classInfos.stream().forEach(c -> {
                Class clazz = c.load();
                Class[] interfaces = clazz.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {

                    for (Class interfaceClass : interfaces) {
                        if (interfaceClass == CodeEngine.class) {
                            Annotation[] annotations = clazz.getAnnotationsByType(Language.class);
                            if (annotations != null) {
                                for (Annotation a : annotations) {
                                    Language language = (Language) a;
                                    try {
                                        engineMap.put(language.value(), (CodeEngine) clazz.newInstance());
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ExecutionEngine() {

    }

    public static ExecutionEngine getInstance() {
        return INSTANCE;
    }

    public static ExecutionResult execute(CodeRequest request) {
        CodeEngine engine = engineMap.get(request.getLanguage().toLowerCase());
        if (engine == null) {
            throw new UnsupportedOperationException("can't execute " + request.getLanguage() + " code");
        }

        stopwatch.reset();
        stopwatch.start();
        ExecutionResult result =  engine.execute(request.getCodes());
        stopwatch.stop();
        result.setConsumedTime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

}
