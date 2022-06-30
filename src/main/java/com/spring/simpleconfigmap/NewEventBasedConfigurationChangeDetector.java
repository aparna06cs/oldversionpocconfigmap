package com.spring.simpleconfigmap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.cloud.kubernetes.config.ConfigMapPropertySource;
import org.springframework.cloud.kubernetes.config.ConfigMapPropertySourceLocator;
import org.springframework.cloud.kubernetes.config.SecretsPropertySource;
import org.springframework.cloud.kubernetes.config.SecretsPropertySourceLocator;
import org.springframework.cloud.kubernetes.config.reload.ConfigurationUpdateStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;

/**
 * This method overrides the 
 * @author apaperic
 *
 */
@Configuration
public class NewEventBasedConfigurationChangeDetector extends NewConfigurationChangeDetector {


	private ConfigMapPropertySourceLocator configMapPropertySourceLocator;

	private SecretsPropertySourceLocator secretsPropertySourceLocator;

	private Map<String, Watch> watches;

	public NewEventBasedConfigurationChangeDetector(AbstractEnvironment environment,
			NewConfigReloadProperties properties, KubernetesClient kubernetesClient,
			ConfigurationUpdateStrategy strategy,
			ConfigMapPropertySourceLocator configMapPropertySourceLocator,
			SecretsPropertySourceLocator secretsPropertySourceLocator) {
		super(environment, properties, kubernetesClient, strategy);

		this.configMapPropertySourceLocator = configMapPropertySourceLocator;
		this.secretsPropertySourceLocator = secretsPropertySourceLocator;
		this.watches = new HashMap<>();
	}

	@PostConstruct
	public void watch() {
		boolean activated = false;

		if (this.properties.isMonitoringConfigMaps()
				&& this.configMapPropertySourceLocator != null) {
			try {
				String name = "config-maps-watch";
				this.watches.put(name, this.kubernetesClient.configMaps()
						.watch(new Watcher<ConfigMap>() {
							@Override
							public void eventReceived(Action action,
									ConfigMap configMap) {
								onEvent(configMap);
							}

							@Override
							public void onClose(KubernetesClientException e) {
							}
						}));
				activated = true;
				this.log.info("Added new Kubernetes watch: " + name);
			}
			catch (Exception e) {
				this.log.error(
						"Error while establishing a connection to watch config maps: configuration may remain stale",
						e);
			}
		}

		if (this.properties.isMonitoringSecrets()
				&& this.secretsPropertySourceLocator != null) {
			try {
				activated = false;
				String name = "secrets-watch";
				this.watches.put(name,
						this.kubernetesClient.secrets().watch(new Watcher<Secret>() {
							@Override
							public void eventReceived(Action action, Secret secret) {
								onEvent(secret);
							}

							@Override
							public void onClose(KubernetesClientException e) {
							}
						}));
				activated = true;
				this.log.info("Added new Kubernetes watch: " + name);
			}
			catch (Exception e) {
				this.log.error(
						"Error while establishing a connection to watch secrets: configuration may remain stale",
						e);
			}
		}

		if (activated) {
			this.log.info(
					"Kubernetes event-based configuration change detector activated");
		}
	}

	@PreDestroy
	public void unwatch() {
		if (this.watches != null) {
			for (Map.Entry<String, Watch> entry : this.watches.entrySet()) {
				try {
					this.log.debug("Closing the watch " + entry.getKey());
					entry.getValue().close();

				}
				catch (Exception e) {
					this.log.error("Error while closing the watch connection", e);
				}
			}
		}
	}

	private void onEvent(ConfigMap configMap) {

		this.log.debug(String.format("onEvent configMap: %s", configMap.toString()));

		boolean changed = changed(
				locateMapPropertySources(this.configMapPropertySourceLocator,
						this.environment),
				findPropertySources(ConfigMapPropertySource.class));
		if (changed) {
			this.log.info("Detected change in config maps");
			reloadProperties();
		}
	}

	private void onEvent(Secret secret) {

		this.log.debug(String.format("onEvent configMap: %s", secret.toString()));

		boolean changed = changed(
				locateMapPropertySources(this.secretsPropertySourceLocator,
						this.environment),
				findPropertySources(SecretsPropertySource.class));
		if (changed) {
			this.log.info("Detected change in secrets");
			reloadProperties();
		}
	}


}
