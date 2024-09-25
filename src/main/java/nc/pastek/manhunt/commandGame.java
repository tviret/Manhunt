package nc.pastek.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.security.spec.ECField;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class commandGame implements CommandExecutor {

    public static Game game = new Game();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player;
        if(sender instanceof Player) {
            player = (Player) sender;
        }else{return false;}

        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("help")){
                player.sendMessage("/game speedrunner\n/game hunter\n/game init {dimension (0,1 ou 2)} {x} {y} {z}  \n/game start\n/game close\n/game help");
            }


            if(args[0].equalsIgnoreCase("speedrunner")){
                if (game.inGame){
                    player.sendMessage("Vous ne pouvez pas rejoindre d'équipe si la game a commencé.");
                    return false;
                }
                if(game.pl_in_hunters(player)){
                    game.hunters.remove(player.getName());
                }if(!game.pl_in_speedrunners(player)){
                    game.speedrunners.add(player.getName());
                    player.sendMessage("Vous rejoignez les speedrunners");
                }else {player.sendMessage("Vous etes déjà speedrunner");}
            }

            if(args[0].equalsIgnoreCase("hunter")){

                if (game.inGame){
                    player.sendMessage("Vous ne pouvez pas rejoindre d'équipe si la game a commencé.");
                    return false;
                }

                if(game.pl_in_speedrunners(player)){
                    game.speedrunners.remove(player.getName());
                }if(!game.pl_in_hunters(player)){
                    game.hunters.add(player.getName());
                    player.sendMessage("Vous rejoignez les hunters");
                }else{player.sendMessage("Vous etes déjà hunters");}
            }
            if(args[0].equalsIgnoreCase("list")){
                player.sendMessage("Speedrunners : \n");
                for (int i = 0 ; i < game.speedrunners.size() ; i ++){
                    player.sendMessage(game.speedrunners.get(i));
                }

                player.sendMessage("\nHunters: \n");
                for (int i = 0 ; i < game.hunters.size() ; i ++){
                    player.sendMessage(game.hunters.get(i));
                }
            }



        return false;
    }

        if (args.length == 2){

            if (args[0].equalsIgnoreCase("start")){

                if (game.inGame){
                    player.sendMessage("Vous ne pouvez pas start une game si la game a commencé.");
                    return false;
                }

                if (!game.speedrunners.contains(player.getName())){ // on vérifie que c'est un runner qui execute la commande
                    player.sendMessage("Seul un speedrunner peut executer la commande start");
                    return  false;
                }

                try {int compteur = Integer.parseInt(args[1]);}
                catch (Exception e){player.sendMessage("Veuillez entrer un délai valide"); return false;}

                final int[] compteur = {Integer.parseInt(args[1])};

                Location tp_all;
                if (game.teleport_block == null){ // si on a specifié un bloc où tp tout le monde on s'y tp, sinon on se tp à l'executeur de la commande
                    tp_all = player.getLocation();

                } else {
                    tp_all = game.teleport_block;
                }

                // tp de tout les hunter et runners
                game.hunters.forEach((pl) -> Objects.requireNonNull(Bukkit.getPlayer(pl)).teleport(tp_all));
                game.speedrunners.forEach((pl) -> Objects.requireNonNull(Bukkit.getPlayer(pl)).teleport(tp_all));

                // début du timer (message au début, message toutes les 30aines de secondes messages toutes les secondes de 10 à 0)

                Timer chrono = new Timer();

                int min = compteur[0] / 60;
                int sec = compteur[0] % 60;
                game.broadcast("Démarrage du chrono, lancement dans "+min+":"+sec);
                game.effects_on_hunters(compteur[0]);
                game.inGame = true; // on entre dans une game.



                chrono.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        if (compteur[0] > 0){

                            int min = compteur[0] / 60;
                            int sec = compteur[0] % 60;

                            if (compteur[0] <=10){
                                game.broadcast(""+ compteur[0]);
                            }
                            else if (sec == 30) {
                                game.broadcast("Il reste "+min+":"+sec);
                            } else if (sec == 0) {
                                game.broadcast("Il reste "+min+":"+sec+"0");
                            }
                            compteur[0]--;


                        }else{
                            // démarrage de la game
                            game.broadcast("START !");

                            cancel();
                        }
                    }
                },1000,1000);


            }
        }

        if (args.length == 5){

            if(args[0].equalsIgnoreCase("init")){
                World dimension = null;
                ;switch (args[1]){
                    case "0":  dimension = Bukkit.getWorld("world"); break;
                    case "1":  dimension = Bukkit.getWorld("world_nether"); break;
                    case "2":  dimension = Bukkit.getWorld("world_the_end"); break;
                    default: player.sendMessage("Veuillez entrer une dimension valide (0 overworld 1 nether 2 end)"); return false;

                }

                if (dimension == null) {
                    player.sendMessage("Une erreur a eu lieu en éxecutant la commande (dimension non trouvée)");
                    return false;
                }

                 try {
                    int x = Integer.parseInt(args[2]);
                    int y = Integer.parseInt(args[3]);
                    int z = Integer.parseInt(args[4]);

                    try{
                        game.teleport_block = new Location(dimension,(double)x,(double) y,(double) z);
                        return true;
                    }
                    catch (Exception e){

                        player.sendMessage("Une erreur a eu lieu lors de l'execution de la commande : ",e.toString());
                    }



                }
                catch (Exception e){
                    player.sendMessage("Veuillez entrer des valeurs valides pour les coordonnées");
                    return false;
                }



            }


        }
        return false;
    }
}