package net.thewebgamer.bungee.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import org.pircbotx.PircBotX;

public class BungeeListener extends Command implements Listener {

	public DCBungee instance;
	public List<String> input = new ArrayList<String>();
	private PircBotX irc;

	public BungeeListener(DCBungee Nethad) {
		super("netchat", "netchat.use", new String[0]);
		this.instance = Nethad;
		this.instance.getProxy().getPluginManager().registerListener(this.instance, this);
		this.irc = this.instance.getBot();
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender s, String[] args) {
		if (!this.input.contains(s.getName())) {
			this.input.add(s.getName());
			//s.sendMessage(ChatColor.DARK_GREEN + "Toggled, OFF!");
			return;
		}

		this.input.remove(s.getName());
		//s.sendMessage(ChatColor.DARK_RED + "Toggled, ON!");
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerChat(ChatEvent e) {
		if (e.isCommand()) {
			return;
		}

		if ((e.getSender() instanceof ProxiedPlayer)) {
			ProxiedPlayer s = (ProxiedPlayer) e.getSender();
			if (instance.getConfig().serverChat) {
				e.setCancelled(true);
				for (ProxiedPlayer pl : this.instance.getProxy().getPlayers()) {
					if (pl instanceof ProxiedPlayer) {
						if (instance.getConfig().gameShowConnectedServer) {
							pl.sendMessage(ChatColor.DARK_GRAY
									+ "["
									+ ChatColor.AQUA
									+ s.getServer().getInfo().getName()
									.toUpperCase() + ChatColor.DARK_GRAY
									+ "] " + ChatColor.GOLD + s.getName() + ": " 
									+ ChatColor.WHITE + e.getMessage());
						}else{
							pl.sendMessage(ChatColor.GOLD + s.getName() + ": " 
									+ ChatColor.WHITE + e.getMessage());
						}
					}
				}
			}
			if (instance.getConfig().ircChat) {
				e.setCancelled(true);
				if (instance.getConfig().ircShowConnectedServer) {
					irc.sendIRC().message(instance.getConfig().channel,
							"[" + s.getServer().getInfo().getName() .toUpperCase() + "] " + 
									"<" + s.getDisplayName() + "> " + e.getMessage());
				} else {
					irc.sendIRC().message(instance.getConfig().channel,
							"<" + s.getDisplayName() + "> " + e.getMessage());
				}
			}
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin(LoginEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (instance.getConfig().ircChat) {
			irc.sendIRC().message(instance.getConfig().channel,
					e.getConnection().getName() + " has joined the server");
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent e) {
		if (instance.getConfig().ircChat) {
			irc.sendIRC().message(instance.getConfig().channel,
					e.getPlayer().getDisplayName() + " has left the server");
		}
	}
}
