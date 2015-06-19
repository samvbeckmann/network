package com.qkninja.network.tileentity;

import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.*;

/**
 * Defines a Tile Entity of a transporter (nexus)
 *
 * @author QKninja
 */
public class TileEntityTransporter extends TileEntityNetwork implements IInventory
{
    private int counter;
    private ItemStack inventory;
    private List<DistanceHandler> exportLocations = new ArrayList<DistanceHandler>();

    @Override
    public void updateEntity()
    {
        if (counter < ConfigValues.transportDelay) counter++;

        if (counter >= ConfigValues.transportDelay)
        {
            for (DistanceHandler d : exportLocations)
            {
                if (!validateBlock(d))
                    exportLocations.remove(d);
            }

            switch (this.getBlockMetadata())
            {
                case 1:
                    if (inventory == null)
                    {
                        // TODO Grab item from inventory
                    }
                    return;
                case 2:
                    if (inventory != null)
                    {
                        // TODO attempt place in inventory
                    }
                    return;
            }

            attemptTeleport();
        }
    }

    public boolean addDestination(World world, int x, int y, int z)
    {
        if (world != worldObj)
        {
            LogHelper.info("Blocks not linked, world error.");
            return false;
        } else if (xCoord == x && yCoord == y && zCoord == z)
        {
            LogHelper.info("Blocks not linked, same block.");
            return false;
        }
        {
            DistanceHandler handler = new DistanceHandler(this, x, y, z);
            if (handler.getDistance() > ConfigValues.maxDistanceSq)
            {
                LogHelper.info("Blocks not linked, too far away.");
                return false;
            } else
            {
                if (exportLocations.contains(handler))
                {
                    exportLocations.remove(handler);
                    LogHelper.info("Block link removed.");
                    return true;
                } else if (validateBlock(handler))
                {
                    exportLocations.add(handler);
                    LogHelper.info("Blocks Linked successfully.");
                    return true;
                } else
                {
                    LogHelper.info("Blocks not linked, not Transporter");
                    return false;
                }
            }
        }
    }

    private boolean validateBlock(DistanceHandler d)
    {
        TileEntity te = worldObj.getTileEntity(d.getX(), d.getY(), d.getZ());
        return te instanceof TileEntityTransporter;
    }


    private boolean attemptTeleport()
    {
        Collections.sort(exportLocations);
        for (DistanceHandler loc : exportLocations)
        {
            TileEntity tile = worldObj.getTileEntity(loc.getX(), loc.getY(), loc.getZ());
            if (tile instanceof TileEntityTransporter) // Should always be true
            {
                TileEntityTransporter transporter = (TileEntityTransporter) tile;
                if (transporter.getStackInSlot(1) == null)
                {
                    transporter.setInventorySlotContents(1, this.inventory);
                    this.decrStackSize(1, 1);
                    transporter.resetCounter();
                    return true;
                }
            }
        }
        return false;
    }

    public void resetCounter()
    {
        counter = 0;
    }

    public int getSizeInventory()
    {
        return 1;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return inventory;
    }

    public ItemStack decrStackSize(int slotIndex, int numRemove)
    {
        if (this.inventory != null)
        {
            if (inventory.stackSize <= numRemove)
            {
                ItemStack itemStack = inventory;
                inventory = null;
                return itemStack;
            } else
            {
                return inventory.splitStack(numRemove);
            }
        } else
            return null;
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        ItemStack itemStack = inventory;
        inventory = null;
        return itemStack;
    }

    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : null;
    }

    public boolean hasCustomInventoryName()
    {
        return this.hasCustomName();
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }

    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
                entityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory()
    {
        // NOOP
    }

    public void closeInventory()
    {
        // NOOP
    }

    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }
}
