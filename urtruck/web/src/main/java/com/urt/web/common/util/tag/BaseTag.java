package com.urt.web.common.util.tag;

import java.io.IOException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.urt.interfaces.authority.TagService;


public class BaseTag extends SimpleTagSupport {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	protected TagService tagService;
	
	public void doTag() throws IOException {
		initSvc();
	}
	private void initSvc() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		tagService=(TagService)webApplicationContext.getBean("tagService");
		//tagService = ContextHelper.getBean("tagService");
	}

}
