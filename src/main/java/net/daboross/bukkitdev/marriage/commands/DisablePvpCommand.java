package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.filters.ArgumentFilter;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import net.daboross.bukkitdev.marriage.conditions.MarriedCondition;
import net.daboross.bukkitdev.marriage.conditions.MarriedFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DisablePvpCommand extends SubCommand {

    private MarriagePlugin plugin;

    public DisablePvpCommand(MarriagePlugin plugin) {
        super("disablepvp", false, null, "Disables partner pvp");
        this.addCommandFilter(ArgumentFilter.NO_ARGS);
        this.addCommandPreCondition(new MarriedCondition(plugin.getStorage(), true));
        this.addCommandFilter(new MarriedFilter(plugin.getStorage(), true, ColorList.ERR + "You aren't married."));
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        String partner = plugin.getStorage().getPartner(sender.getName());
        if (partner == null) {
            sender.sendMessage(ColorList.ERR + "You aren't married.");
            return;
        }
        plugin.getStorage().setPvpEnabled(sender.getName(), false);
        plugin.getStorage().setPvpEnabled(partner, false);
        sender.sendMessage(ColorList.REG + "Partner PvP is now disabled.");
    }
}