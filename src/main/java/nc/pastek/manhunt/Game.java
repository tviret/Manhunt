package nc.pastek.manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Locale;

public class Game  extends Object{
    ArrayList <Player> speedrunners = new ArrayList<Player>();
    ArrayList <Player> hunters = new ArrayList<Player>();
    Location teleport_block;
    boolean inGame = false;

    public boolean pl_in_hunters(Player player){
        for(int i = 0 ; i < hunters.size() ; i++){
            if(hunters.get(i).equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean pl_in_speedrunners(Player player){
        for(int i = 0 ; i < speedrunners.size() ; i++){
            if(speedrunners.get(i).equals(player)) {
                return true;
            }
        }
        return false;
    }

    public Player get_closer_runner(Location target){

        World targetWorld = target.getWorld();
        ArrayList<Player> pl = new ArrayList<Player>();
        for(int i = 0 ; i<speedrunners.size(); i++){
            if (speedrunners.get(i).getLocation().getWorld() == targetWorld){
                pl.add(speedrunners.get(i));
            }
        }

        if (pl.isEmpty()){
            return null;
        }

        if (pl.size() == 1){
            return pl.getFirst();
        }

        double dist_mini = (double) -1 ;
        int id_mini = 0;
        double targetX = target.getX();
        double targetZ = target.getZ();

        for(int i = 0 ; i<pl.size(); i++){
            Location loca = pl.get(i).getLocation();

            double locaX = loca.getX();
            double locaZ = loca.getZ();


            double dist_euc = Math.sqrt((targetX-locaX)*(targetX-locaX) + (targetZ-locaZ)*(targetZ-locaZ));


            if (dist_mini < (double)0) {dist_mini = dist_euc; id_mini = i;;};
            if (dist_euc < dist_mini) {dist_mini = dist_euc; id_mini = i;System.out.println();};


        }

        return pl.get(id_mini);

    }

    public void broadcast(String message){
        hunters.forEach((pl) -> pl.sendMessage(message));
        speedrunners.forEach((pl) -> pl.sendMessage(message));
    }

    public void effects_on_hunters(int duration){

        for (Player pl : hunters) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl.getName() + " slowness "+duration+" 255 false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl.getName() + " jump_boost "+duration+" 255 false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl.getName() + " blindness "+duration+" 255 false");
        }
    }



}

