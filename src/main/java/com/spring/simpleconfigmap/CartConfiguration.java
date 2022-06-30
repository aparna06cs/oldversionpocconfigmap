package com.spring.simpleconfigmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties
@RefreshScope
@Configuration
public class CartConfiguration {
	@Value("${cart.success:property from spring boot local for 1.5 conf}")
	private String cartSucess;
	
	@Value("${cart.success1:property from spring boot local for 1.5 conf}")
	private String cartSucess1;
	
	@Value("${cart.failure:property from spring boot local for 1.5 conf}")
	private String cartFailure;
	
	@Value("${cart.failure1:property from spring boot local for 1.5 conf}")
	private String cartFailure1;
	
	@Value("${success:property from spring boot local for 1.5 conf}")
	private String success;
	
	@Value("${failure:property from spring boot local for 1.5 conf}")
	private String failure;
	
	@Value("${success1:property from spring boot local for 1.5 conf}")
    private String success1;
	
	@Value("${failure1:property from spring boot local for 1.5 conf}")
	private String failure1;

	public String getCartSucess() {
		return cartSucess;
	}

	public void setCartSucess(String cartSucess) {
		this.cartSucess = cartSucess;
	}

	public String getCartSucess1() {
		return cartSucess1;
	}

	public void setCartSucess1(String cartSucess1) {
		this.cartSucess1 = cartSucess1;
	}

	public String getCartFailure() {
		return cartFailure;
	}

	public void setCartFailure(String cartFailure) {
		this.cartFailure = cartFailure;
	}

	public String cartFailure() {
		return cartFailure1;
	}

	public void setCartFailure1(String cartFailure1) {
		this.cartFailure1 = cartFailure1;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getSuccess1() {
		return success1;
	}

	public void setSuccess1(String success1) {
		this.success1 = success1;
	}

	public String getFailure1() {
		return failure1;
	}

	public void setFailure1(String failure1) {
		this.failure1 = failure1;
	}
	
	
}
