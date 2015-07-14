package com.qkninja.network.tileentity;

import com.qkninja.network.client.particle.EntityFXSpark;
import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Names;
import com.qkninja.network.utility.LogHelper;
import com.qkninja.network.utility.TransporterMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
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
import net.minecraftforge.common.util.ForgeDirection;

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
    private TransporterMode mode = TransporterMode.NEUTRAL;

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


                switch (mode)
                {
                    case IMPORT:
                        if (inventory == null)
                        {
                            TileEntity tile = getTileEntityFromMetadata();
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
                    case EXPORT:
                        if (inventory != null)
                        {
                            TileEntity tile = getTileEntityFromMetadata();
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

                if (inventory != null)
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
            if (ConfigValues.debugMode) LogHelper.info("Blocks not linked, world error.");
            return false;
        } else if (xCoord == x && yCoord == y && zCoord == z)
        {
            if (ConfigValues.debugMode) LogHelper.info("Blocks not linked, same block.");
            return false;
        }
        {
            DistanceHandler handler = new DistanceHandler(this, x, y, z);
            if (handler.getDistance() > ConfigValues.maxDistanceSq)
            {
                if (ConfigValues.debugMode) LogHelper.info("Blocks not linked, too far away.");
                return false;
            } else
            {
                if (exportLocations.contains(handler))
                {
                    exportLocations.remove(handler);
                    if (ConfigValues.debugMode) LogHelper.info("Block link removed.");
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
                    return true;
                } else if (validateBlock(handler))
                {
                    exportLocations.add(handler);
                    if (ConfigValues.debugMode) LogHelper.info("Blocks Linked successfully.");
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
                    return true;
                } else
                {
                    if (ConfigValues.debugMode) LogHelper.info("Blocks not linked, not Transporter");
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
                    resetCounter();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Spawns a particle to travel along the paths of all active connections.
     *
     * @param locs array of locations to send particles to
     */
    private void spawnParticles(DistanceHandler[] locs)
    {
        for (DistanceHandler handler : locs)
        {
            double x = handler.getX() - xCoord;
            double y = handler.getY() - yCoord;
            double z = handler.getZ() - zCoord;
            Vec3 vector = Vec3.createVectorHelper(x, y, z);
            double distance = vector.lengthVector();

            vector = vector.normalize();

            double xMotion = vector.xCoord / ConfigValues.sparkSpeedFactor;
            double yMotion = vector.yCoord / ConfigValues.sparkSpeedFactor;
            double zMotion = vector.zCoord / ConfigValues.sparkSpeedFactor;
            int lifespan = (int) (distance * ConfigValues.sparkSpeedFactor);

            EntityFX spark = new EntityFXSpark(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, xMotion, yMotion, zMotion, lifespan);
            Minecraft.getMinecraft().effectRenderer.addEffect(spark);
        }
    }

    public void resetCounter()
    {
        counter = 0;
    }

    public TransporterMode incrementMode()
    {
        switch (mode)
        {
            case NEUTRAL:
                mode = TransporterMode.IMPORT;
                break;
            case IMPORT:
                mode = TransporterMode.EXPORT;
                break;
            case EXPORT:
                mode = TransporterMode.NEUTRAL;
                break;
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
        return mode;
    }

    private TileEntity getTileEntityFromMetadata()
    {
        ForgeDirection direction = ForgeDirection.getOrientation(blockMetadata);
        switch (direction)
        {
            case DOWN:
                return worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            case WEST:
                return worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
            case EAST:
                return worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
            case NORTH:
                return worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
            case SOUTH:
                return worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
            case UP:
            default:
                return worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        }
    }

    public TransporterMode getMode()
    {
        return mode;
    }

    public void setMode(TransporterMode mode)
    {
        this.mode = mode;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
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
        tag.setByte(Names.NBT.MODE, (byte) mode.ordinal());
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
        mode = TransporterMode.values()[tag.getByte(Names.NBT.MODE)];
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
