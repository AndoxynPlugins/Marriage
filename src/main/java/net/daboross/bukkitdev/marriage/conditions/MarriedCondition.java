/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.marriage.conditions;

import net.daboross.bukkitdev.commandexecutorbase.CommandPreCondition;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.marriage.MarriageStorage;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class MarriedCondition implements CommandPreCondition {

    private final MarriageStorage storage;
    private final boolean value;

    public MarriedCondition(MarriageStorage storage, boolean value) {
        this.storage = storage;
        this.value = value;
    }

    @Override
    public boolean canContinue(CommandSender sender, SubCommand subCommand) {
        return storage.isMarried(sender.getName()) == value;
    }
}
