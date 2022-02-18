package com.booksaw.guiAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ConfigManager {

	public final YamlConfiguration config;
	private final String resourceName, filePath;
	private final JavaPlugin plugin;

	public ConfigManager(String resourceName, JavaPlugin plugin, boolean updateChecks) {
		this.plugin = plugin;
		if (!resourceName.endsWith(".yml")) {
			resourceName = resourceName + ".yml";
		}

		this.resourceName = resourceName;

		File f = new File(plugin.getDataFolder() + File.separator + resourceName);
		this.filePath = f.getPath();

		if (!f.exists()) {
			plugin.saveResource(resourceName, false);
		}

		config = YamlConfiguration.loadConfiguration(f);

		if (updateChecks) {
			updateFromDefaultSave();
		}

	}

	/**
	 * Used to load / create a config file where the resource name is different from
	 * the destination path
	 * 
	 * @param resourceName The name of the resource within the jar file
	 * @param filePath     The path to save the resource to
	 */
	public ConfigManager(String resourceName, JavaPlugin plugin, String filePath) {
		this.plugin = plugin;
		if (!resourceName.endsWith(".yml")) {
			resourceName = resourceName + ".yml";
		}

		this.resourceName = resourceName;

		if (!filePath.endsWith(".yml")) {
			filePath = filePath + ".yml";
		}
		this.filePath = plugin.getDataFolder() + File.separator + filePath;
		File f = new File(plugin.getDataFolder(), filePath);

		if (!f.exists()) {
			saveResource(resourceName, this.filePath, false);
		}
		config = YamlConfiguration.loadConfiguration(f);

	}

	public void save() {
		save(true);
	}

	public void save(boolean log) {
		if (log) {
			Bukkit.getLogger().info("Saving new values to " + filePath);
		}

		File f = new File(filePath);
		try {
			config.save(f);
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + f, ex);
		}
	}

	public void updateFromDefaultSave() {
		updateFromDefaultSave(true);
	}

	public void updateFromDefaultSave(boolean log) {

		Logger logger = Bukkit.getLogger();

		if (log) {
			logger.info("[BetterTeams] Checking if the file " + resourceName + " is up to date");
		}

		List<String> changes = updateFileConfig(plugin.getResource(resourceName));

		if (log) {
			String pluginName = plugin.getDescription().getName();
			if (changes.isEmpty()) {
				logger.info("[BetterTeams] File is up to date");
			} else {
				logger.info("[" + pluginName + "] ==================================================================");
				logger.info("[" + pluginName + "] File is not updated, adding values under the following references:");

				for (String str : changes) {
					logger.info("[" + pluginName + "] - " + str);
				}

				logger.info("[" + pluginName + "]" + resourceName
						+ " is now upated to the latest version, thank you for using BetterTeams");
				logger.info("[" + pluginName + "] ==================================================================");

			}
		}

		if (!changes.isEmpty()) {
			save(false);
		}

	}

	private @NotNull List<String> updateFileConfig(@NotNull InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		return updateFileConfig(reader);
	}

	private @NotNull List<String> updateFileConfig(@NotNull BufferedReader reader) {

		List<String> addedPaths = new ArrayList<>();

		YamlConfiguration internalConfig = YamlConfiguration.loadConfiguration(reader);
		for (String str : internalConfig.getKeys(true)) {
			if (!config.contains(str)) {
				Object toSave = internalConfig.get(str);
				if (toSave == null || toSave instanceof ConfigurationSection) {
					continue;
				}

				// saving the new value to the config
				config.set(str, toSave);
				addedPaths.add(str);
			}
		}

		return addedPaths;

	}

	public void saveResource(String resourcePath, String resultPath, boolean replace) {
		if (resourcePath == null || resourcePath.equals(""))
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		if (resultPath == null || resultPath.equals(""))
			throw new IllegalArgumentException("ResultPath cannot be null or empty");

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = plugin.getResource(resourcePath);

		if (in == null)
			throw new IllegalArgumentException(
					"The embedded resource '" + resourcePath + "' cannot be found in " + plugin.getDataFolder());
		File outFile = new File(resultPath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(resultPath.substring(0, (lastIndex >= 0) ? lastIndex : 0));

		if (!outDir.exists())
			outDir.mkdirs();

		try {
			if (!outFile.exists() || replace) {
				if (!outFile.exists()) {
					outFile.createNewFile();
				}

				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
					out.write(buf, 0, len);
				out.close();
				in.close();
			} else {
				plugin.getLogger().log(Level.WARNING, "Could not save " + resourcePath + " to " + outFile + " because "
						+ outFile.getName() + " already exists.");
			}
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save " + resourcePath + " to " + resultPath, ex);
		}
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFilePath() {
		return filePath;
	}

}
