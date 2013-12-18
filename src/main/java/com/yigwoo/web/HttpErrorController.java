package com.yigwoo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class HttpErrorController {
	/* It seems that sitemesh cannot correctly decorate the 
	 * forwarding page, the working approach I use is redirect
	 * the request to another url.
	 * I know this approach is kinda ugly...
	 * I don't know if other approaches are available, because
	 * this problem is not the main concern..
	 * Just leave it now.
	 */
	
	@RequestMapping(value="404")
	public String handle404() {
		return "redirect:/error/Error404";
	}
	
	@RequestMapping(value="500")
	public String handle500() {
		return "redirect:/error/Error500";
	}
	
	@RequestMapping("Error404")
	public String displayError404() {
		return "error/404";
	}
	
	@RequestMapping("Error500")
	public String displayError500() {
		return "error/500";
	}
}
