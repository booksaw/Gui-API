package com.booksaw.guiAPI.message;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

/**
 * Used to control all communications to the user
 *
 * @author booksaw
 */
public class MessageManager {

	/**
	 * Used to store all loaded messages
	 */
	private static HashMap<String, String> messages = new HashMap<>();

	private static FileConfiguration defaultMessages;

	/**
	 * This is the prefix which goes before all messages related to this plugin
	 */
	private static String prefix;

	/**
	 * This is the language reference for the selected language
	 */
	private static String lang;

	/**
	 * Stopping this class being instantiated
	 */
	private MessageManager() {
	}

	public static String getLanguage() {
		return lang;
	}

	public static void setLanguage(String lang) {
		MessageManager.lang = lang;
	}

	private static boolean placeholderAPI;

	public static void setPlaceholderAPI(boolean placeholderAPI) {
		MessageManager.placeholderAPI = placeholderAPI;
	}

	public static boolean isPlaceholderAPI() {
		return placeholderAPI;
	}

	/**
	 * This method is used to provide the configuration file in which all the
	 * message references are stored, this method also loads the default prefix
	 *
	 * @param file the configuration file
	 */
	public static void addMessages(String prefixFormat, String dataFolderPath, String pluginName,
			FileConfiguration file) {
		prefix = prefixFormat;
		defaultMessages = file;
		addMessages(file, dataFolderPath, pluginName, false);

	}

	/**
	 * Used to select a file to contain backup messages in the event that the
	 * community translations are incomplete
	 * 
	 * @param file           The file to load the backup messages from
	 * @param dataFolderPath The path to the data folder of this plugin
	 */
	public static void addBackupMessages(String dataFolderPath, String pluginName, YamlConfiguration file) {
		addMessages(file, dataFolderPath, pluginName, true);
	}

	private static void addMessages(FileConfiguration file, String dataFolderPath, String pluginName, boolean backup) {

		List<String> backupMessages = new ArrayList<>();

		for (String str : file.getKeys(true)) {
			if (!messages.containsKey(str)) {
				String toSave = file.getString(str);
				if (toSave == null || file.get(str) instanceof ConfigurationSection) {
					continue;
				}

				messages.put(str, toSave);

				if (backup) {
					backupMessages.add(str);
				}
			}
		}
		if (!backupMessages.isEmpty()) {

			saveMissingMessages(dataFolderPath, backupMessages);

			Logger logger = Bukkit.getLogger();
			logger.info("[" + pluginName + "] ==================================================================");
			logger.info("[" + pluginName
					+ "] Messages are missing from your selected language, the following messages are using english:");

			for (String str : backupMessages) {
				logger.info("[" + pluginName + "] - " + str + ": " + messages.get(str));
			}

			logger.info("[" + pluginName
					+ "] If you are able to help with translation please join the discord server and make yourself known (https://discord.gg/JF9DNs3)");
			logger.info("[" + pluginName
					+ "] A file called `missingMessages.txt` has been created within this plugins folder. To contribute to the community translations, translate the messages within it and submit it to the discord");
			logger.info("[" + pluginName + "] ==================================================================");
		}

	}

