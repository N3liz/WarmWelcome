package me.neliz.warmwelcome.commands;

import me.neliz.warmwelcome.WarmWelcome;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeFirstJoinCommand implements CommandExecutor {

    private final WarmWelcome plugin;
    public FakeFirstJoinCommand(WarmWelcome plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            String firstjoinMessage = this.plugin.getConfig().getString("first-join-message");
            String fakePrefix = this.plugin.getConfig().getString("fake-prefix");
            if(args.length == 0){
                firstjoinMessage = firstjoinMessage.replace("<player>", sender.getName());
            }else{
                String player = args[0];
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
        return true;
    }
}