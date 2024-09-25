package nc.pastek.manhunt;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin{

    public static boolean in_game = true;
    public static boolean game_init = false;


    @Override
    public void onEnable() {
        System.out.println(" Yo Angelo !!! ");
        getCommand("test").setExecutor(new CommandTest());
        getCommand("broadcast").setExecutor(new CommandTest());
        getCommand("team").setExecutor(new commandTeam());
        getCommand("game").setExecutor(new commandGame());
        getServer().getPluginManager().registerEvents(new manhuntListeners(), this);


    }

}
