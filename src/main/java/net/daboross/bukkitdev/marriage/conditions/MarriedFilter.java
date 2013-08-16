package net.daboross.bukkitdev.marriage.conditions;

import net.daboross.bukkitdev.commandexecutorbase.CommandFilter;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.marriage.MarriageStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author daboross
 */
public class MarriedFilter implements CommandFilter {

    private final MarriageStorage storage;
    private final boolean married;
    private final String[] message;

    public MarriedFilter(MarriageStorage storage, boolean married, String... message) {
        this.storage = storage;
        this.married = married;
        this.message = message;
    }

    @Override
    public boolean canContinue(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return storage.isMarried(sender.getName()) == married;
    }

    @Override
    public String[] getDeniedMessage(CommandSender sender, Command baseCommand, SubCommand subCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs) {
        return message;
    }
}
