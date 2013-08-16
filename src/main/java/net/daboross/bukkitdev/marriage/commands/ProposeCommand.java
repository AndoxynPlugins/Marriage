package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.filters.ArgumentFilter;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import net.daboross.bukkitdev.marriage.MarriageStorage;
import net.daboross.bukkitdev.marriage.conditions.MarriedCondition;
import net.daboross.bukkitdev.marriage.conditions.MarriedFilter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class ProposeCommand extends SubCommand {

    private MarriagePlugin plugin;

    public ProposeCommand(MarriagePlugin plugin) {
        super("propose", false, null, "Proposes to a given person");
        this.addCommandPreCondition(new MarriedCondition(plugin.getStorage(), false));
        this.addArgumentNames("Your true love");
        this.addCommandFilter(new ArgumentFilter(
                ArgumentFilter.ArgumentCondition.GREATER_THAN, 0, ColorList.ERR + "You can't marry nobody."));
        this.addCommandFilter(new ArgumentFilter(
                ArgumentFilter.ArgumentCondition.LESS_THAN, 2, ColorList.ERR + "Please give one name. Sorry, no polygamy."));
        this.addCommandFilter(new MarriedFilter(plugin.getStorage(), false, ColorList.ERR + "You are already married. Sorry, no polygamy."));
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        MarriageStorage storage = plugin.getStorage();
        Player target = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().contains(subCommandArgs[0])
                    || p.getDisplayName().contains(subCommandArgs[0])) {
                target = p;
            }
        }
        if (target == null) {
            sender.sendMessage(ColorList.ERR + "Couldn't find " + ColorList.ERR_ARGS + subCommandArgs[0]);
        } else {
            plugin.getProposals().addProposal(sender.getName(), target.getName());
            sender.sendMessage(ColorList.REG + "You have proposed to " + ColorList.DATA + target.getName());
            Bukkit.broadcastMessage(String.format(ColorList.BROADCAST_NAME_FORMAT, "Priest") + ColorList.DATA + sender.getName() + ColorList.REG + " has proposed to " + ColorList.DATA + target.getName());
            target.sendMessage(ChatColor.BLACK + "#" + ColorList.DATA + ((Player) sender).getDisplayName() + ColorList.REG + " will you marry me?");
            target.sendMessage(ColorList.REG + "Type " + ColorList.CMD + "/ma accept" + ColorList.REG + " to accept.");
        }
    }
}
