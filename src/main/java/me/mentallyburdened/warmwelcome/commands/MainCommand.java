package me.mentallyburdened.warmwelcome.commands;

import me.mentallyburdened.warmwelcome.WarmWelcome;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private final WarmWelcome plugin;

    public MainCommand(WarmWelcome plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String prefix = this.plugin.getConfig().getString("plugin prefix");
            String fakePrefix = this.plugin.getConfig().getString("fake prefix");
            if (args.length == 0) {
                p.sendMessage("+--------------------------------------+");
                p.sendMessage("/warmwelcome - shows a list of all commands");
                p.sendMessage("/warmwelcome reload - reloads the plugin");
                p.sendMessage("/warmwelcome fakefirstjoin - sends a fake first join message");
                p.sendMessage("/warmwelcome fakejoin - sends a fake join message");
                p.sendMessage("/warmwelcome fakeleave - sends a fake leave message");
                p.sendMessage("+--------------------------------------+");
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("warmwelcome.reload")) {
                    plugin.reloadConfig();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "The config.yml has been reloaded "));
                    String headerTab = this.plugin.getConfig().getString("header-tab");
                    String footerTab = this.plugin.getConfig().getString("footer-tab");
                    if (headerTab != null && footerTab != null && plugin.getConfig().getBoolean("tab management")) {
                        headerTab = headerTab.replace("<player>", p.getPlayer().getDisplayName());
                        footerTab = footerTab.replace("<player>", p.getPlayer().getDisplayName());
                        final Component header = Component.text(ChatColor.translateAlternateColorCodes('&',headerTab));
                        final Component footer = Component.text(ChatColor.translateAlternateColorCodes('&',footerTab));
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendPlayerListHeaderAndFooter(header, footer);
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "The tablist has been reloaded "));
                    }
                }
            } else if (args[0].equalsIgnoreCase("fakefirstjoin")) {
                if (p.hasPermission("warmwelcome.fake.firstjoin")) {
                    String firstjoinMessage = this.plugin.getConfig().getString("first-join-message");
                    if (args.length == 1) {
                        firstjoinMessage = firstjoinMessage.replace("<player>", sender.getName());
                    } else {
                        String player = args[1];
                        firstjoinMessage = firstjoinMessage.replace("<player>", player);
                    }
                    for (Player playerr : Bukkit.getOnlinePlayers()) {
                        if (playerr.hasPermission("warmwelcome.fake.bypass")){
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', fakePrefix + firstjoinMessage));
                        } else {
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', firstjoinMessage));
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("fakejoin")) {
                if (p.hasPermission("warmwelcome.fake.join")) {
                    String joinMessage = this.plugin.getConfig().getString("join-message");
                    if(args.length == 1){
                        joinMessage = joinMessage.replace("<player>", sender.getName());
                    }else{
                        String player = args[1];
                        joinMessage = joinMessage.replace("<player>", player);
                    }
                    for (Player playerr : Bukkit.getOnlinePlayers()) {
                        if (playerr.hasPermission("warmwelcome.fake.bypass")){
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', fakePrefix + joinMessage));
                        } else {
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
                        }
                    }                }
            } else if (args[0].equalsIgnoreCase("fakeleave")) {
                if (p.hasPermission("warmwelcome.fake.leave")) {
                    String leaveMessage = this.plugin.getConfig().getString("leave-message");
                    if(args.length == 1){
                        leaveMessage = leaveMessage.replace("<player>", sender.getName());
                    }else {
                        String player = args[1];
                        leaveMessage = leaveMessage.replace("<player>", player);
                    }
                    for (Player playerr : Bukkit.getOnlinePlayers()) {
                        if (playerr.hasPermission("warmwelcome.fake.bypass")){
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', fakePrefix + leaveMessage));
                        } else {
                            playerr.sendMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
                        }
                    }
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Incorrect use of command"));
            }
        }
        return true;
    }
}