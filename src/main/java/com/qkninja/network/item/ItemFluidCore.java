package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelCrystal;
import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Collections;

/**
 * Defines a core for transporting Fluids.
 *
 * @author Sam Beckmann
 */
public class ItemFluidCore extends ItemNetwork implements INetworkModCore
{
    private ResourceLocation texture;
    private ModelBase model;

    public ItemFluidCore()
    {
        super();
        setUnlocalizedName(Names.Items.FLUID_CORE);
        this.texture = ResourceLocationHelper.getResourceLocation(Names.Models.FLUID_CRYSTAL);
        this.model = new ModelCrystal();
    }

    @Override
    public void onNexusImportCall(TileEntityTransporter nexus)
    {
        TileEntity tile = nexus.getAttachedTileEntity();

        if (tile != null && tile instanceof IFluidHandler)
        {
            IFluidHandler inv = (IFluidHandler) tile;

            ForgeDirection nexusOrientation = ForgeDirection.getOrientation(nexus.getBlockMetadata());
            FluidTankInfo[] tankInfo = nexus.getTankInfo(nexusOrientation);
            FluidStack nexusFluid = tankInfo[0].fluid;

            FluidStack drained = null;

            if (nexusFluid == null) // no fluid
            {
                drained = inv.drain(nexusOrientation, 100, true);

            } else // has fluid
            {
                int remainingSpace = tankInfo[0].capacity - nexusFluid.amount;
                if (remainingSpace != 0)
                {
                    int toDrain = Math.min(remainingSpace, 100);
                    drained = inv.drain(nexusOrientation, new FluidStack(nexusFluid.getFluid(), toDrain), true);
                }
            }

            if (drained != null && drained.amount > 0)
            {
                nexus.fill(nexusOrientation, drained, true);
                nexus.resetCounter();
                return;
            }

            attemptTeleport(nexus, nexusOrientation);

        }


    }

    @Override
    public void onNexusExportCall(TileEntityTransporter nexus)
    {
        ForgeDirection nexusOrientation = ForgeDirection.getOrientation(nexus.getBlockMetadata());
        FluidTankInfo[] tankInfo = nexus.getTankInfo(nexusOrientation);
        FluidStack nexusFluid = tankInfo[0].fluid;

        if (nexusFluid != null && nexusFluid.amount > 0)
        {
            TileEntity tile = nexus.getAttachedTileEntity();
            if (tile instanceof IFluidHandler)
            {
                IFluidHandler inv = (IFluidHandler) tile;
                if (inv.canFill(nexusOrientation, nexusFluid.getFluid()))
                {
                    FluidStack toFill = new FluidStack(nexusFluid.getFluid(), Math.min(nexusFluid.amount, 100));
                    int filled = inv.fill(nexusOrientation, toFill, true);
                    nexus.drain(nexusOrientation, filled, true);

                    if (filled != 0)
                    {
                        nexus.resetCounter();
                        return;
                    }
                }
            }
            attemptTeleport(nexus, nexusOrientation);
        }
    }

    @Override
    public void onNexusNeutralCall(TileEntityTransporter nexus)
    {
        attemptTeleport(nexus, ForgeDirection.getOrientation(nexus.getBlockMetadata()));
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(this);
    }

    @Override
    public ModelBase getModel()
    {
        return model;
    }

    @Override
    public ResourceLocation getTexture()
    {
        return texture;
    }

    private boolean attemptTeleport(TileEntityTransporter nexus, ForgeDirection orientation)
    {
        FluidTankInfo[] tankInfo = nexus.getTankInfo(orientation);
        FluidStack nexusFluid = tankInfo[0].fluid;
        if (nexusFluid != null)
        {
            Collections.sort(nexus.getExportLocations());
            for (DistanceHandler loc : nexus.getExportLocations())
            {
                TileEntity tile = nexus.getWorldObj().getTileEntity(loc.getX(), loc.getY(), loc.getZ());
                if (tile instanceof TileEntityTransporter) // should always be true
                {
                    TileEntityTransporter transporter = (TileEntityTransporter) tile;
                    FluidStack transporterFluid = transporter.getTankInfo(orientation)[0].fluid;
                    if (transporterFluid == null || transporterFluid.containsFluid(nexusFluid))
                    {
                        FluidStack toFill = new FluidStack(nexusFluid.getFluid(), Math.min(nexusFluid.amount, 100));
                        int filled = transporter.fill(orientation, toFill, true);
                        nexus.drain(orientation, filled, true);
                        if (filled > 0)
                        {
                            transporter.resetCounter();
                            nexus.resetCounter();
                        }
                    }
                }
            }
        }
        return false;
    }
}
