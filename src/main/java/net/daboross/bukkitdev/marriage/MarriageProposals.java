/*
 * Copyright (C) 2013-2014 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.marriage;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author daboross
 */
public class MarriageProposals implements Listener {

    private final Map<String, String> proposals = new HashMap<>();

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        proposals.remove(evt.getPlayer().getName());
    }

    public void addProposal(String playerProposing, String target) {
        proposals.put(target, playerProposing);
    }

    public String getLastProposal(String target) {
        return proposals.get(target);
    }

    public void removeProposal(String target) {
        proposals.remove(target);
    }
}
