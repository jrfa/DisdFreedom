package net.ultimatemayhem.disdfreedom.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_warn
  extends SCCommand
{
  public Command_warn() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length < 1)
    {
      return false;
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
    boolean actions = true;
    
    if ((args.length > 1) && 
      (args[1].equalsIgnoreCase("off"))) {
      actions = false;
    }
    

    TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(p);
    playerdata.setFrozen(actions);
    playerdata.setMuted(actions);
    
    if (actions) {
      DisdFreedom.server.broadcastMessage(ChatColor.RED + p.getName() + ", you are at high risk of being permanently banned (name and IP) from the Total Freedom server. Please immediately review all rules listed at www.totalfreedom.me and comply with them.");
    } else {
      p.sendMessage(ChatColor.GRAY + "You have been released.");
      sender.sendMessage(ChatColor.GRAY + "Warning removed.");
    }
    
    return true;
  }
}
