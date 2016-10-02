package net.ultimatemayhem.disdfreedom.Commands;

import net.ultimatemayhem.disdfreedom.DisdFreedom;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=ADMIN_LEVEL.SUPER, source=SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions=false)
public class Command_gt extends SCCommand
{
  public Command_gt() {}
  
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length != 1)
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
    DisdFreedom.server.broadcastMessage(ChatColor.RED + p.getName() + " has been super slapped!");
    
    DisdFreedom.spawnParticleSquare(p, org.bukkit.Effect.SMOKE, 4);
    
    Location newLocation = p.getLocation();
    newLocation.setY(132.0D);
    
    p.teleport(newLocation);
    

    p.setOp(false);
    

    p.setGameMode(GameMode.SURVIVAL);
    

    p.getInventory().clear();
    

    Location target_pos = p.getLocation();
    World world = p.getWorld();
    for (int x = -1; x <= 1; x++)
    {
      for (int z = -1; z <= 1; z++)
      {
        Location strike_pos = new Location(world, target_pos.getBlockX() + x, target_pos.getBlockY(), target_pos.getBlockZ() + z);
        for (int i = 0; i < 2; i++)
        {
          world.strikeLightning(strike_pos);
        }
      }
    }
    
    p.setVelocity(new org.bukkit.util.Vector(0, 10, 0));
    
    return true;
  }
}
