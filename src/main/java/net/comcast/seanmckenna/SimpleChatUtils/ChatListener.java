package net.comcast.seanmckenna.SimpleChatUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener {

    public SimpleChatUtils simpleChatUtils;
    public ChatListener(SimpleChatUtils simpleChatUtils){
        this.simpleChatUtils = simpleChatUtils;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){

        String message = event.getMessage().toLowerCase();
        Player sender = event.getPlayer();

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        List<Player> mentionedPlayers = new ArrayList<>();
        for(Player player : players){
            if(message.contains(player.getDisplayName().toLowerCase()) || checkAliases(player, message)){
                mentionedPlayers.add(player);
            }
        }

        for(Player player: mentionedPlayers){
            event.getRecipients().remove(player);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "<"+sender.getDisplayName()+"> " + event.getMessage());
            player.playNote(player.getLocation(), Instrument.CHIME, Note.sharp(0,Note.Tone.A));
        }
    }

    private boolean checkAliases(Player player, String message){
        ArrayList<String> aliases = simpleChatUtils.aliasMap.get(player.getUniqueId());

        if(aliases == null)
            return false;

        for(String alias : aliases){
            if(message.contains(alias))
                return true;
        }

        return false;
    }
}
