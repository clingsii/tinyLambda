package com.cl.cloud.web;

import com.cl.cloud.domain.CodeRequest;
import com.cl.cloud.domain.ExecutionResult;
import com.cl.cloud.engine.ExecutionEngine;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ling Cao on 2016/10/6.
 */

@Controller
public class WebController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("user", "meow");
        map.addAttribute("command", new CodeRequest());
        return "index";
    }

    @RequestMapping(value = "/code", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    public String post(CodeRequest request) {
        System.out.println("code : " + request.getCodes());
        ExecutionResult result = ExecutionEngine.getInstance().execute(request);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", result.isSuccess());
        jsonObject.put("output", result.getOutput());
        jsonObject.put("timeConsumed", result.getConsumedTime());
        return jsonObject.toString();
    }

}
