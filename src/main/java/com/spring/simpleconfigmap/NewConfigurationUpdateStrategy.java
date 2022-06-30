package com.spring.simpleconfigmap;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NewConfigurationUpdateStrategy {


	private String name;

	private Runnable reloadProcedure;

	public NewConfigurationUpdateStrategy(String name, Runnable reloadProcedure) {
		Objects.requireNonNull(name, "name cannot be null");
		Objects.requireNonNull(reloadProcedure, "reloadProcedure cannot be null");
		this.name = name;
		this.reloadProcedure = reloadProcedure;
	}

	public String getName() {
		return name;
	}

	public void reload() {
		this.reloadProcedure.run();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ConfigurationUpdateStrategy{");
		sb.append("name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
