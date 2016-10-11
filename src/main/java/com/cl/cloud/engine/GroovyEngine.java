package com.cl.cloud.engine;

import com.cl.cloud.domain.ExecutionResult;
import com.google.common.collect.Lists;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Ling Cao on 2016/10/11.
 */
public class GroovyEngine implements CodeEngine {
    @Override
    public ExecutionResult execute(String code) {

        ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();

        PrintStream output = new PrintStream(bytesOutput);
        Binding binding = new Binding();
        binding.setProperty("out", new PrintStream(output));
        GroovyShell shell = new GroovyShell(binding);
        shell.run(code, "test", Lists.newArrayList());

        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setSuccess(true);
        executionResult.setOutput(bytesOutput.toString());

        return executionResult;
    }
}
