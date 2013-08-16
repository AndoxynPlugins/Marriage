package net.daboross.bukkitdev.marriage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MarriageStorage {

    private final MarriagePlugin plugin;
    private File saveFile;
    private YamlConfiguration save;

    public MarriageStorage(MarriagePlugin plugin) {
        this.plugin = plugin;
        save = new YamlConfiguration();
        save.options().pathSeparator('|');
        save.options().indent(2);
        saveFile = new File(plugin.getDataFolder(), "save.yml");
        if (saveFile.exists()) {
            try {
                save.load(saveFile);
            } catch (IOException | InvalidConfigurationException ex) {
                plugin.getLogger().log(Level.SEVERE, "Error loading save file", ex);
            }
        }
    }

    public void save() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        try {
            save.save(saveFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error saving", ex);
        }
    }

    public boolean getPvPEnabled(String playerName) {
        return save.getBoolean(playerName + "|pvpEnabled");
    }

    public void setPvpEnabled(String playerName, boolean enabled) {
        save.set(playerName + "|pvpEnabled", enabled);
    }

    public boolean isMarried(String playerName) {
        return save.getBoolean(playerName + "|isMarried");
    }

    public void removeMarriage(String playerName) {
        save.set(playerName + "|isMarried", false);
        save.set(playerName + "|marriedTo", null);
        save.set(playerName + "|marriedBy", null);
        save.set(playerName + "|marriedTime", null);
//        save.set(playerName + "|home", null);
//        save.set(playerName + "|home|world", null);
//        save.set(playerName + "|home|x", null);
//        save.set(playerName + "|home|y", null);
//        save.set(playerName + "|home|z", null);
    }

    public void addMarriage(String player1, String player2) {
        setMarried(player1, player2);
        setMarried(player2, player1);
    }

    private void setMarried(String playerName, String partner) {
        save.set(playerName + "|isMarried", true);
        save.set(playerName + "|marriedTo", partner);
        save.set(playerName + "|marriedTime", System.currentTimeMillis());
        save.set(playerName + "|pvpEnabled", false);
    }

//    public void setMarriedHome(String playerName, Location homeLocation) {
//        if (isMarried(playerName)) {
//            save.set(playerName + "|home|world", homeLocation.getWorld().getName());
//            save.set(playerName + "|home|x", homeLocation.getX());
//            save.set(playerName + "|home|y", homeLocation.getY());
//            save.set(playerName + "|home|z", homeLocation.getZ());
//        }
//    }
//    public Location getMarriedHome(String playerName) {
//        int x = save.getInt(playerName + "|home|x"),
//                y = save.getInt(playerName + "|home|y"),
//                z = save.getInt(playerName + "|home|z");
//        String world = save.getString(playerName + "|home|world");
//        return new Location(Bukkit.getWorld(world), x, y, z);
//    }
    public String getPartner(String playerName) {
        return save.getString(playerName + "|marriedTo");
    }

    public boolean isPartner(String player1, String player2) {
        return player2.equals(getPartner(player1));
    }
}