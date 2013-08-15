package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.marriage.MarriageStorage;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import net.daboross.bukkitdev.marriage.conditions.MarriedCondition;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EnablePvpCommand extends SubCommand {

    private MarriagePlugin plugin;

    public EnablePvpCommand(MarriagePlugin plugin) {
        super("enablepvp", false, "marriage.enablepvp", "Enables partner PvP.");
        this.addCommandHelpCondition(new MarriedCondition(plugin.getStorage(), true));
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