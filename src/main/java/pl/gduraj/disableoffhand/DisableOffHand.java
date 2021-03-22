package pl.gduraj.disableoffhand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisableOffHand extends JavaPlugin implements CommandExecutor {

    private static DisableOffHand intance;
    private ConfigManager configManager;
    private static int SLOT;


    @Override
    public void onEnable() {
        intance = this;
        configManager = new ConfigManager();
        saveDefaultConfig();
        SLOT = getConfigManager().getConfig().getInt("settings.slot");

        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("dohreload").setExecutor(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static DisableOffHand getIntance() {
        return intance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public int getSLOT() {
        return SLOT;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("disableoffhand.reload")){
            sender.sendMessage(getConfigManager().getString("messages.noPerm"));
            return true;
        }

        configManager.reloadConfig();
        SLOT = getConfigManager().getConfig().getInt("settings.slot");
        sender.sendMessage(getConfigManager().getString("messages.reload"));
        return true;
    }
}
