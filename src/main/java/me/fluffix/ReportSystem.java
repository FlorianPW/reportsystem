package me.fluffix;

import me.fluffix.commands.ReportCommand;
import me.fluffix.commands.ReportsCommand;
import me.fluffix.manager.MongoManager;
import me.fluffix.manager.ReportManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class ReportSystem extends Plugin {

    private ReportManager reportManager;

    private static ReportSystem instance;

    private MongoManager mongoManager;

    @Override
    public void onEnable() {
        instance = this;
        this.reportManager = new ReportManager();
        this.mongoManager = new MongoManager();
        fetchClasses();
    }

    private void fetchClasses() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerCommand(instance, new ReportCommand(instance));
        pluginManager.registerCommand(instance, new ReportsCommand(instance));
    }

    public static ReportSystem getInstance() {
        return instance;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }
}
