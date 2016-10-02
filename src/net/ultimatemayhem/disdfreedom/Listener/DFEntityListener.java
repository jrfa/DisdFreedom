package net.ultimatemayhem.disdfreedom.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class DFEntityListener implements Listener
{
  public DFEntityListener() {}
  
  @org.bukkit.event.EventHandler(priority=EventPriority.NORMAL)
  public void onBlockPhysics(BlockPhysicsEvent event)
  {
    if ((event.getBlock().getType().name().equalsIgnoreCase("sand")) || (event.getBlock().getType().name().equalsIgnoreCase("gravel")) || (event.getBlock().getType().name().equalsIgnoreCase("anvil"))) {
      event.setCancelled(true);
    }
  }
}
