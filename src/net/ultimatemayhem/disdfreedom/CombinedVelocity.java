package net.ultimatemayhem.disdfreedom;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class CombinedVelocity
{
  Player targetPlayer;
  float velocityX = 0.0F;
  float velocityY = 0.0F;
  float velocityZ = 0.0F;
  boolean hasBeenModified = false;
  
  public CombinedVelocity(Player p)
  {
    targetPlayer = p;
  }
  
  public void setVelocityX(float newX)
  {
    hasBeenModified = true;
    velocityX = newX;
  }
  
  public void setVelocityY(float newY)
  {
    hasBeenModified = true;
    velocityY = newY;
  }
  
  public void setVelocityZ(float newZ)
  {
    hasBeenModified = true;
    velocityZ = newZ;
  }
  
  public float getVelocityX()
  {
    return velocityX;
  }
  
  public float getVelocityY()
  {
    return velocityY;
  }
  
  public float getVelocityZ()
  {
    return velocityZ;
  }
  
  public boolean getHasBeenModified()
  {
    return hasBeenModified;
  }
  
  public void setPlayerVelocity()
  {
    if (hasBeenModified)
    {
      targetPlayer.setVelocity(new Vector(velocityX, velocityY, velocityZ));
    }
  }
}
