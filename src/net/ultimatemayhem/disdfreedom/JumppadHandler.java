package net.ultimatemayhem.disdfreedom;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class JumppadHandler
{
  Player targetPlayer;
  CombinedVelocity cv;
  
  public JumppadHandler(Player p)
  {
    targetPlayer = p;
    cv = new CombinedVelocity(targetPlayer);
  }
  
  public void handle()
  {
    if (DisdFreedom.jumpPadsEnabled)
    {
      Location targetPlayerLocation = targetPlayer.getLocation();
      targetPlayerLocation.setY(targetPlayerLocation.getY() - 1.0D);
      
      int blockID = targetPlayerLocation.getBlock().getTypeId();
      
      if (DisdFreedom.advancedJumpPads)
      {
        Location targetPlayerLocationLeftX = targetPlayer.getLocation();
        targetPlayerLocationLeftX.setX(targetPlayerLocationLeftX.getX() - 1.0D);
        
        Location targetPlayerLocationRightX = targetPlayer.getLocation();
        targetPlayerLocationRightX.setX(targetPlayerLocationRightX.getX() + 1.0D);
        
        Location targetPlayerLocationLeftZ = targetPlayer.getLocation();
        targetPlayerLocationLeftZ.setZ(targetPlayerLocationLeftZ.getZ() - 1.0D);
        
        Location targetPlayerLocationRightZ = targetPlayer.getLocation();
        targetPlayerLocationRightZ.setZ(targetPlayerLocationRightZ.getZ() + 1.0D);
        
        int blockIDLeftX = targetPlayerLocationLeftX.getBlock().getTypeId();
        int blockIDRightX = targetPlayerLocationRightX.getBlock().getTypeId();
        
        int blockIDLeftZ = targetPlayerLocationLeftZ.getBlock().getTypeId();
        int blockIDRightZ = targetPlayerLocationRightZ.getBlock().getTypeId();
        
        cv = new CombinedVelocity(targetPlayer);
        
        if (blockID == DisdFreedom.jumpPadBlockID)
        {

          cv.setVelocityY(DisdFreedom.jumpPadHeight);
          if (targetPlayer.getFallDistance() > 0.0F)
          {
            targetPlayer.setFallDistance(0.0F);
          }
        }
        if (blockIDLeftX == DisdFreedom.jumpPadBlockID)
        {

          cv.setVelocityX(DisdFreedom.jumpPadHeight);
          cv.setVelocityY(cv.getVelocityY() + 0.3F);
        }
        if (blockIDRightX == DisdFreedom.jumpPadBlockID)
        {

          cv.setVelocityX(-DisdFreedom.jumpPadHeight);
          cv.setVelocityY(cv.getVelocityY() + 0.3F);
        }
        if (blockIDLeftZ == DisdFreedom.jumpPadBlockID)
        {
          cv.setVelocityZ(DisdFreedom.jumpPadHeight);
          cv.setVelocityY(cv.getVelocityY() + 0.3F);
        }
        if (blockIDRightZ == DisdFreedom.jumpPadBlockID)
        {
          cv.setVelocityZ(-DisdFreedom.jumpPadHeight);
          cv.setVelocityY(cv.getVelocityY() + 0.3F);
        }
        
        if (cv.getHasBeenModified())
        {
          cv.setPlayerVelocity();
        }
        

      }
      else if (blockID == DisdFreedom.jumpPadBlockID)
      {
        targetPlayer.setVelocity(new Vector(0.0F, DisdFreedom.jumpPadHeight, 0.0F));
        if (targetPlayer.getFallDistance() > 0.0F)
        {
          targetPlayer.setFallDistance(0.0F);
        }
      }
    }
  }
}
