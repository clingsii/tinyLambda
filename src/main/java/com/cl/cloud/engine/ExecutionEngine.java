package com.cl.cloud.engine;

import com.cl.cloud.domain.CodeRequest;
import com.cl.cloud.domain.ExecutionResult;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Ling Cao on 2016/10/11.
 */
public class ExecutionEngine {

    private static ExecutionEngine executionEngine;


    private static Map<String, CodeEngine> engineMap;

    static {
        executionEngine = new ExecutionEngine();
        executionEngine.engineMap = Maps.newHashMap();
        executionEngine.engineMap.put("java", new JavaCodeEngine());
        executionEngine.engineMap.put("groovy", new GroovyEngine());
    }

    private ExecutionEngine(){

    }

    public static ExecutionEngine getInstance(){
        return executionEngine;
    }

    public static ExecutionResult execute(CodeRequest request) {
        CodeEngine engine = engineMap.get(request.getLanguage().toLowerCase());
        if (engine == null) {
            throw new UnsupportedOperationException("can't execute " + request.getLanguage() + " code");
        }
        return engine.execute(request.getCodes());
    }
}
