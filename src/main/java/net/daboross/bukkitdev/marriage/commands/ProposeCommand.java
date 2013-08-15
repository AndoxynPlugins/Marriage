/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.CommandPreCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import net.daboross.bukkitdev.marriage.MarriageStorage;
import net.daboross.bukkitdev.marriage.conditions.MarriedCondition;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class ProposeCommand extends SubCommand {

    private MarriagePlugin plugin;

    public ProposeCommand(MarriagePlugin plugin) {
        super("propose", false, null, "Proposes to a given person");
        this.addCommandHelpCondition(new MarriedCondition(plugin.getStorage(), false));
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        MarriageStorage storage = plugin.getStorage();
        String partner = storage.getPartner(sender.getName());
        if (partner != null) {
            storage.setPvpEnabled(sender.getName(), true);
            storage.setPvpEnabled(partner, true);
            sender.sendMessage(ColorList.REG + "Partner PvP is now enabled.");
        } else {
            sender.sendMessage(ColorList.ERR + "You aren't married.");
        }
    }
}
