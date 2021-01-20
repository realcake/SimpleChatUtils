package net.comcast.seanmckenna.SimpleChatUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class Alias implements CommandExecutor {


    public SimpleChatUtils simpleChatUtils;
    public Alias(SimpleChatUtils simpleChatUtils){
        this.simpleChatUtils = simpleChatUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player == false)
            return false;

        Player player = (Player) sender;

        ArrayList<String> aliases = new ArrayList<>();

        try{
            aliases = simpleChatUtils.aliasMap.get(player);
        }catch (NullPointerException e){
            simpleChatUtils.aliasMap.put(player.getUniqueId(), aliases);
        }

        if(args.length == 0)
            return false;

        if(aliases == null)
            aliases = new ArrayList<>();

        aliases.add(args[0]);
        simpleChatUtils.aliasMap.put(player.getUniqueId(),aliases);
        return true;
    }
}
