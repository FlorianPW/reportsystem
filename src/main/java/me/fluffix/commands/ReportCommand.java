package me.fluffix.commands;

import me.fluffix.utils.$;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import me.fluffix.ReportSystem;


public class ReportCommand extends Command {

    private ReportSystem instance;

    public ReportCommand(ReportSystem instance) {
        super("report");
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if (args.length > 0) {
            ProxiedPlayer toReport = instance.getProxy().getPlayer(args[0]);
            if (toReport == null) {
                player.sendMessage(new TextComponent($.PREFIX + "Der Spieler §8[§c" + args[0] + "§8] §7ist nicht online!"));
                return;
            }

            if (toReport.hasPermission("report.ignore")) {
                player.sendMessage(new TextComponent($.PREFIX + "Der Spieler kann nicht reportet werden!"));
                return;
            }
            if (args.length == 2) {
                int id = Integer.parseInt(args[1]);
                String reason = ReportSystem.getInstance().getReportManager().getReason(id);
                if (reason == null) {
                    player.sendMessage(new TextComponent($.PREFIX + "Der Grund §8[§c" + id + "§8] §7ist nicht verfügbar!"));
                    player.sendMessage(new TextComponent());
                    for (int tmpID : ReportSystem.getInstance().getReportManager().getReasons().keySet()) {
                        player.sendMessage(new TextComponent($.PREFIX + "§c§l" + tmpID + ". §8" + $.ARROW_RIGHT + "§c " + ReportSystem.getInstance().getReportManager().getReason(tmpID)));
                    }
                }

                ReportSystem.getInstance().getReportManager().createReport(player, toReport, reason);

            } else {
                $.sendMessageToTeam("§8§m--------*----§8[ §4§lR§ceport §8]§8§m---*--------");
                for (int tmpID : ReportSystem.getInstance().getReportManager().getReasons().keySet()) {
                    TextComponent comp = new TextComponent();
                    comp.setText($.PREFIX + " §8[§c" + ReportSystem.getInstance().getReportManager().getReason(tmpID) + "§8] " + $.ARROW_LEFT);
                    comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + toReport.getName() + " " + tmpID));
                    comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[0]));
                    $.sendMessageToTeam(comp);
                }
                $.sendMessageToTeam("§8§m--------*----§8[ §4§lR§ceport §8]§8§m---*--------");
            }

        } else {
            player.sendMessage(new TextComponent($.PREFIX + "Benutze /report <Name>"));
        }

    }
}
