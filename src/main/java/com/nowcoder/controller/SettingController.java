package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by nowcoder on 2016/6/26.
 */
@Controller
public class SettingController {
	
	//develop 测试文件外未冲突
	public void good(){
		
	}
	
    @RequestMapping("/setting")
    @ResponseBody
    public String setting() {
        return "Setting:OK";
    }
}
