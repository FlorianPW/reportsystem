package me.fluffix.utils;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import me.fluffix.ReportSystem;

public class $ {


    public static String LINE = "┃";
    public static String ARROW_RIGHT = "»";
    public static String ARROW_LEFT = "«";

    public static String PREFIX = "§8" + LINE + " §4§lR§ceport §8" + ARROW_RIGHT + "§7 ";

    public static void sendMessageToTeam(String message) {
        for (ProxiedPlayer proxiedPlayer : ReportSystem.getInstance().getProxy().getPlayers()) {
            if (proxiedPlayer.hasPermission("report.team.notification")) {
                proxiedPlayer.sendMessage(new TextComponent(PREFIX + message));
            }
        }
    }
    public static void sendMessageToTeam(TextComponent message) {
        for (ProxiedPlayer proxiedPlayer : ReportSystem.getInstance().getProxy().getPlayers()) {
            if (proxiedPlayer.hasPermission("report.team.notification")) {
                proxiedPlayer.sendMessage(message);
            }
        }
    }
}
