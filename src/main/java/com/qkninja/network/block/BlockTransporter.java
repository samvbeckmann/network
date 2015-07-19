package com.qkninja.network.block;

import com.qkninja.network.client.particle.EntityFXSpark;
import com.qkninja.network.init.ModItems;
import com.qkninja.network.item.INetworkModCore;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

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
        this.setLightLevel(0.5F);
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
            TileEntityTransporter te = (TileEntityTransporter) world.getTileEntity(x, y, z);

            if (entityPlayer.getHeldItem() == null && entityPlayer.isSneaking())
            {
                INetworkModCore core = te.getActiveCore();
                if (core != null)
                {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, core.getItemStack());
                    te.setActiveCore(null);
                }

            }
            else if (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() == ModItems.hyperspanner)
            {
                ItemHyperspanner hyperspanner = (ItemHyperspanner) entityPlayer.getHeldItem().getItem();
                hyperspanner.storeOrLinkCoordinates(world, entityPlayer.getHeldItem(), x, y, z);
                return true;
            }
            else if (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof INetworkModCore && te.getActiveCore() == null)
            {
                te.setActiveCore((INetworkModCore) entityPlayer.getHeldItem().getItem());
                entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
            }
        }
        return false;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float par6, float par7, float par8, int par9)
    {
        return side;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        ForgeDirection direction = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
        AxisAlignedBB bb;

        switch (direction)
        {
            case DOWN:
                bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, 1 - HEIGHT + y, DISTANCE_FROM_EDGE + z, 1 - DISTANCE_FROM_EDGE + x, 1 + y, 1 - DISTANCE_FROM_EDGE + z);
                break;
            case EAST:
                bb = AxisAlignedBB.getBoundingBox(x, DISTANCE_FROM_EDGE + y, DISTANCE_FROM_EDGE + z, HEIGHT + x, 1 - DISTANCE_FROM_EDGE + y, 1 - DISTANCE_FROM_EDGE + z);
                break;
            case WEST:
                bb = AxisAlignedBB.getBoundingBox(1 - HEIGHT + x, DISTANCE_FROM_EDGE + y, DISTANCE_FROM_EDGE + z, 1 + x, 1 - DISTANCE_FROM_EDGE + y, 1 - DISTANCE_FROM_EDGE + z);
                break;
            case SOUTH:
                bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, DISTANCE_FROM_EDGE + y, z, 1 - DISTANCE_FROM_EDGE + x, 1 - DISTANCE_FROM_EDGE + y, HEIGHT + z);
                break;
            case NORTH:
                bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, DISTANCE_FROM_EDGE + y, 1 - HEIGHT + z, 1 - DISTANCE_FROM_EDGE + x, 1 - DISTANCE_FROM_EDGE + y, 1 + z);
                break;
            case UP:
            default:
                bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, y, DISTANCE_FROM_EDGE + z, 1 - DISTANCE_FROM_EDGE + x, HEIGHT + y, 1 - DISTANCE_FROM_EDGE + z);
                break;
        }
        return bb;
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

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd)
    {
        if (rnd.nextFloat() > .75)
        {
            float spawnX = (float) (x + 0.25 + rnd.nextFloat() / 2);
            float spawnY = (float) (y + 0.25 + rnd.nextFloat() / 2);
            float spawnZ = (float) (z + 0.25 + rnd.nextFloat() / 2);
            int lifespan = 40 + rnd.nextInt(40);

            EntityFX spark = new EntityFXSpark(world, spawnX, spawnY, spawnZ, 0, 0, 0, lifespan, 0.5F, true);
            Minecraft.getMinecraft().effectRenderer.addEffect(spark);
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
