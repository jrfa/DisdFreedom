package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.AbilityManager;
import net.ultimatemayhem.disdfreedom.Spectator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;





@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_spectate
  extends SCCommand
{
  public Command_spectate() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (senderIsConsole)
    {
      sender.sendMessage(ChatColor.RED + "Sorry, this command cannot be used from the console.");
      return true;
    }
    
    if (args.length != 1)
    {
      return false;
    }
    
    if (args[0].equalsIgnoreCase("stop")) {
      if (AbilityManager.isPlayerSpectating(sender_p)) {
        for (Spectator spectator : AbilityManager.spectators) {
          if (spectator.getSpectator().getName() == sender_p.getName()) {
            spectator.stop(sender_p.isOnline());
          }
        }
      } else {
        sender.sendMessage(ChatColor.GRAY + "You are not spectating anyone.");
      }
      return true;
    }
    

    try
    {
      p = getPlayer(args[0]);
    }
    catch (CantFindPlayerException ex) {
      Player p;
      playerMsg(ex.getMessage(), ChatColor.RED);
      return true;
    }
    Player p;
    Spectator spectator = new Spectator(sender_p, p);
    AbilityManager.spectators.add(spectator);
    
    return true;
  }
}
