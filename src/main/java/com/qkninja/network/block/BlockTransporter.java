package com.qkninja.network.block;

import com.qkninja.network.init.ModItems;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Defines the block of a transporter (nexus)
 *
 * @author QKninja
 */
public class BlockTransporter extends BlockNetwork implements ITileEntityProvider
{
    private static final float DISTANCE_FROM_EDGE = 0.1875F;
    private static final float HEIGHT = 0.65625F;

    public BlockTransporter()
    {
        super();
        setBlockName(Names.Blocks.TRANSPORTER);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_)
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
    public int onBlockPlaced(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
    {
        return side;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
    {
        ForgeDirection direction = ForgeDirection.getOrientation(blockAccess.getBlockMetadata(x, y, z));

        switch (direction)
        {
            case DOWN:
                this.setBlockBounds(DISTANCE_FROM_EDGE, 1 - HEIGHT, DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, 1, 1 - DISTANCE_FROM_EDGE);
                break;
            case EAST:
                this.setBlockBounds(0, DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, HEIGHT, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE);
                break;
            case WEST:
                this.setBlockBounds(1 - HEIGHT, DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 1, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE);
                break;
            case SOUTH:
                this.setBlockBounds(DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 0, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, HEIGHT);
                break;
            case NORTH:
                this.setBlockBounds(DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 1 - HEIGHT, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, 1);
                break;
            case UP:
                this.setBlockBounds(DISTANCE_FROM_EDGE, 0, DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, HEIGHT, 1 - DISTANCE_FROM_EDGE);
                break;
        }
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
