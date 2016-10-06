package com.cl.cloud.web;

import com.cl.cloud.domain.CodeRequest;
import com.cl.cloud.domain.ExecutionResult;
import com.cl.cloud.engine.CodeEngine;
import com.cl.cloud.engine.JavaCodeEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String post(CodeRequest request, ModelMap map) {
        System.out.println("code : " + request.getCodes());
        CodeEngine engine = new JavaCodeEngine();
        ExecutionResult result = engine.execute(request.getCodes());
        map.addAttribute("output", result.getOutput());
        return "redirect:/";
    }

}
