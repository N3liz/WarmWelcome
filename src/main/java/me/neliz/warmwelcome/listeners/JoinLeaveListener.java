package me.neliz.warmwelcome.listeners;

import me.neliz.warmwelcome.WarmWelcome;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.time.Duration;

public class JoinLeaveListener implements Listener {

    private final WarmWelcome plugin;
    public JoinLeaveListener(WarmWelcome plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        String joinMessage = this.plugin.getConfig().getString("join-message");
        String firstjoinMessage = this.plugin.getConfig().getString("first-join-message");
        if (joinMessage != null && firstjoinMessage != null && plugin.getConfig().getBoolean("join/leave management")) {
            joinMessage = joinMessage.replace("<player>", e.getPlayer().getDisplayName());
            firstjoinMessage = firstjoinMessage.replace("<player>", e.getPlayer().getDisplayName());
            if (player.hasPlayedBefore() || !plugin.getConfig().getBoolean("first-join")) {
                e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
            } else {
                e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', firstjoinMessage));
            }
            if (plugin.getConfig().getBoolean("motd management")) {
                for (int i = 0; i < plugin.getConfig().getList("motd-message").size(); i++) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("motd-message").get(i).toString()));
                }
            }
        }
        String mainTitle = this.plugin.getConfig().getString("main-title");
        String subTitle = this.plugin.getConfig().getString("sub-title");
        if (mainTitle != null && subTitle != null && plugin.getConfig().getBoolean("title management")) {
            mainTitle = mainTitle.replace("<player>", e.getPlayer().getDisplayName());
            subTitle = subTitle.replace("<player>", e.getPlayer().getDisplayName());
            final Component main = Component.text(ChatColor.translateAlternateColorCodes('&',mainTitle));
            final Component sub = Component.text(ChatColor.translateAlternateColorCodes('&',subTitle));
            final Title title = Title.title(main, sub, Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(3000), Duration.ofMillis(1000)));
            player.showTitle(title);
        }
        String headerTab = this.plugin.getConfig().getString("header-tab");
        String footerTab = this.plugin.getConfig().getString("footer-tab");
        if (headerTab != null && footerTab != null && plugin.getConfig().getBoolean("tab management")) {
            headerTab = headerTab.replace("<player>", e.getPlayer().getDisplayName());
            footerTab = footerTab.replace("<player>", e.getPlayer().getDisplayName());
            final Component header = Component.text(ChatColor.translateAlternateColorCodes('&',headerTab));
            final Component footer = Component.text(ChatColor.translateAlternateColorCodes('&',footerTab));
            player.sendPlayerListHeaderAndFooter(header, footer);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();

        String leaveMessage = this.plugin.getConfig().getString("leave-message");
        if (leaveMessage != null){
            leaveMessage = leaveMessage.replace("<player>", e.getPlayer().getDisplayName());
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
        }
    }
}