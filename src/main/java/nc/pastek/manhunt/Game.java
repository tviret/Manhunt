package nc.pastek.manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Game  extends Object{
    ArrayList <String> speedrunners = new ArrayList<String>();
    ArrayList <String> hunters = new ArrayList<String>();
    Location teleport_block;
    boolean inGame = false;

    public boolean pl_in_hunters(Player player){
        for(int i = 0 ; i < hunters.size() ; i++){
            if(Objects.equals(Bukkit.getPlayer(hunters.get(i)), player)) {
                return true;
            }
        }
        return false;
    }

    public boolean pl_in_speedrunners(Player player){
        for (String speedrunner : speedrunners) {
            if (Objects.equals(Bukkit.getPlayer(speedrunner), player)) {
                return true;
            }
        }
        return false;
    }

    public Player get_closer_runner(Location target){

        World targetWorld = target.getWorld();
        ArrayList<Player> pl = new ArrayList<Player>();
        for(int i = 0 ; i<speedrunners.size(); i++){
            if (Objects.requireNonNull(Bukkit.getPlayer(speedrunners.get(i))).getLocation().getWorld() == targetWorld){
                pl.add(Bukkit.getPlayer(speedrunners.get(i)));
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
        hunters.forEach((pl) -> Objects.requireNonNull(Bukkit.getPlayer(pl)).sendMessage(message));
        speedrunners.forEach((pl) -> Objects.requireNonNull(Bukkit.getPlayer(pl)).sendMessage(message));
    }

    public void effects_on_hunters(int duration){

        for (String pl_pseudo : hunters) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl_pseudo + " slowness "+duration+" 255 false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl_pseudo + " jump_boost "+duration+" 255 false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + pl_pseudo + " blindness "+duration+" 255 false");
        }
    }




}

