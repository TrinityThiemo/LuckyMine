package eu.trinitydev.luckymine.events;

import eu.trinitydev.luckymine.Core;
import eu.trinitydev.luckymine.utils.SendMessage;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Random;

public class BlockBreak implements Listener {

    private Core plugin;
    private SendMessage message;

    public BlockBreak(Core instance) {
        this.plugin = instance;
        this.message = new SendMessage(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        // get the block
        String block = "" + event.getBlock().getType();
        String blockname = block.toLowerCase();


        // items.yml
        File items = new File(plugin.getDataFolder() + File.separator
                + "items.yml");
        FileConfiguration iconfig = YamlConfiguration.loadConfiguration(items);

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        List<String> worlds = plugin.getConfig().getStringList("disabled-worlds");
		
        if (worlds.contains(player.getWorld().getName())) {
            return;
        }
        for (String key : iconfig
                .getConfigurationSection("blocks." + blockname).getKeys(false)) {
            Random random = new Random();
            int chance = random.nextInt(100);
            String luckydrops = iconfig.getString("blocks." + blockname + "." + key);
            String[] drops = luckydrops.split("/");

            int ldrops = Integer.parseInt(drops[0]);
            int amount = Integer.parseInt(drops[1]);

            if (chance <= ldrops) {
                ItemStack dropped = new ItemStack(Material.getMaterial(key
                        .toUpperCase()), amount);
                String droppedname = "" + dropped.getType();
                event.getBlock()
                        .getLocation()
                        .getWorld()
                        .dropItemNaturally(event.getBlock().getLocation(),
                                dropped);
                message.sendMessage(player, "lucky-drop", droppedname.toLowerCase(), amount);
            }
        }

    }
}
