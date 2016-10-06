package com.cl.cloud.engine;

import com.cl.cloud.domain.ExecutionResult;

/**
 * Created by Ling Cao on 2016/10/6.
 */
public interface CodeEngine {

    /**
     * execute some code and return the result
     * @param code
     * @return ExecuteResult
     */
    ExecutionResult execute(String code);
}
