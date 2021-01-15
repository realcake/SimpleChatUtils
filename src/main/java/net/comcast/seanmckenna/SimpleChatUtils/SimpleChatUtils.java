package net.comcast.seanmckenna.SimpleChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class SimpleChatUtils extends JavaPlugin{

    ConsoleCommandSender console;

    public HashMap<Player, Player> replyMap;

    public static void main(String[] args) {

    }

    @Override
    public void onEnable(){
        console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.LIGHT_PURPLE + "SimpleChatUtils Enabled.");

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new MsgListener(this), this);

        this.getCommand("r").setExecutor(new Reply(this));

        replyMap = new HashMap<>();
    }

    @Override
    public void onDisable(){}
}
