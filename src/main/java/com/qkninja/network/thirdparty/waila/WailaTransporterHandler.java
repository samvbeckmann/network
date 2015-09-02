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
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * WAILA tooltip for the transporter.
 *
 * @author QKninja
 */
public class WailaTransporterHandler implements IWailaDataProvider
{
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack,
                                     List<String> currenttip,
                                     IWailaDataAccessor accessor,
                                     IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack,
                                     List<String> currenttip,
                                     IWailaDataAccessor accessor,
                                     IWailaConfigHandler config)
    {
        currenttip.add(I18n.format(Messages.Waila.WAILA_MODE, I18n.format(accessor.getNBTData().getString(MODE))));

        if (!accessor.getNBTData().getString(INVENTORY).equals(""))
        {
            currenttip.add(I18n.format(Messages.Waila.WAILA_INVENTORY, accessor.getNBTData().getString(INVENTORY)));
        }
        if (accessor.getNBTData().getInteger(FLUID_AMOUNT) > 0)
        {
            currenttip.add(I18n.format(Messages.Waila.WAILA_FLUID, accessor.getNBTData().getInteger(FLUID_AMOUNT),
                    accessor.getNBTData().getString(FLUID)));
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack,
                                     List<String> currenttip,
                                     IWailaDataAccessor accessor,
                                     IWailaConfigHandler config)
    {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player,
                                     TileEntity te,
                                     NBTTagCompound tag,
                                     World world,
                                     int x,
                                     int y,
                                     int z)
    {
        TileEntityTransporter tile = (TileEntityTransporter) te;

        tag.setString(MODE, ModeHelper.getStringFromMode(tile.getMode()));

        if (tile.getStackInSlot(0) != null)
            tag.setString(INVENTORY, tile.getStackInSlot(0).getDisplayName());
        else tag.setString(INVENTORY, "");

        FluidStack fluidStack = tile.getTankInfo(ForgeDirection.getOrientation(tile.getBlockMetadata()))[0].fluid;

        if (fluidStack != null)
        {
            tag.setString(FLUID, fluidStack.getLocalizedName());
            tag.setInteger(FLUID_AMOUNT, fluidStack.amount);
        } else
        {
            tag.setString(FLUID, "");
            tag.setInteger(FLUID_AMOUNT, 0);
        }

        return tag;
    }

    private static final String MODE = "mode";
    private static final String INVENTORY = "inventory";
    private static final String FLUID = "fluid";
    private static final String FLUID_AMOUNT = "fluidAmount";
}
