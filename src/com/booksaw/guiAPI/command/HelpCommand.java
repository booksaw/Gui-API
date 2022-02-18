package com.booksaw.guiAPI.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.booksaw.guiAPI.message.MessageManager;

import net.md_5.bungee.api.ChatColor;

public class HelpCommand extends SubCommand {

	public static ChatColor prefix, description;
	private static boolean fullyCustom = false;
	final ParentCommand command;
	private final JavaPlugin plugin;

	public HelpCommand(ParentCommand command, ChatColor helpCommandColor, ChatColor helpDescriptionColor, JavaPlugin plugin) {
		this.command = command;
		prefix = helpCommandColor;
		description = helpDescriptionColor;
		this.plugin = plugin;

	}

	public static void setupHelp(boolean fullyCustom) {
		HelpCommand.fullyCustom = fullyCustom;
	}

	@Override
	public CommandResponse onCommand(CommandSender sender, String label, String[] args) {

		if (fullyCustom) {
			fullyCustom(sender, label);
			return null;
		}

		// Send specific help message if command found
		if (args.length != 0 && command.getSubCommands().containsKey(args[0])) {
			sender.sendMessage(
					createHelpMessage(label, args[0] + " " + command.getSubCommands().get(args[0]).getArguments(),
							command.getSubCommands().get(args[0]).getHelpMessage()));
			return null;
		}

		for (Entry<String, SubCommand> subCommand : command.getSubCommands().entrySet()) {
			if (sender.hasPermission("betterTeams." + subCommand.getValue().getNode())) {
				sender.sendMessage(
						createHelpMessage(label, subCommand.getKey() + " " + subCommand.getValue().getArguments(),
								subCommand.getValue().getHelpMessage()));
			}
		}

		return null;
	}

	/**
	 * Used to send a fully custom help message which is stored in a file
	 *
	 * @param sender the CommandSender that called the help message
	 * @param label  the label for the message (the base command for example for
	 *               /teamadmin it could be /teama as well)
	 */
	public void fullyCustom(CommandSender sender, String label) {
		File f = new File(plugin.getDataFolder() + File.separator + command.getCommand() + ".txt");

		if (!f.exists()) {
			try {
				f.createNewFile();

				PrintWriter writer = new PrintWriter(f);
				for (Entry<String, SubCommand> sub : command.getSubCommands().entrySet()) {
					writer.println("&b/" + label + " " + sub.getKey() + " " + sub.getValue().getArguments() + "&f - &6"
							+ sub.getValue().getHelpMessage());
				}
				writer.close();

			} catch (Exception e) {
				Bukkit.getLogger().log(Level.SEVERE,

						"Could not use fully custom help messages, inform booksaw (this should never happen)");
				sender.sendMessage(ChatColor.RED + "Something went wrong, inform your server admins");
				e.printStackTrace();
				fullyCustom = false;
			}
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				sender.sendMessage(
						MessageManager.getPrefix() + org.bukkit.ChatColor.translateAlternateColorCodes('&', line));
			}
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE,
					"Could not use fully custom help messages, inform booksaw (this should never happen)");
			sender.sendMessage(ChatColor.RED + "Something went wrong, inform your server admins");
			e.printStackTrace();
			fullyCustom = false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.SEVERE,
							"Could not use fully custom help messages, inform booksaw (this should never happen)");
					sender.sendMessage(ChatColor.RED + "Something went wrong, inform your server admins");
				}
			}
		}
	}

	/**
	 * Used to create a formatted help message to explain what a command does to the
	 * user
	 *
	 * @param label       the base command
	 * @param commandPath the rest of the command (i.e. help [param])
	 * @param description the description of the command
	 * @return the created message relating to that command
	 */
	public String createHelpMessage(String label, String commandPath, String description) {
		return prefix + "/" + label + " " + commandPath + ChatColor.WHITE + " - " + HelpCommand.description
				+ description;
	}

	@Override
	public String getCommand() {
		return "help";
	}

	@Override
	public String getNode() {
		return "";
	}

	@Override
	public String getHelp() {
		return "View this help page";
	}

	@Override
	public String getArguments() {
		return "";
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public void onTabComplete(List<String> options, CommandSender sender, String label, String[] args) {

	}

	@Override
	public int getMaximumArguments() {
		return 0;
	}

}
