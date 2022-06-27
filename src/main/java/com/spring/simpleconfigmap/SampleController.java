package com.spring.simpleconfigmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RequestMapping("/test")
@RestController
public class SampleController {
	
	@Autowired 
	private SampleConfiguration conf;
	
	@Value("${welcome.message:property from spring boot local for 1.5}")
	private String welcomemessage;
	
	  @GetMapping
	    public String welcome() {
	        System.out.println("the controoler class from configmap 1 & 2 for 1.5->"+conf.getMessage());
	        return conf.getMessage();
	    }
	  @GetMapping("/message")
	    public String sampleMessage() {
	        System.out.println("the controoler class config map 2 for 1.5->"+conf.getSamplemessage());
	        return conf.getSamplemessage();
	    }
	  
	  @GetMapping("/message1")
	    public String sampleMessage1() {
	        System.out.println("the controoler class message 1 config map 1 for 1.5->"+conf.getMessage1());
	        return conf.getMessage1();
	    }
	  
	  @GetMapping("/default")
	    public String defaultMessage() {
	        System.out.println("the controoler class message 1 config map 1 for 1.5->"+conf.getDefaultValue());
	        return conf.getDefaultValue();
	    }
	  @GetMapping("/welcomemessage")
	    public String defaultMessage2() {
	        System.out.println("the controoler class welcomemessage ->"+welcomemessage);
	        return welcomemessage;
	    }


}
