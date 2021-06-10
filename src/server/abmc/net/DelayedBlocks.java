package server.abmc.net;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DelayedBlocks extends JavaPlugin implements Listener {

    private HashMap<String, Integer> igniteCounter = new HashMap<>();
    
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }
    

    private List<String> damagetheseitems = Arrays.asList("DIAMOND_SWORD", "DIAMOND_CHESTPLATE");

    @EventHandler
    public void ontest(PlayerItemDamageEvent e){
        if (!damagetheseitems.contains(e.getItem().getType().toString())){
            e.setCancelled(true);
        } else {
            ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
            if (hand != null){ // remove if doesn't work
                short durability = hand.getDurability();
                hand.setDurability((short)(durability + 5));
            }
        }
    }
    

    @EventHandler
    public void onTntDamage(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
            e.setDamage(e.getDamage()/2);
        }
    }

    @EventHandler
    public void onIgnite(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
            if (hand != null) {
                if (hand.getType().toString().equalsIgnoreCase("FLINT_AND_STEEL") && !e.getPlayer().isOp()) {
                    if (igniteCounter.get(e.getPlayer().getName()) == null) {
                        igniteCounter.put(e.getPlayer().getName(), 1);
                    } else if (igniteCounter.get(e.getPlayer().getName()) >= 10) {
                        e.getPlayer().getInventory().setItemInMainHand(null);
                        e.setCancelled(true);
                        igniteCounter.put(e.getPlayer().getName(), 0);
                    } else {
                        int increment = igniteCounter.get(e.getPlayer().getName());
                        igniteCounter.put(e.getPlayer().getName(), increment + 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getBlock().getType().toString().equalsIgnoreCase("OBSIDIAN") && !e.getBlock().getType().toString().equalsIgnoreCase("TNT") && !e.getPlayer().isOp()) {
            if (!e.getBlock().getType().toString().equalsIgnoreCase("FIRE")) {
                e.setCancelled(true);
            }
        }

        if (e.getBlock().getType().toString().equalsIgnoreCase("OBSIDIAN")) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                if (e.getBlock().getType().equals(Material.OBSIDIAN)) {
                    e.getBlock().setType(Material.AIR);
                    e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), org.bukkit.Effect.STEP_SOUND, 49);
                }
            }, 30 * 20);
        }
    }


	@EventHandler
	public void Place(BlockPlaceEvent e) {
		if (e.getBlock().getType().toString().equalsIgnoreCase("TNT")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
				if (e.getBlock().getType().equals(Material.TNT)) {
				e.getBlock().setType(Material.AIR);
                e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), org.bukkit.Effect.STEP_SOUND, 49);
            }
        }, 60 * 20);
			}
}



		@EventHandler
		public void Rod(PlayerInteractEvent e){

			ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
			short durability = hand.getDurability(); 
			if (hand.getType().toString().equalsIgnoreCase("FISHING_ROD")) { 
    		    hand.setDurability((short)(durability + 1));
    			e.setCancelled(false);
    		
       
       
   }
}

}

