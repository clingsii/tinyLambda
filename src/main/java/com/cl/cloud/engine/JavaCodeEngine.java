package com.cl.cloud.engine;

import com.cl.cloud.annotations.Language;
import com.cl.cloud.domain.ExecutionResult;
import net.openhft.compiler.CompilerUtils;

import java.io.*;


/**
 * Created by Ling Cao on 2016/10/6.
 */
@Language("java")
public class JavaCodeEngine implements CodeEngine {

    @Override
    public ExecutionResult execute(String code) {
        ExecutionResult executionResult = new ExecutionResult();

        String className = getDynamicClassName();
        String javaCode = getClassContent(className, code);


        ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytesOutput));

        Class aClass = null;
        try {
            aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
            Runnable runner = (Runnable) aClass.newInstance();
            runner.run();
            executionResult.setOutput(bytesOutput.toString());
            executionResult.setSuccess(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return executionResult;
    }

    private String getDynamicClassName() {
        return "DynamicJava" + System.nanoTime();
    }

    private String getClassContent(String className, String code) {
        return "import java.io.*; " +
                "public class " + className + "  implements Runnable {" +
                "    @Override " +
                "    public void run() { " +
                "try {"
                + code +
                " } catch(Exception e){e.printStackTrace();} }" +
                "}";
    }

}
