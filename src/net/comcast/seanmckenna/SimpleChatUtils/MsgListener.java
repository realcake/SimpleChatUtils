package net.comcast.seanmckenna.SimpleChatUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MsgListener implements Listener {

    public SimpleChatUtils simpleChatUtils;
    public MsgListener(SimpleChatUtils simpleChatUtils){
        this.simpleChatUtils = simpleChatUtils;
    }

    @EventHandler
    public void onMsg(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        if (event.getMessage().startsWith("/msg")) {
            if (command.length() < 3) {
                return;
            }

            Player receiverPlayer = Bukkit.getPlayer(command.split(" ")[1]);
            Player senderPlayer = event.getPlayer();

            simpleChatUtils.replyMap.put(receiverPlayer, senderPlayer);
        }
    }
}
