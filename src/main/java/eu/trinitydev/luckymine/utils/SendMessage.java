package eu.trinitydev.luckymine.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.trinitydev.luckymine.Core;

public class SendMessage {
	
	private Core plugin;

	public SendMessage(Core instance) {
		this.plugin = instance;
	}

	public void sendMessage(Player player, String message, String item, int amount) {
		String messages = "";
		messages = plugin.getConfig().getString("messages." + message + ".message");
		messages = ChatColor.translateAlternateColorCodes('&', messages);
		String a = "" + amount;
		messages = messages.replaceAll("%amount%", a);
		
		if(amount > 1) {
			messages = messages.replaceAll("%item%", item + "s");
		} else {
			messages = messages.replaceAll("%item%", item);
		}
		
		if(plugin.getConfig().getBoolean("messages." + message + ".enabled")) {
		player.sendMessage(messages);
		}
	}

}
