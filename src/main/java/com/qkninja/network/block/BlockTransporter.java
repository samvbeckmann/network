package com.qkninja.network.block;

import com.qkninja.network.init.ModItems;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Defines the block of a transporter (nexus)
 *
 * @author QKninja
 */
public class BlockTransporter extends BlockNetwork implements ITileEntityProvider
{
    public BlockTransporter()
    {
        super();
        setBlockName(Names.Blocks.TRANSPORTER);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityTransporter();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        if (!world.isRemote)
        {
            if (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() == ModItems.hyperspanner)
            {
                ItemHyperspanner hyperspanner = (ItemHyperspanner) entityPlayer.getHeldItem().getItem();
                hyperspanner.storeOrLinkCoordinates(world, entityPlayer.getHeldItem(), x, y, z);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
