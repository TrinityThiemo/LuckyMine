package eu.trinitydev.luckymine;

import eu.trinitydev.luckymine.events.BlockBreak;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Core extends JavaPlugin {

    public void onEnable() {
        initConfig();
        initEvents();
    }

    private void initConfig() {
        saveDefaultConfig();
        try {
            createItems();
        } catch (IOException e) {
            getLogger()
                    .info("LuckyMine > Error while creating items.yml, try a server restart");
            e.printStackTrace();
        }
    }

    private void createItems() throws IOException {
        File items = new File(getDataFolder() + File.separator + "items.yml");
        if (!items.exists()) {
            this.saveResource("items.yml", false);
        }

    }

    private void initEvents() {
        getServer().getPluginManager().registerEvents(new BlockBreak(this),
                this);

    }

}
