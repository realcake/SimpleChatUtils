package net.comcast.seanmckenna.SimpleChatUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reply implements CommandExecutor {

    public SimpleChatUtils simpleChatUtils;
    public Reply(SimpleChatUtils simpleChatUtils){
        this.simpleChatUtils = simpleChatUtils;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args){
        Player lastSender;

        try{
            lastSender = simpleChatUtils.replyMap.get(commandSender);
        }catch(NullPointerException e){ //Nobody has sent them a pm.
            return false;
        }

        String message = "";

        for(String part : args){
            message += " " + part;
        }

        simpleChatUtils.replyMap.put(lastSender,(Player)commandSender);

        lastSender.sendMessage(""+ChatColor.GRAY+ChatColor.ITALIC +commandSender.getName()+" whispers to you:" + message);
        commandSender.sendMessage(""+ChatColor.GRAY+ChatColor.ITALIC + "You whisper to " + lastSender.getDisplayName()+":" + message);
        return true;
    }
}