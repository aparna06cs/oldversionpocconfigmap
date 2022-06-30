package com.spring.simpleconfigmap;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This method is used to load the config reload properties
 * @author apaperic
 *
 */
@ConfigurationProperties(prefix = "spring.cloud.kubernetes.reload")
public class NewConfigReloadProperties  {


	/**
	 * Enables the Kubernetes configuration reload on change.
	 */
	private boolean enabled = false;

	/**
	 * Enables monitoring on config maps to detect changes.
	 */
	private boolean monitoringConfigMaps = true;

	/**
	 * Enables monitoring on secrets to detect changes.
	 */
	private boolean monitoringSecrets = false;

	/**
	 * Sets the reload strategy for Kubernetes configuration reload on change.
	 */
	private ReloadStrategy strategy = ReloadStrategy.REFRESH;

	/**
	 * Sets the detection mode for Kubernetes configuration reload.
	 */
	private ReloadDetectionMode mode = ReloadDetectionMode.EVENT;

	/**
	 * Sets the polling period to use when the detection mode is POLLING.
	 */
	private Duration period = Duration.ofMillis(15000L);

	/**
	 * If Restart or Shutdown strategies are used, Spring Cloud Kubernetes waits a random
	 * amount of time before restarting. This is done in order to avoid having all
	 * instances of the same application restart at the same time. This property
	 * configures the maximum of amount of wait time from the moment the signal is
	 * received that a restart is needed until the moment the restart is actually
	 * triggered
	 */
	private Duration maxWaitForRestart = Duration.ofSeconds(2);

	public NewConfigReloadProperties() {
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isMonitoringConfigMaps() {
		return this.monitoringConfigMaps;
	}

	public void setMonitoringConfigMaps(boolean monitoringConfigMaps) {
		this.monitoringConfigMaps = monitoringConfigMaps;
	}

	public boolean isMonitoringSecrets() {
		return this.monitoringSecrets;
	}

	public void setMonitoringSecrets(boolean monitoringSecrets) {
		this.monitoringSecrets = monitoringSecrets;
	}

	public ReloadStrategy getStrategy() {
		return this.strategy;
	}

	public void setStrategy(ReloadStrategy strategy) {
		this.strategy = strategy;
	}

	public ReloadDetectionMode getMode() {
		return this.mode;
	}

	public void setMode(ReloadDetectionMode mode) {
		this.mode = mode;
	}

	public Duration getPeriod() {
		return this.period;
	}

	public void setPeriod(Duration period) {
		this.period = period;
	}

	public Duration getMaxWaitForRestart() {
		return maxWaitForRestart;
	}

	public void setMaxWaitForRestart(Duration maxWaitForRestart) {
		this.maxWaitForRestart = maxWaitForRestart;
	}

	/**
	 * Reload strategies.
	 */
	public enum ReloadStrategy {

		/**
		 * Fire a refresh of beans annotated with @ConfigurationProperties
		 * or @RefreshScope.
		 */
		REFRESH,

		/**
		 * Restarts the Spring ApplicationContext to apply the new configuration.
		 */
		RESTART_CONTEXT,

		/**
		 * Shuts down the Spring ApplicationContext to activate a restart of the
		 * container. Make sure that the lifecycle of all non-daemon threads is bound to
		 * the ApplicationContext and that a replication controller or replica set is
		 * configured to restart the pod.
		 */
		SHUTDOWN

	}

	/**
	 * Reload detection modes.
	 */
	public enum ReloadDetectionMode {

		/**
		 * Enables a polling task that retrieves periodically all external properties and
		 * fire a reload when they change.
		 */
		POLLING,

		/**
		 * Listens to Kubernetes events and checks if a reload is needed when configmaps
		 * or secrets change.
		 */
		EVENT

	}



}