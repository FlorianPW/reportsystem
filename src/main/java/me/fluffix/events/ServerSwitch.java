package me.fluffix.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import me.fluffix.ReportSystem;

public class ServerSwitch implements  Listener {

    public ServerSwitch() {
    }
    
    @EventHandler
    public void onPostLogin(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(ReportSystem.getInstance().getReportManager().getProcessingMap().containsKey(player)) {
            if(player.getServer().getInfo().getName().equalsIgnoreCase(ReportSystem.getInstance().getReportManager().getProcessingMap().get(player))) {
                player.chat("/spec");
            }
        }
    }
}
