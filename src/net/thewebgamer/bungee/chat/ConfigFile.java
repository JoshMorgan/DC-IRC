package net.thewebgamer.bungee.chat;

import java.io.File;

public class ConfigFile {
    private static String configpath = File.separator+"plugins"+File.separator+"DecisiveCraft_IRC"+File.separator+"settings.yml";
    public Config c = new Config(configpath);
    public String host = c.getString("IRC Host", "irc.esper.net");
    public int port = c.getInt("Port", 6667);
    public String nick = c.getString("IRC Bot Nickname", "DCBot");
    public String pass = c.getString("IRC Bot Password", "Password123");
    public String channel = c.getString("Channel", "#DecisiveCraft");
    public boolean serverChat = c.getBoolean("Cross Server Chat", true);
	public boolean ircChat = c.getBoolean("IRC Chat", true);
	public boolean ircShowConnectedServer = c.getBoolean("Show server that player is connected to in IRC", true);
	public boolean gameShowConnectedServer = c.getBoolean("Show server that player is connected to in game", true);
}