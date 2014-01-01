/*
 * Copyright (C) 2013-2014 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.CommandFilter;
import net.daboross.bukkitdev.commandexecutorbase.CommandPreCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.filters.ArgumentFilter;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import net.daboross.bukkitdev.marriage.conditions.MarriedCondition;
import net.daboross.bukkitdev.marriage.conditions.MarriedFilter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class AcceptCommand extends SubCommand implements CommandPreCondition, CommandFilter {

    private MarriagePlugin plugin;

    public AcceptCommand(MarriagePlugin plugin) {
        super("accept", false, null, "Accepts a proposal");
        this.addCommandPreCondition(new MarriedCondition(plugin.getStorage(), false));
        this.addCommandPreCondition(this);
        this.addArgumentNames("Your true love");
        this.addCommandFilter(ArgumentFilter.NO_ARGS);
        this.addCommandFilter(new MarriedFilter(plugin.getStorage(), false, ColorList.ERR + "You are already married. Sorry, no polygamy."));
        this.addCommandFilter(this);
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        String proposal = plugin.getProposals().getLastProposal(sender.getName());
        Player proposalPlayer = Bukkit.getPlayer(proposal);
        if (proposalPlayer == null) {
            sender.sendMessage(ColorList.ERR + "You're suiter has left!");
            return;
        }
        plugin.getProposals().removeProposal(sender.getName());
        proposalPlayer.sendMessage(ColorList.DATA + sender.getName() + ColorList.REG + " has accepted your proposal!");
        sender.sendMessage(ColorList.REG + "You have accepted " + ColorList.DATA + sender.getName() + ColorList.REG + "'s proposal!");
        Bukkit.broadcastMessage(String.format(ColorList.BROADCAST_NAME_FORMAT, "Priest") + ColorList.DATA + proposal + ColorList.REG + " is now married to " + ColorList.DATA + sender.getName() + ColorList.DATA + "!");
        plugin.getStorage().addMarriage(sender.getName(), proposal);
    }

    @Override
    public boolean canContinue(CommandSender sender, SubCommand subCommand) {
        return plugin.getProposals().getLastProposal(sender.getName()) != null;
    }

    @Override
    public boolean canContinue(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return plugin.getProposals().getLastProposal(sender.getName()) != null;
    }

    @Override
    public String[] getDeniedMessage(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return new String[]{"No one has proposed to you since you logged in :(."};
    }
}
