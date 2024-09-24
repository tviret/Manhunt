package nc.pastek.manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Locale;

public class Team  extends Object{
    String name;
    ArrayList <Player> players;
    Team(String str){
        this.name = str;
        this.players = new ArrayList();

    }


    public void AddPlayer(Player player){

        if(players.size()==0){
            players.add(player);
            player.sendMessage("Ajouté à l'équipe avec succès.");
            return;
        }
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equals(player.getName())){
                System.out.println("Le joueur est déjà dans cette equipe.");
                player.sendMessage("Vous êtes deja dans cette equipe .");
                return;
            }
        }
        players.add(player);
        player.sendMessage("Ajouté à l'équipe avec succès.");

    }

    public void DisplayPlayers(Player player){
        if(players.size()==0){
            player.sendMessage("Il n'y a personne dans cette equipe");
        }else{
            StringBuilder dp = new StringBuilder();
            for(Player el : players){
                dp.append(el.getName()+"\n");
            }
            player.sendMessage(players.size()+" joueur(s) dans cette equipe :");
            player.sendMessage(String.valueOf(dp));
        }
    }
    public void QuitTeam(Player player){
        players.remove(player);
        player.sendMessage("§2Equipe quitée avec succès.");
    }
    public void ResetTeam(){
        this.name ="";
        this.players = new ArrayList<>();
    }
}
