package com.cl.cloud.engine;

import com.cl.cloud.annotations.Language;
import com.cl.cloud.domain.ExecutionResult;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Ling Cao on 2016/10/14.
 */
@Language("javascript")
public class JavascriptEngine implements CodeEngine {
    @Override
    public ExecutionResult execute(String code) {
        ExecutionResult result = new ExecutionResult();


        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer, true);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        engine.getContext().setWriter(pw);

        try {
            engine.eval(code);
            result.setSuccess(true);
            result.setOutput(writer.getBuffer().toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }
}
