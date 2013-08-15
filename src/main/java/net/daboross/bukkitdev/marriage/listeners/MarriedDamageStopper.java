package net.daboross.bukkitdev.marriage.listeners;

import net.daboross.bukkitdev.marriage.MarriagePlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MarriedDamageStopper implements Listener {

    private MarriagePlugin marriagePlugin;

    public MarriedDamageStopper(MarriagePlugin marriagePlugin) {
        this.marriagePlugin = marriagePlugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager(), defender = event.getEntity();
        if (!(attacker instanceof Player) || !(defender instanceof Player)) {
            return;
        }
        Player attackerPlayer = (Player) attacker;
        Player defenderPlayer = (Player) defender;
        if (marriagePlugin.getStorage().isPartner(attackerPlayer.getName(), defenderPlayer.getName())) {
            if (!marriagePlugin.getStorage().getPvPEnabled(attackerPlayer.getName())) {
                attackerPlayer.sendMessage(ChatColor.RED + "You have partner PvP disabled.");
                event.setCancelled(true);
            }
        }
    }
}
