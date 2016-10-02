package net.ultimatemayhem.disdfreedom.Commands;

import java.util.ArrayList;
import net.ultimatemayhem.disdfreedom.DisdFreedom;
import net.ultimatemayhem.disdfreedom.NopeBanDelay;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_nope extends SCCommand
{
  public Command_nope()
  {
    adminOnly = true;
  }
  

  public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1)
    {
      return false;
    }
    

    try
    {
      p = getPlayer(args[0].toString());
    }
    catch (CantFindPlayerException ex) {
      Player p;
      playerMsg(ex.getMessage(), org.bukkit.ChatColor.RED);
      return true;
    }
    
    Player p;
    
    Location newLocation = p.getLocation();
    newLocation.setY(132.0D);
    
    p.teleport(newLocation);
    
    server.dispatchCommand(sender, "rollback " + p.getName() + " all");
    
    if (!senderIsConsole)
    {
      server.dispatchCommand(sender, String.format("/undo %d %s", new Object[] { Integer.valueOf(15), p.getName() }));
    }
    
    p.setOp(false);
    p.setGameMode(GameMode.SURVIVAL);
    p.getInventory().clear();
    
    for (int i = 0; i < 3; i++)
    {
      p.getWorld().strikeLightning(p.getLocation());
    }
    p.setVelocity(new org.bukkit.util.Vector(0, 4, 0));
    
    if (!DisdFreedom.pendingPlayerExplosions.contains(p))
    {
      DisdFreedom.pendingPlayerExplosions.add(p);
      NopeBanDelay bandelay = new NopeBanDelay();
      bandelay.activateDelay(p, sender, DisdFreedom.pendingPlayerExplosions.size() - 1);
    }
    
    return true;
  }
}
