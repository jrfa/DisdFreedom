package net.ultimatemayhem.disdfreedom;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathMessages
{
  public String[] messages = { "VICTIM <r>hit the ground too fucking hard.</r>", "VICTIM was <r>torn apart</r> by ATTACKER", "VICTIM was <r>stabbed</r> by ATTACKER", "VICTIM was <r>punched</r> to death by ATTACKER", "VICTIM was <r>assassinated</r> by ATTACKER", "VICTIM was <r>poisoned</r> by ATTACKER", "VICTIM was <r>killed</r> by ATTACKER with ITEM" };
  
  public DeathMessages() {}
  
  public boolean getEvent(PlayerDeathEvent event)
  {
    Player player = event.getEntity();
    org.bukkit.Location location = player.getLocation();
    location.setY(location.getY() - 1.0D);
    player.teleport(location);
    String newMessage = null;
    if (player.getKiller() != null) {
      if (player.getKiller().getItemInHand().getType().toString().contains("SWORD")) {
        newMessage = messages[2];
      } else if (player.getKiller().getItemInHand().getType().toString().contains("BOW")) {
        newMessage = messages[4];
      } else if (player.getKiller().getItemInHand().getType().toString().contains("AIR")) {
        newMessage = messages[3];
      } else {
        newMessage = messages[6];
      }
    }
    if (newMessage != null) {
      newMessage = colorMessage(newMessage);
      newMessage = newMessage.replaceAll("VICTIM", player.getName()).replaceAll("ATTACKER", player.getKiller().getName());
      String itemName = player.getKiller().getItemInHand().getType().toString().replaceAll("_", " ");
      itemName = org.apache.commons.lang.WordUtils.capitalize(itemName);
      newMessage = newMessage.replaceAll("ITEM", itemName);
      event.setDeathMessage(newMessage);
      return true;
    }
    return false;
  }
  
  public String colorMessage(String s)
  {
    s = s.replaceAll("VICTIM", ChatColor.BLUE + "VICTIM" + ChatColor.AQUA);
    s = s.replaceAll("ATTACKER", ChatColor.RED + "ATTACKER" + ChatColor.AQUA);
    s = s.replaceAll("ITEM", ChatColor.GOLD + "ITEM" + ChatColor.AQUA);
    s = s.replaceAll("<r>", ChatColor.DARK_RED);
    s = s.replaceAll("</r>", ChatColor.AQUA);
    return s;
  }
}
