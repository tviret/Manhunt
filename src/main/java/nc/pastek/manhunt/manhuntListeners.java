package nc.pastek.manhunt;

import org.bukkit.Bukkit;
import java.lang.Object;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import javax.swing.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;


public class manhuntListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        return;

    }


    @EventHandler
    public void onSpawn(PlayerRespawnEvent q){
        Player player = q.getPlayer();
        Player target_pl = commandGame.game.get_closer_runner(player.getLocation());
        Location target;
        if (target_pl == null){
            target = new Location(player.getWorld(), 0 ,0 ,0);
        }else{
        target = target_pl.getLocation();
        }

        ItemStack compass = new ItemStack(Material.COMPASS);
        Location location = target;

        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        assert compassMeta != null;
        compassMeta.setLodestone(location);
        compassMeta.setLodestoneTracked(false);

        compass.setItemMeta(compassMeta);

        player.getInventory().addItem(compass);

        compassMeta.setLodestone(location);
        compassMeta.setLodestoneTracked(false);
        compass.setItemMeta(compassMeta);

    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Action a = e.getAction();


        if (!(e.getItem() == null)){
        if (((a.toString().equals("RIGHT_CLICK_AIR")) || (a.toString().equals("RIGHT_CLICK_BLOCK"))) && (e.getItem().getType() == Material.COMPASS)) {

            Player target_pl = commandGame.game.get_closer_runner(player.getLocation());

            if (target_pl == null){
                player.sendMessage("Il n'y a aucun speedrunner dans votre dimension.");
                return;
            }
            Location target = target_pl.getLocation();


            ItemStack compass = e.getItem();

            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
            assert compassMeta != null;
            compassMeta.setLodestone(target);
            compassMeta.setLodestoneTracked(false);

            compass.setItemMeta(compassMeta);
            System.out.println("la bousolle pointe vers : "+target.getBlockX()+" "+target.getBlockY()+" "+target.getBlockZ());
            System.out.println("position de "+target_pl.getDisplayName()+" : "+target_pl.getLocation().getBlockX() + " " + +target_pl.getLocation().getBlockY() + " " + target_pl.getLocation().getBlockZ());
            System.out.println(((CompassMeta) compass.getItemMeta()).getLodestone());
            player.sendMessage("La boussole pointe vers "+target_pl.getName());
        }
    }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(commandGame.game.inGame){
            if(commandGame.game.speedrunners.contains(player.getName())){
                player.setGameMode(GameMode.SPECTATOR);
                commandGame.game.speedrunners.remove(player.getName());
                commandGame.game.broadcast("Le speedrunner " + "§c" + player.getDisplayName()+"est §cMort");
                if (commandGame.game.speedrunners.isEmpty()){
                    // les hunter ont gagné
                    for(String pl_pseudo : commandGame.game.hunters){
                        Objects.requireNonNull(Bukkit.getPlayer(pl_pseudo)).setGameMode(GameMode.SPECTATOR);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+pl_pseudo+" times 20 100 20");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+pl_pseudo+" subtitle {\"text\":\"Tous les speedrunners sont tombés\"}");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title "+pl_pseudo+" title [\"\",{\"text\":\"Les \"},{\"text\":\"Hunters\",\"color\":\"dark_red\"},{\"text\":\" ont \"},{\"text\":\"gagné !\",\"color\":\"dark_red\"}]");
                    }

                    commandGame.game.inGame = false;
                }
            }
        }
    }





    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location blockCoords = block.getLocation();

        if(Main.in_game){

            }

       }
    }






