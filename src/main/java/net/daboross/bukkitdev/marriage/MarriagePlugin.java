/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.marriage;

import net.daboross.bukkitdev.marriage.listeners.MarriageChatNotifier;
import net.daboross.bukkitdev.marriage.listeners.MarriedDamageStopper;
import net.daboross.bukkitdev.commandexecutorbase.CommandExecutorBase;
import net.daboross.bukkitdev.marriage.commands.DisablePvpCommand;
import net.daboross.bukkitdev.marriage.commands.EnablePvpCommand;
import net.daboross.bukkitdev.marriage.commands.TeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public class MarriagePlugin extends JavaPlugin implements Listener {

    private MarriageStorage flatFiles;
    private MarriageChatNotifier marriageChatNotifier;
    private MarriedDamageStopper marriedDamageStopper;

    @Override
    public void onEnable() {
        CommandExecutorBase base = new CommandExecutorBase(null);
        base.addSubCommand(new DisablePvpCommand(this));
        base.addSubCommand(new EnablePvpCommand(this));
        base.addSubCommand(new TeleportCommand(this));

        PluginCommand marriage = getCommand("marriage");
        if (marriage != null) {
            marriage.setExecutor(base);
        }
        flatFiles = new MarriageStorage(this);
        marriageChatNotifier = new MarriageChatNotifier(this);
        marriedDamageStopper = new MarriedDamageStopper(this);
        registerListeners();
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(marriageChatNotifier, this);
        pm.registerEvents(marriedDamageStopper, this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Marriage doesn't know about the command /" + cmd.getName());
        return true;
    }

    public MarriageChatNotifier getChat() {
        return marriageChatNotifier;
    }

    public MarriedDamageStopper getDmg() {
        return marriedDamageStopper;
    }

    public MarriageStorage getStorage() {
        return flatFiles;
    }
}
