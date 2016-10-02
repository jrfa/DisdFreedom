package net.ultimatemayhem.disdfreedom;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class AbilityManager
{
  public static ArrayList<String> strengthEnabledFor = new ArrayList();
  
  public static ArrayList<Spectator> spectators = new ArrayList();
  
  public AbilityManager() {}
  
  public static boolean isStrengthEnabledFor(String s) { if (strengthEnabledFor.contains(s))
    {
      return true;
    }
    return false;
  }
  
  public static void addBurriedPlayer(String s)
  {
    if (!DisdFreedom.burriedPlayers.contains(s))
    {
      DisdFreedom.burriedPlayers.add(s);
    }
  }
  
  public static void removeBurriedPlayer(String s)
  {
    for (int i = 0; i < DisdFreedom.burriedPlayers.size(); i++)
    {
      if (((String)DisdFreedom.burriedPlayers.get(i)).equals(s))
      {
        DisdFreedom.burriedPlayers.remove(i);
      }
    }
  }
  
  public static boolean isBurriedPlayer(String s)
  {
    if (DisdFreedom.burriedPlayers.contains(s))
    {
      return true;
    }
    return false;
  }
  
  public static boolean isPlayerSpectating(Player p) {
    for (Spectator spectator : spectators) {
      if (player.getName() == p.getName()) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isPlayerBeingSpectated(Player p) {
    for (Spectator spectator : spectators) {
      if (targetPlayer.getName() == p.getName()) {
        return true;
      }
    }
    return false;
  }
}
