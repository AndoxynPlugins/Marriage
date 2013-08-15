package net.daboross.bukkitdev.marriage.listeners;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MarriageChatNotifier
        implements Listener {

    private MarriagePlugin marriagePlus;

    public MarriageChatNotifier(MarriagePlugin marriagePlus) {
        this.marriagePlus = marriagePlus;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        String partnerPlayerName = marriagePlus.getStorage().getPartner(event.getPlayer().getName());
        if (partnerPlayerName != null) {
            Player partnerPlayer = marriagePlus.getServer().getPlayer(partnerPlayerName);
            if (partnerPlayer != null) {
                partnerPlayer.sendMessage(ColorList.REG + "That was your partner joining.");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        String partnerPlayerName = marriagePlus.getStorage().getPartner(event.getPlayer().getName());
        if (partnerPlayerName != null) {
            Player partnerPlayer = marriagePlus.getServer().getPlayer(partnerPlayerName);
            if (partnerPlayer != null) {
                partnerPlayer.sendMessage(ColorList.REG + "That was your partner leaving.");
            }
        }
    }
}