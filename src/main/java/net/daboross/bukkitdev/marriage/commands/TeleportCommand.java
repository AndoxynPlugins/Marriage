package net.daboross.bukkitdev.marriage.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.marriage.MarriagePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends SubCommand {

    private MarriagePlugin plugin;

    public TeleportCommand(MarriagePlugin plugin) {
        super("tp", false, "marriage.teleport", "Teleports you to your partner.");
        this.plugin = plugin;
    }

    @Override
    public void runCommand(CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        Player senderPlayer = (Player) sender;
        String partner = plugin.getStorage().getPartner(sender.getName());
        if (partner == null) {
            sender.sendMessage("You aren't married.");
        }
        Player partnerPlayer = plugin.getServer().getPlayer(partner);
        if (partnerPlayer != null) {
            senderPlayer.teleport(partnerPlayer);
            sender.sendMessage(ColorList.REG + "You've teleported to " + ColorList.DATA + partner);
            partnerPlayer.sendMessage(ColorList.DATA + sender.getName() + ColorList.REG + " has teleported to you.");
        } else {
            sender.sendMessage(ColorList.ERR_ARGS + partner + ColorList.ERR + " isn't online.");
        }
    }
}