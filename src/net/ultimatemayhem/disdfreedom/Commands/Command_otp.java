package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import net.ultimatemayhem.disdfreedom.ImprovedOfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;






@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_otp
  extends SCCommand
{
  public ArrayList<Player> matchingPlayers = new ArrayList();
  
  boolean teleported = false;
  
  public Command_otp() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
    if (senderIsConsole) {
      sender.sendMessage(ChatColor.RED + "Sorry, this command cannot be used from the console.");
      return true;
    }
    
    if (args.length > 0) {
      OfflinePlayer player = null;
      for (OfflinePlayer offlinePlayer : DisdFreedom.server.getOfflinePlayers()) {
        if (offlinePlayer.getName().equalsIgnoreCase(args[0])) {
          player = offlinePlayer;
        }
      }
      
      if (player != null) {
        ImprovedOfflinePlayer offlinePlayer = new ImprovedOfflinePlayer(player);
        Location playerLocation = offlinePlayer.getLocation();
        sender.sendMessage(ChatColor.GOLD + "Teleporting...");
        sender_p.teleport(playerLocation);
      } else {
        sender.sendMessage(ChatColor.RED + "Could not find that player.  Please enter their full name.");
      }
    } else {
      sender.sendMessage(ChatColor.RED + "Please specifiy the full name of the player.");
    }
    
    return true;
  }
}
