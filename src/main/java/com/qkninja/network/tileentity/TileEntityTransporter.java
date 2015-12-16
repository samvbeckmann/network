package com.qkninja.network.tileentity;

import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.item.INetworkModCore;
import com.qkninja.network.item.INexusUpgrade;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Names;
import com.qkninja.network.utility.LogHelper;
import com.qkninja.network.utility.ParticleHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Defines a Tile Entity of a transporter (nexus)
 *
 * @author QKninja
 */
public class TileEntityTransporter extends TileEntityNetwork implements IInventory, IFluidHandler
{
    private int counter;
    private ItemStack inventory;
    private List<DistanceHandler> exportLocations = new ArrayList<DistanceHandler>();
    private Stack<INexusUpgrade> upgrades = new Stack<INexusUpgrade>();
    private TransporterMode mode = TransporterMode.NEUTRAL;
    private int startingRotation = new Random().nextInt(90);
    private INetworkModCore activeCore;
    private int delay = ConfigValues.transportDelay;
    private int connectionDistance = ConfigValues.maxDistanceSq;
    private boolean highCapacity = false;
    private FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);

    public TileEntityTransporter()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        if (counter < delay) counter++;

        if (counter >= delay)
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

        if (!worldObj.isRemote && activeCore != null)
        {

            if (counter >= delay)
            {
                switch (mode)
                {
                    case IMPORT:
                        activeCore.onNexusImportCall(this);
                        break;
                    case EXPORT:
                        activeCore.onNexusExportCall(this);
                        break;
                    case NEUTRAL:
                        activeCore.onNexusNeutralCall(this);
                        break;
                }
            }
        } else if (worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 0)
        {
            DistanceHandler[] tempLocs = new DistanceHandler[exportLocations.size()];
            tempLocs = exportLocations.toArray(tempLocs);

            if (tempLocs.length > 0)
                ParticleHelper.spawnSpark(worldObj, xCoord, yCoord, zCoord, tempLocs);
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
            if (handler.getDistance() > connectionDistance)
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

    /**
     * Resets the counter until the next interaction.
     */
    public void resetCounter()
    {
        counter = 0;
    }

    /**
     * When called, cycles the Nexus to it's next mode
     *
     * @return {@link com.qkninja.network.tileentity.TileEntityTransporter.TransporterMode} the Nexus is now in.
     */
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

    /**
     * Gets The tile entity that the Nexus would interact with, based on it's orientation.
     *
     * @return Tile tile entity that the Nexus would interact with, or null if it doesn't exist.
     */
    @Nullable
    public TileEntity getAttachedTileEntity()
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

    public INetworkModCore getActiveCore()
    {
        return activeCore;
    }

    public void setActiveCore(INetworkModCore activeCore)
    {
        this.activeCore = activeCore;
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
        return 64;
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

    public int getStartingRotation()
    {
        return startingRotation;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public int getConnectionDistance()
    {
        return connectionDistance;
    }

    public void setConnectionDistance(int connectionDistance)
    {
        this.connectionDistance = connectionDistance;
    }

    public boolean isHighCapacity()
    {
        return highCapacity;
    }

    public void setHighCapacity(boolean highCapacity)
    {
        this.highCapacity = highCapacity;
    }

    public Stack<INexusUpgrade> getUpgrades()
    {
        return upgrades;
    }

    public boolean addUpgrade(INexusUpgrade upgrade)
    {
        if (upgrades.size() >= ConfigValues.maxUpgradeNumber || upgrades.contains(upgrade))
            return false;
        else
        {
            upgrades.push(upgrade);
            upgrade.onAdded(this);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return true;
        }
    }

    public INexusUpgrade removeTopUpgrade()
    {
        if (upgrades.isEmpty())
            return null;
        else
        {
            INexusUpgrade upgrade = upgrades.pop();
            upgrade.onRemoved(this);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
            return upgrade;
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item)
    {
        return true;
    }



    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        counter = tag.getShort(Names.NBT.COUNTER);
        this.setDelay(tag.getShort(Names.NBT.DELAY));
        this.setConnectionDistance(tag.getShort(Names.NBT.MAX_DISTANCE));
        this.setHighCapacity(tag.getBoolean(Names.NBT.HIGH_CAPACITY));
        tank.readFromNBT(tag);

        NBTTagCompound invCompound = (NBTTagCompound) tag.getTag(Names.NBT.ITEMS);
        if (invCompound != null)
            inventory = ItemStack.loadItemStackFromNBT(invCompound);
        else
            inventory = null;


        this.readSyncableData(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setShort(Names.NBT.COUNTER, (short) counter);
        tag.setShort(Names.NBT.DELAY, (short) this.getDelay());
        tag.setShort(Names.NBT.MAX_DISTANCE, (short) this.getConnectionDistance());
        tag.setBoolean(Names.NBT.HIGH_CAPACITY, this.isHighCapacity());
        tank.writeToNBT(tag);

        if (inventory != null)
        {
            NBTTagCompound invCompound = new NBTTagCompound();
            inventory.writeToNBT(invCompound);
            tag.setTag(Names.NBT.ITEMS, invCompound);
        }

        writeSyncableData(tag);
    }

    private NBTTagCompound writeSyncableData(NBTTagCompound tag)
    {
        if (activeCore != null)
        {
            NBTTagCompound core = new NBTTagCompound();
            activeCore.getItemStack().writeToNBT(core);
            tag.setTag(Names.NBT.CORE, core);
        }

        tag.setByte(Names.NBT.MODE, (byte) mode.ordinal());

        NBTTagList tagList = new NBTTagList();
        for (DistanceHandler exportLocation : exportLocations)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            exportLocation.writeToNBT(tagCompound);
            tagList.appendTag(tagCompound);
        }
        tag.setTag(Names.NBT.LOCATIONS, tagList);

        NBTTagList upgradeList = new NBTTagList();
        for (INexusUpgrade upgrade : this.getUpgrades())
        {
            NBTTagCompound compound = new NBTTagCompound();
            upgrade.getItemStack().writeToNBT(compound);
            upgradeList.appendTag(compound);
        }
        tag.setTag(Names.NBT.UPGRADES, upgradeList);

        return tag;
    }

    private void readSyncableData(NBTTagCompound tag)
    {
        NBTTagCompound coreCompound = (NBTTagCompound) tag.getTag(Names.NBT.CORE);
        if (coreCompound != null)
        {
            ItemStack coreItemStack = ItemStack.loadItemStackFromNBT(coreCompound);
            activeCore = (INetworkModCore) coreItemStack.getItem();
        } else
            activeCore = null;

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

        upgrades = new Stack<INexusUpgrade>();
        NBTTagList upgradeList= tag.getTagList(Names.NBT.UPGRADES, 10);
        for (int i = 0; i < upgradeList.tagCount(); i++)
        {
            NBTTagCompound compound = upgradeList.getCompoundTagAt(i);
            upgrades.push((INexusUpgrade) ItemStack.loadItemStackFromNBT(compound).getItem());
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

    /* IFluidHandler methods */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { tank.getInfo()};
    }

    /**
     * Possible modes for a transporter to be in.
     */
    public enum TransporterMode
    {
        NEUTRAL, IMPORT, EXPORT
    }
}
