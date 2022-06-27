package com.spring.simpleconfigmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "welcome")
@Configuration
public class SampleConfiguration {
	     //property from config map 1 & 2
	    @Value("${message:property from spring boot local}")
	    private String message;
	    
	    //property from config map 2
	    @Value("${samplemessage:property from spring boot local sample message}")
	    private String samplemessage;
	    
	    //property from config map 1
	    @Value("${message1:property from spring boot local sample message from config map1}")
	    private String message1;
	    
	    //property from configmap1 and default
	    @Value("${default:default property}")
	    private String defaultValue;


	    public String getMessage() {
	        return message;
	    }

	    public String getSamplemessage() {
			return samplemessage;
		}

		public void setSamplemessage(String samplemessage) {
			System.out.println("the message from configuration class sample message:"+samplemessage);
			this.samplemessage = samplemessage;
		}

		public void setMessage(String message) {
	    	System.out.println("the message from configuration class:"+message);
	        this.message = message;
	    }

		public String getMessage1() {
			return message1;
		}

		public void setMessage1(String message1) {
			this.message1 = message1;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		
		
	}
	
