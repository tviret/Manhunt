package nc.pastek.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("Bravo, tu as réussi le test");
                return true;

            } else {
                sender.sendMessage("Commande réservée au joueurs.");
            }

        }

        if (command.getName().equalsIgnoreCase("broadcast")){
            if(args.length == 0){
                sender.sendMessage("la commande est /broadcast <message>");
                return false;
            }

            if(args.length > 0) {

                if (sender.getName().equals("Superpasteque211")) {

                    StringBuilder bc = new StringBuilder();
                    for(String part : args){
                        bc.append(part+" ");
                    }

                    Bukkit.broadcastMessage("§c[Le Roi Pastèque vous parle ! ]:  "+bc);
                    return true;
                }
                else{

                    StringBuilder bc = new StringBuilder();
                    for(String part : args){
                        bc.append(part+" ");
                    }

                    Bukkit.broadcastMessage("["+sender.getName()+"] "+bc);
                    return true;

                }

            }

        }

        return false;

    }
}
