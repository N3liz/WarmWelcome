package me.neliz.warmwelcome.commands;

import me.neliz.warmwelcome.WarmWelcome;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeLeaveCommand implements CommandExecutor {

    private final WarmWelcome plugin;
    public FakeLeaveCommand(WarmWelcome plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            String leaveMessage = this.plugin.getConfig().getString("leave-message");
            String fakePrefix = this.plugin.getConfig().getString("fake-prefix");
            if(args.length == 0){
                leaveMessage = leaveMessage.replace("<player>", sender.getName());
            }else {
                String player = args[0];
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
        return true;
    }
}