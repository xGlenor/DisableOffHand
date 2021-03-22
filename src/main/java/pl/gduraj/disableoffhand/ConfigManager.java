package pl.gduraj.disableoffhand;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private DisableOffHand plugin;
    private FileConfiguration config;


    public ConfigManager(){
        this.plugin = DisableOffHand.getIntance();
        this.config = plugin.getConfig();
    }

    public String getString(String path){
        return ChatColor.translateAlternateColorCodes('&', this.config.getString(path));
    }


    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfig(){
        this.plugin.reloadConfig();
        this.config = this.plugin.getConfig();
    }



}
