package com.meta1203.overload;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestController {
	@Resource(name = "serverId")
	private String serverId;
	
	@RequestMapping(value = "/ping", method = GET)
	public @ResponseBody String ping(HttpServletRequest request, HttpServletResponse response) {
		return serverId;
	}
	
	@RequestMapping(value = "/overload", method = POST)
	public @ResponseBody String overload(@RequestParam(defaultValue = "1024") int memory, @RequestParam(defaultValue = "600") int duration,
			@RequestParam(defaultValue = "0.8") double load, HttpServletRequest request, HttpServletResponse response) {
		Overload.overload(load, memory, duration);
		return "overload started";
	}
}
