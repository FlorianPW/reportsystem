package me.fluffix.commands;

import me.fluffix.utils.$;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import me.fluffix.ReportSystem;

public class ReportsCommand extends Command {

    public ReportSystem instance;

    public ReportsCommand(ReportSystem instance) {
        super("reports");
        this.instance = instance;
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (player.hasPermission("report.command.reports")) {
            if (args.length == 0) {
                for (int id : ReportSystem.getInstance().getReportManager().getOpenReports().keySet()) {
                    player.sendMessage(new TextComponent($.PREFIX + "§c§l" + id + "§c. §8" + $.ARROW_RIGHT + ReportSystem.getInstance().getReportManager().getOpenReports().get(id)));
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("next")) {
                    ReportSystem.getInstance().getReportManager().next(player);
                } else if (args[0].equalsIgnoreCase("close")) {
                    ReportSystem.getInstance().getReportManager().close(player);
                }
            }else {
                player.sendMessage(new TextComponent($.PREFIX + "Benutze /reports <next / close>"));
            }
        }
    }
}