	private static void saveMissingMessages(String dataFolderPath, List<String> missingMessages) {

		File f = new File(dataFolderPath + File.separator + "missingMessages.txt");

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		try (PrintWriter writer = new PrintWriter(f)) {
			writer.println(
					"# Please translate these messages and then submit them to the Booksaw Development (https://discord.gg/JF9DNs3) in the #messages-submissions channel for a special rank");
			writer.println("# Your translations will be included in the next update");
			for (String str : missingMessages) {
				writer.println(str + ": " + messages.get(str));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Used to send a message to the specified user
	 *
	 * @param sender    the commandSender which the message should be sent to
	 * @param reference the reference for the message
	 */
	public static void sendMessage(CommandSender sender, String reference) {
		try {
			String message = getMessage(sender, reference);
			if (message.equals("")) {
				return;
			}

			sender.sendMessage(prefix + message);

		} catch (NullPointerException e) {
			Bukkit.getLogger().warning("Could not find the message with the reference " + reference);
			sender.sendMessage(prefix + "Something went wrong with the message, alert your server admins");
		}

	}

	/**
	 * Used to send a formatted message
	 *
	 * @param sender      the commandSender which the message should be sent to
	 * @param reference   the reference for the message
	 * @param replacement the value that the placeholder should be replaced with
	 */
	public static void sendMessageF(CommandSender sender, String reference, String... replacement) {
		try {
			String message = getMessage(sender, reference);
			if (message.equals("")) {
				return;
			}

			try {
				message = String.format(prefix + message, (Object[]) replacement);
			} catch (MissingFormatArgumentException e) {
				// expected error if the message does not contain %s
			}

			sender.sendMessage(message);
		} catch (NullPointerException e) {
			Bukkit.getLogger().warning("Could not find the message with the reference " + reference);
			sender.sendMessage(prefix + "Something went wrong with the message, alert your server admins");
		}
	}

	/**
	 * Used to send a formatted message
	 *
	 * @param sender      the commandSender which the message should be sent to
	 * @param reference   the reference for the message
	 * @param replacement the value that the placeholder should be replaced with
	 */
	public static void sendMessageF(CommandSender sender, String reference, Object[] replacement) {
		try {
			String message = getMessage(sender, reference);
			if (message.equals("")) {
				return;
			}

			if (message.contains("%s")) {
				message = String.format(prefix + message, replacement);
			}

			sender.sendMessage(message);
		} catch (NullPointerException e) {
			Bukkit.getLogger().warning("Could not find the message with the reference " + reference);
			sender.sendMessage(prefix + "Something went wrong with the message, alert your server admins");
		}
	}

	/**
	 * This is used to get the message from the provided location in the
	 * Configuration file, this does not add a prefix to the message
	 *
	 * @param reference the reference for the message
	 * @return the message (without prefix)
	 */
	public static String getMessage(String reference) {

		if (!messages.containsKey(reference)) {
			Bukkit.getLogger().warning("Could not find the message with the reference " + reference);
			return "";
		}

		String msg = messages.get(reference);

		return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(msg));
	}

	public static String getMessage(CommandSender sender, String reference) {
		try {
			String msg = getMessage(reference);
			if (sender instanceof Player && placeholderAPI) {
				msg = PlaceholderAPI.setPlaceholders((Player) sender, msg);
			}
			return ChatColor.translateAlternateColorCodes('&', msg);
		} catch (NullPointerException e) {
			Bukkit.getLogger().warning("Could not find the message with the reference " + reference);
			return "";
		}
	}

	public static Map<String, String> getMessages() {
		return messages;
	}

	/**
	 * @return the prefix for all messages Defaults to [BetterTeams] unless it is
	 *         changed by end user
	 */
	public static String getPrefix() {
		return prefix;
	}

	/**
	 * Used when you are sending a user a message instead of a message loaded from a
	 * file
	 *
	 * @param sender  the player who sent the command
	 * @param message the message to send to that user
	 */
	public static void sendFullMessage(CommandSender sender, String message) {
		sendFullMessage(sender, message, true);
	}

	/**
	 * Used when you are sending a user a message instead of a message loaded from a
	 * file
	 * 
	 * @param sender  the player who sent the command
	 * @param message The message to send to that user
	 * @param prefix  The prefix for that message
	 */
	public static void sendFullMessage(CommandSender sender, String message, boolean prefix) {
		if (prefix) {
			sender.sendMessage(prefix + message);
		} else {
			sender.sendMessage(message);
		}
	}

	public static File getFile() {
		return new File("plugins/BetterTeams/" + lang + ".yml");
	}

	public static FileConfiguration getDefaultMessages() {
		return defaultMessages;
	}

}
