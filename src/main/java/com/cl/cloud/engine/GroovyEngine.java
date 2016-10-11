package com.cl.cloud.engine;

import com.cl.cloud.domain.ExecutionResult;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Created by Ling Cao on 2016/10/11.
 */
public class GroovyEngine implements CodeEngine {
    @Override
    public ExecutionResult execute(String code) {
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(code);
        Object result = script.run();

        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setSuccess(true);
        executionResult.setOutput(result.toString());

        return executionResult;
    }
}
