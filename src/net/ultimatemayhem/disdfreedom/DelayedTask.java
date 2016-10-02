package net.ultimatemayhem.disdfreedom;

import org.bukkit.Server;

public class DelayedTask { boolean complete = true;
  long delayTime;
  
  public DelayedTask(long time)
  {
    delayTime = time;
  }
  
  public void activate()
  {
    if (complete)
    {
      complete = false;
      DisdFreedom.plugin.getServer().getScheduler().scheduleSyncDelayedTask(DisdFreedom.plugin, new Runnable()
      {

        public void run()
        {
          actions();
          complete = true;
        }
      }, delayTime);
    }
  }
  
  public void actions() {}
}
