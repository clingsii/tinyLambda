package com.cl.cloud.engine;

import com.cl.cloud.domain.ExecutionResult;
import com.google.common.io.Files;
import net.openhft.compiler.CompilerUtils;

import java.io.*;
import java.nio.charset.Charset;


/**
 * Created by Ling Cao on 2016/10/6.
 */
public class JavaCodeEngine implements CodeEngine {

    @Override
    public ExecutionResult execute(String code) {
        ExecutionResult executionResult = new ExecutionResult();

        String className = getDynamicClassName();
        String javaCode = getClassContent(className, code);

        File logFile = new File("temp/run.log");

        OutputStream out = null;
        try {
            out = new FileOutputStream(logFile);
            System.setOut(new PrintStream(out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Class aClass = null;
        try {
            aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
            Runnable runner = (Runnable) aClass.newInstance();
            runner.run();

            String log = Files.toString(logFile, Charset.defaultCharset());
            executionResult.setOutput(log);
            logFile.delete();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return executionResult;
    }

    private String getDynamicClassName() {
        return "DynamicJava";
    }

    private String getClassContent(String className, String code) {
//        return "public class " + className + " { public static void main(String[] args) { "
//                + code + "  }}";

        return "import java.io.*; " +
                "public class " + className + "  implements Runnable {" +
                "    @Override " +
                "    public void run() { "

                + "File logFile = new File(\"temp/run.log\");" +
                "" +
                "        OutputStream out = null;" +
                "        try {" +
                "            out = new FileOutputStream(logFile);" +
                "            System.setOut(new PrintStream(out));"
                + code
                + "    } catch (FileNotFoundException e) {" +
                "            e.printStackTrace();" +
                "    }" +
                "  }" +
                "}";
    }

}
