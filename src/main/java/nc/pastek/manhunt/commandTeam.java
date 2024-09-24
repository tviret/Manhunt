package nc.pastek.manhunt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class commandTeam implements CommandExecutor {

    public static ArrayList<Team> teams = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


            if(sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length == 1) {
                    if(args[0].equals("help")){
                        player.sendMessage("/team create <nom>\n/team join <nom>\n/team list\n/team playerlist <nom>\n/team quit\n/team delete <nom>\n/team help\n");
                    }

                    if (args[0].equals("list")) {

                        if (teams.isEmpty()) {

                            System.out.println("okok");
                            player.sendMessage("Aucune équipe créée.");
                        } else {

                            StringBuilder bc = new StringBuilder();
                            for(int i = 0 ; i<teams.size(); i++){
                                bc.append("§2"+teams.get(i).name + "\n");

                            }
                            player.sendMessage("§2"+String.valueOf(teams.size())+" équipes active(s) :");
                            player.sendMessage(String.valueOf(bc));

                        }
                    }
                }
                if (args[0].equals("quit")){
                    QuitTeam(player);
                }

                if (args.length == 2) {
                    if (args[0].equals("create")) {
                        if (!equipeExiste(args[1])){
                            teams.add(new Team(args[1]));
                            player.sendMessage("§2Equipe "+args[1]+" créée avec succès !");
                        }else{
                            player.sendMessage("Cette équipe existe déjà :/");
                        }

                    }
                    if(args[0].equals("join")){
                        AddPlayer(player,args[1]);
                    }
                    if(args[0].equals("playerlist")){
                        DisplayPlayers(args[1],player);

                    }
                    if(args[0].equals("delete")){
                        if(equipeExiste(args[1])){
                            DeleteTeam(player,args[1]);
                        }
                    }
                }
            }


        return false;
    }


    public static boolean equipeExiste(String proposition) {

        for (int i = 0; i < teams.size(); i++) {
            if(teams.get(i).name.equals(proposition)){
                return true;
            }
        }
        return false;
    }

    public static int GetEquipeByName(String name) {
        if (equipeExiste(name)) {
            for (int i = 0; i < teams.size(); i++) {
                if (teams.get(i).name.equals(name)) {
                    return i;
                }

            }
        }
        return 99999;
    }

    public void AddPlayer(Player player , String team_name){
        if (equipeExiste(team_name)){
            if(!isPlayerInTeam(player)){
                teams.get(GetEquipeByName(team_name)).AddPlayer(player);
            }else{
                player.sendMessage("§cVous êtes déjà dans une équipe");
            }

        }else{
            player.sendMessage("§cL'équipe demandée n'existe pas.");
        }
    }

    public void DisplayPlayers(String team_name,Player player){
        if(equipeExiste(team_name)){
            teams.get(GetEquipeByName(team_name)).DisplayPlayers(player);
        }else {
            player.sendMessage("§cL'équipe demandée n'existe pas.");
        }


    }
    public void QuitTeam(Player player){
        if(isPlayerInTeam(player)){
            GetPlayerTeam(player).QuitTeam(player);
        }
        else{
            player.sendMessage("§cVous n'êtes pas dans une équipe.");
        }
    }
    public void DeleteTeam(Player player,String team_name){
        if(equipeExiste(team_name) ){
            teams.get(GetEquipeByName(team_name)).ResetTeam();
            teams.remove(GetEquipeByName(""));
            player.sendMessage("§2Equipe supprimée avec succès."); ;
        }


        }


    public static Team GetPlayerTeam(Player player){
        for(Team el : teams){
            if(el.players.contains(player)){
               return el;
            }
        }
        return null;
    }

    public boolean isPlayerInTeam(Player player){
        for(Team el : teams){
            if(el.players.contains(player)){
                return true;
            }

        }
        return false;
    }
    public static Team get_team_by_name(String team_name){

        if(teams.size()==0){
            return null;
        }
        if(equipeExiste(team_name)){

        for(int i = 0; i<teams.size();i++ ) {
            Team val = teams.get(i);

            if (val.name.equals(team_name)) {
                return val;
            }
        }

        }
        return null;

    }



    }