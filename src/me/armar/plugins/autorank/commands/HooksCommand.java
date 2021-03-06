package me.armar.plugins.autorank.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.commands.manager.AutorankCommand;
import me.armar.plugins.autorank.hooks.DependencyManager.dependency;
import me.staartvin.statz.hooks.Dependency;
import net.md_5.bungee.api.ChatColor;

public class HooksCommand extends AutorankCommand {

	private final Autorank plugin;

	public HooksCommand(final Autorank instance) {
		this.setUsage("/ar hooks");
		this.setDesc("Shows a list of hookable plugins for Autorank");
		this.setPermission("autorank.hooks");

		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

		if (!plugin.getDependencyManager().getDependency(dependency.STATZ).isAvailable()) {
			sender.sendMessage(ChatColor.RED + "Cannot show dependencies as Statz is not installed");
			return true;
		}

		sender.sendMessage(ChatColor.GOLD + "Autorank Hooks:");

		for (final Dependency dep : Dependency.values()) {
			// There is no dependency handler for Autorank

			final me.staartvin.statz.hooks.DependencyHandler handler = plugin.getDependencyManager()
					.getDependencyHandler(dep);

			final StringBuilder message = new StringBuilder(ChatColor.GRAY + dep.toString() + ": " + ChatColor.RESET);

			if (handler.isAvailable()) {
				message.append(ChatColor.GREEN + "AVAILABLE");
			} else {
				message.append(ChatColor.RED + "NOT AVAILABLE");
			}

			sender.sendMessage(message.toString());
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see me.armar.plugins.autorank.commands.manager.AutorankCommand#onTabComplete(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String commandLabel,
			final String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
