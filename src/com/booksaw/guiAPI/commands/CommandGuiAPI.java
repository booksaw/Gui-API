package com.booksaw.guiAPI.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandGuiAPI implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {

		Sub cmd;
		if (args.length == 0) {
			cmd = new GuiAPIHelp();
			cmd.command(sender, args, label);

			return true;
		}

		switch (args[0].toLowerCase()) {
		case "save":
			cmd = new GuiApiSave();
			break;
		default:
			cmd = new GuiAPIHelp();
		}

		cmd.command(sender, args, label);
		return true;

	}

}
