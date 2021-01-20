package net.comcast.seanmckenna.SimpleChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleChatUtils extends JavaPlugin{

    ConsoleCommandSender console;

    public HashMap<Player, Player> replyMap;
    public HashMap<UUID, ArrayList<String>> aliasMap;
    public ArrayList<Player> afkList;

    public static void main(String[] args) {

    }

    @Override
    public void onEnable(){
        console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.LIGHT_PURPLE + "SimpleChatUtils Enabled.");

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new MsgListener(this), this);


        this.getCommand("r").setExecutor(new Reply(this));
        this.getCommand("alias").setExecutor(new Alias(this));

        replyMap = new HashMap<>();
        aliasMap = new HashMap<>();
        afkList = new ArrayList<>();

        console.sendMessage(ChatColor.LIGHT_PURPLE + "Loading alias.txt ...");
        loadAliases();
        console.sendMessage(ChatColor.LIGHT_PURPLE + "Done, loaded " + aliasMap.size() + " aliases.");
    }

    private void loadAliases(){
        console.sendMessage(ChatColor.LIGHT_PURPLE + "PATH: " + this.getDataFolder().getAbsolutePath());
        File aliasFile = new File(this.getDataFolder().getAbsolutePath(), "aliases.txt");
        try{
            BufferedReader in = new BufferedReader(new FileReader(aliasFile));
            String line = null;
            while((line = in.readLine()) != null){
                String[] aliasLine = line.split(" ");
                UUID uuid = UUID.fromString(aliasLine[0]);
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

                ArrayList<String> aliases = new ArrayList<>();

                for(int i =1; i< aliasLine.length; i++){
                    aliases.add(aliasLine[i]);
                }

                aliasMap.put(player.getUniqueId(), aliases);
            }

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            console.sendMessage(ChatColor.LIGHT_PURPLE + "Alias file not found. Creating it.");
            createAliasFile(aliasFile);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void writeAliases(File file){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));

            for(HashMap.Entry<UUID, ArrayList<String>> entry : aliasMap.entrySet()){
                try {
                    UUID uuid = entry.getKey();
                    String line = uuid.toString();

                    for (String alias : entry.getValue()) {
                        line += " " + alias;
                    }
                    line += "\n";
                    out.write(line);

                }catch(NullPointerException e){

                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAliasFile(File file){
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        console.sendMessage(ChatColor.LIGHT_PURPLE + "Saving alias.txt ...");
        File aliasFile = new File(this.getDataFolder().getAbsolutePath(), "aliases.txt");
        writeAliases(aliasFile);
        console.sendMessage(ChatColor.LIGHT_PURPLE + "Done.");
    }
}
