package com.pinyougou.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆用户类
 * @author 86189
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("/name")
	public Map name() {
		//获取用户登陆的用户名
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Map map= new HashMap<>();
		
		map.put("loginName", name);
		return map;
		
	}
	

}
