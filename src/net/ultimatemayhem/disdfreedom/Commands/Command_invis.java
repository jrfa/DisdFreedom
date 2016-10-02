package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_invis extends SCCommand
{
  public ArrayList<String> invisiblePlayers = new ArrayList();
  
  public Command_invis() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) { Player localPlayer1;
    Player player;
    if (args.length > 0)
    {
      if (args[0].equalsIgnoreCase("smiteall"))
      {
        int count = 0;
        Player[] arrayOfPlayer2; int i = (arrayOfPlayer2 = DisdFreedom.server.getOnlinePlayers()).length; for (localPlayer1 = 0; localPlayer1 < i; localPlayer1++) { player = arrayOfPlayer2[localPlayer1];
          
          if ((player.getActivePotionEffects().toString().contains("INVISIBILITY")) && (!net.ultimatemayhem.disdfreedom.DF_Util.isSuperAdmin(player)))
          {
            for (int i = 0; i < 2; i++)
            {
              player.getWorld().strikeLightning(player.getLocation());
            }
            for (PotionEffect potionEffect : player.getActivePotionEffects())
            {
              player.removePotionEffect(potionEffect.getType());
            }
            player.setHealth(0);
            player.sendMessage(ChatColor.RED + "No invisibility potions!");
            count++;
          }
        }
        sender.sendMessage(ChatColor.GRAY + count + " player(s) smited.");
      }
      return true;
    }
    try
    {
      Player[] arrayOfPlayer1;
      localPlayer1 = (arrayOfPlayer1 = DisdFreedom.server.getOnlinePlayers()).length; for (player = 0; player < localPlayer1; player++) { Player player = arrayOfPlayer1[player];
        
        if (player.getActivePotionEffects().toString().contains("INVISIBILITY"))
        {
          invisiblePlayers.add(player.getName());
        }
      }
    }
    catch (Exception localException) {}
    


    if (getInvisiblePlayers() != "None")
    {
      sender.sendMessage(ChatColor.GRAY + "Invisible players: " + getInvisiblePlayers());
    }
    else
    {
      sender.sendMessage(ChatColor.GRAY + "No invisible players.");
    }
    
    return true;
  }
  
  public String getInvisiblePlayers()
  {
    String players = "";
    if (invisiblePlayers.size() > 0)
    {
      for (int i = 0; i < invisiblePlayers.size(); i++)
      {
        if (players != "")
        {
          players = players + ", " + (String)invisiblePlayers.get(i);
        }
        else
        {
          players = (String)invisiblePlayers.get(i);
        }
        
      }
      
    } else {
      players = "None";
    }
    return players;
  }
}
