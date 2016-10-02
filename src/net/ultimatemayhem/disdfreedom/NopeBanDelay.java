package net.ultimatemayhem.disdfreedom;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NopeBanDelay extends DelayedTask
{
  Player targetPlayer;
  CommandSender sender;
  int playerNumb;
  
  public NopeBanDelay()
  {
    super(24L);
  }
  
  public void activateDelay(Player p, CommandSender newSender, int i)
  {
    targetPlayer = p;
    sender = newSender;
    playerNumb = i;
    activate();
  }
  

  public void actions()
  {
    targetPlayer.setHealth(0);
    targetPlayer.getWorld().createExplosion(targetPlayer.getLocation(), 4.0F, false);
    DisdFreedom.server.broadcastMessage(org.bukkit.ChatColor.RED + targetPlayer.getName() + " was blown to pieces.");
    for (int i = 0; i < 3; i++)
    {
      targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
    }
    DisdFreedom.spawnParticleSquare(targetPlayer, org.bukkit.Effect.MOBSPAWNER_FLAMES, 4);
    targetPlayer.kickPlayer("NOPE");
    DisdFreedom.server.dispatchCommand(sender, "glist ban " + targetPlayer.getName());
    DisdFreedom.pendingPlayerExplosions.remove(playerNumb);
  }
}
