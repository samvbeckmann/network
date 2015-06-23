package com.qkninja.network.tileentity;

import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Names;
import com.qkninja.network.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.Vec3;
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
            DistanceHandler[] tempLocs = new DistanceHandler[exportLocations.size()];
            tempLocs = exportLocations.toArray(tempLocs);

            for (DistanceHandler d : tempLocs)
            {
                if (!validateBlock(d))
                {
                    exportLocations.remove(d);
                    worldObj.markBlockForUpdate(xCoord, yCoord, xCoord);
                    markDirty();
                }
            }
        }

        if (!worldObj.isRemote)
        {

            if (counter >= ConfigValues.transportDelay)
            {


                switch (this.getBlockMetadata())
                {
                    case 1:
                        if (inventory == null)
                        {
                            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord); // TODO make sided aware
                            if (tile != null && tile instanceof IInventory)
                            {
                                IInventory inv = (IInventory) tile;
                                for (int i = 0; i < inv.getSizeInventory(); i++)
                                {
                                    if (inv.getStackInSlot(i) != null)
                                    {
                                        inventory = inv.decrStackSize(i, 1);
                                        return;
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        if (inventory != null)
                        {
                            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord); // TODO make sided aware
                            if (tile != null && tile instanceof IInventory)
                            {
                                IInventory inv = (IInventory) tile;
                                ItemStack remainder = TileEntityHopper.func_145889_a(inv, inventory, 0);
                                if (remainder == null)
                                {
                                    inventory = null;
                                    return;
                                }
                            }
                        }
                        break;
                }

                attemptTeleport();
            }
        } else if (worldObj.getTotalWorldTime() % 20 == 0)
        {
            DistanceHandler[] tempLocs = new DistanceHandler[exportLocations.size()];
            tempLocs = exportLocations.toArray(tempLocs);

            if (tempLocs.length > 0)
                spawnParticles(tempLocs);
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
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
                    return true;
                } else if (validateBlock(handler))
                {
                    exportLocations.add(handler);
                    LogHelper.info("Blocks Linked successfully.");
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
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

    private void spawnParticles(DistanceHandler[] locs)
    {
        for (DistanceHandler handler : locs)
        {
            double x = handler.getX() - xCoord;
            double y = handler.getY() - yCoord;
            double z = handler.getZ() - zCoord;
            Vec3 vector = Vec3.createVectorHelper(x, y, z);
            vector = vector.normalize();
            float scale = worldObj.rand.nextFloat();

            worldObj.spawnParticle("mobSpellAmbient", xCoord + .5 + scale * x, yCoord + 0.5D + scale * y, zCoord + .5D + scale * z, vector.xCoord * .02D, vector.yCoord * .02D, vector.zCoord * .02D);
        }
    }

    public void resetCounter()
    {
        counter = 0;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory;
    }

    @Override
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

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        ItemStack itemStack = inventory;
        inventory = null;
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : null;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return this.hasCustomName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
                entityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                        (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {
        // NOOP
    }

    @Override
    public void closeInventory()
    {
        // NOOP
    }

    public List<DistanceHandler> getExportLocations()
    {
        return exportLocations;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        counter = tag.getShort(Names.NBT.COUNTER);
        inventory = ItemStack.loadItemStackFromNBT(tag);

        this.readSyncableData(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setShort(Names.NBT.COUNTER, (short) counter);
        if (inventory != null) inventory.writeToNBT(tag);

        writeSyncableData(tag);
    }

    private NBTTagCompound writeSyncableData(NBTTagCompound tag)
    {
        NBTTagList tagList = new NBTTagList();
        for (DistanceHandler exportLocation : exportLocations)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            exportLocation.writeToNBT(tagCompound);
            tagList.appendTag(tagCompound);
        }
        tag.setTag(Names.NBT.LOCATIONS, tagList);
        return tag;
    }

    private void readSyncableData(NBTTagCompound tag)
    {
        exportLocations = new ArrayList<DistanceHandler>();
        NBTTagList tagList = tag.getTagList(Names.NBT.LOCATIONS, 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            int x = tagCompound.getInteger(Names.NBT.X_COORD);
            int y = tagCompound.getInteger(Names.NBT.Y_COORD);
            int z = tagCompound.getInteger(Names.NBT.Z_COORD);
            float distance = tagCompound.getFloat(Names.NBT.DISTANCE);
            DistanceHandler handler = new DistanceHandler(x, y, z, distance);
            exportLocations.add(handler);
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        writeSyncableData(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readSyncableData(pkt.func_148857_g());
    }
}
