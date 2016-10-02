package net.ultimatemayhem.disdfreedom;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WorldEdit_Check {
  public WorldEdit_Check() {}
  
  public boolean getPreprocessCommandEvent(PlayerCommandPreprocessEvent event) {
    boolean block = false;
    
    String message = event.getMessage();
    String[] messageArgs = message.split(" ");
    
    if (message.startsWith("//")) {
      for (String str : messageArgs) {
        try {
          int numb = Integer.parseInt(str);
          if (numb > DisdFreedom.worldEditCommandLimitSize) {
            block = true;
          }
        }
        catch (Exception localException) {}
      }
    }
    return block;
  }
}
