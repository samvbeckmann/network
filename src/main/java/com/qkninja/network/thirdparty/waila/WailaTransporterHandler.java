package com.qkninja.network.thirdparty.waila;

import com.qkninja.network.reference.Messages;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ModeHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * dilithium-transportation, class made on 2015-06-29
 *
 * @author Sam Beckmann
 */
public class WailaTransporterHandler implements IWailaDataProvider
{
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntityTransporter te = (TileEntityTransporter) accessor.getTileEntity();

        currenttip.add(I18n.format(Messages.Waila.WAILA_MODE, I18n.format(accessor.getNBTData().getString(MODE))));

        if (!accessor.getNBTData().getString(INVENTORY).equals(""))
        {
            currenttip.add(I18n.format(Messages.Waila.WAILA_INVENTORY, accessor.getNBTData().getString(INVENTORY)));
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        TileEntityTransporter tile = (TileEntityTransporter) te;

        tag.setString(MODE, ModeHelper.getStringFromMode(tile.getMode()));

        if (tile.getStackInSlot(0) != null)
            tag.setString(INVENTORY, tile.getStackInSlot(0).getDisplayName());
        else tag.setString(INVENTORY, "");

        return tag;
    }

    private static final String MODE = "mode";
    private static final String INVENTORY = "inventory";
}
