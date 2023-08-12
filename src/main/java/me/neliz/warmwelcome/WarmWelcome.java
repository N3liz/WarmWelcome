package me.neliz.warmwelcome;

import me.neliz.warmwelcome.commands.FakeJoinCommand;
import me.neliz.warmwelcome.commands.FakeLeaveCommand;
import me.neliz.warmwelcome.commands.MainCommand;
import me.neliz.warmwelcome.listeners.JoinLeaveListener;
import me.neliz.warmwelcome.commands.FakeFirstJoinCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarmWelcome extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.getConfig();
        getCommand("ww").setExecutor(new MainCommand(this));
        getCommand("warmwelcome").setExecutor(new MainCommand(this));
        getCommand("fakefirstjoin").setExecutor(new FakeFirstJoinCommand(this));
        getCommand("fakejoin").setExecutor(new FakeJoinCommand(this));
        getCommand("fakeleave").setExecutor(new FakeLeaveCommand(this));
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        getLogger().info("Plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin disabled successfully!");
    }


}