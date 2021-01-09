package me.fluffix.manager;

import com.mongodb.client.MongoCollection;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import me.fluffix.ReportSystem;
import me.fluffix.utils.$;
import me.fluffix.utils.ReportState;
import org.bson.Document;

import java.util.HashMap;
import java.util.Random;

public class ReportManager {

    private HashMap<ProxiedPlayer, String> processingMap;

    private HashMap<Integer, String> reasons;

    public ReportManager() {
        processingMap = new HashMap<ProxiedPlayer, String>();
        loadReasons();
    }

    public void loadReasons() {
        reasons = new HashMap<Integer, String>();
        reasons.put(1, "Hacking");
        reasons.put(2, "Spamming");
        reasons.put(3, "Teaming");
        reasons.put(4, "Beleidigung");
    }

    public void createReport(ProxiedPlayer reporter, ProxiedPlayer reported, String reason) {
        int reportId = generateReportID();

        $.sendMessageToTeam("§8§m--------*----§8[ §4§lR§ceport §8]§8§m---*--------");
        $.sendMessageToTeam("");
        $.sendMessageToTeam(" §7ID: §c#Q" + reportId);
        $.sendMessageToTeam(" §7Ersteller: §c" + reporter.getName());
        $.sendMessageToTeam(" §7Gegen: §c" + reported.getName());
        $.sendMessageToTeam(" §7Grund: §c" + reason);
        $.sendMessageToTeam(" §7Server: §c" + reported.getServer().getInfo().getName());

        TextComponent accept = new TextComponent();
        accept.setText($.PREFIX + " §aBearbeiten");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + reported.getServer().getInfo().getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§anehme den Report an!").create()));
        $.sendMessageToTeam(accept);

        $.sendMessageToTeam("");
        $.sendMessageToTeam("§8§m--------*----§8[ §4§lR§ceport §8]§8§m---*--------");

        openReports().insertOne(new Document("_id", reportId)
                .append("reporter", reporter.getUniqueId().toString())
                .append("reported", reported.getUniqueId().toString())
                .append("reason", reason).append("server", reported.getServer().getInfo().getName())
                .append("state", ReportState.OPEN.toString()));


        reporter.sendMessage(new TextComponent($.PREFIX + "Dein Report gegen §c" + reported.getName() + " §7wurde erstellt!"));
    }

    public MongoCollection<Document> openReports() {
        return ReportSystem.getInstance().getMongoManager().getMongoDatabase().getCollection("openReports");
    }

    public MongoCollection<Document> closedReports() {
        return ReportSystem.getInstance().getMongoManager().getMongoDatabase().getCollection("closedReports");
    }

    public int generateReportID() {
        int i = newReportId();

        while (isInDataBase(i))
            i = newReportId();

        return i;
    }

    private boolean isInDataBase(int id) {
        boolean is = false;
        for (Document document : openReports().find()) {
            if (document.getInteger("_id") == id)
                is = true;
        }

        if (!is) {
            for (Document document : closedReports().find()) {
                if (document.getInteger("_id") == id)
                    is = true;
            }
        }

        return is;
    }

    private int newReportId() {
        return new Random().nextInt(5) * new Random().nextInt(25);
    }

    public void acceptReport(ProxiedPlayer player, int reportID) {
        processingMap.put(player, "Server");
        ProxiedPlayer reporter = null;

        reporter.sendMessage(new TextComponent($.PREFIX + "Dein Report wird jetzt bearbeitet!"));
    }

    public void next(ProxiedPlayer player) {
        int id = 0; //bekomme neue id

        acceptReport(player, id);
    }

    public void close(ProxiedPlayer player) {
        if (!processingMap.containsKey(player)) {
            return;
        }
        processingMap.remove(player);

        ProxiedPlayer reporter = null;

        reporter.sendMessage(new TextComponent($.PREFIX + "Dein Report wurde bearbeitet!"));
        reporter.sendMessage(new TextComponent($.PREFIX + "Vielen Dank für deine Mithilfe"));
    }

    public void updateReport(int reportID, ReportState state) {

    }

    public HashMap<Integer, String> getOpenReports() {
        HashMap<Integer, String> tmp = new HashMap<Integer, String>();

        return tmp;
    }

    public String getReason(int id) {
        return (reasons.containsKey(id) ? reasons.get(id) : null);
    }

    public HashMap<Integer, String> getReasons() {
        return reasons;
    }

    public HashMap<ProxiedPlayer, String> getProcessingMap() {
        return processingMap;
    }
}
