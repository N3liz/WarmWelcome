package me.mentallyburdened.warmwelcome.commands;

import me.mentallyburdened.warmwelcome.WarmWelcome;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeJoinCommand implements CommandExecutor {

    private final WarmWelcome plugin;
    public FakeJoinCommand(WarmWelcome plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String joinMessage = this.plugin.getConfig().getString("join-message");
            String fakePrefix = this.plugin.getConfig().getString("fake prefix");
            if (args.length == 0) {
                joinMessage = joinMessage.replace("<player>", sender.getName());
            } else {
                String player = args[0];
                joinMessage = joinMessage.replace("<player>", player);
            }
            for (Player playerr : Bukkit.getOnlinePlayers()) {
                if (playerr.hasPermission("warmwelcome.fake.bypass")) {
                    playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', fakePrefix + joinMessage));
                } else {
                    playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
                }
            }
        }
        return true;
    }
}
