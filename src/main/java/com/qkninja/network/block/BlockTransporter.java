package com.qkninja.network.block;

import com.qkninja.network.client.particle.EntityFXSpark;
import com.qkninja.network.init.ModItems;
import com.qkninja.network.item.INetworkModCore;
import com.qkninja.network.item.INexusUpgrade;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Random;

/**
 * Defines the block of a transporter (nexus)
 *
 * @author QKninja
 */
public class BlockTransporter extends BlockNetwork implements ITileEntityProvider
{
    private static final float DISTANCE_FROM_EDGE = 0.1875F;
    private static final float CORE_HEIGHT = 0.65625F;
    private static final float NEXUS_HEIGHT = 0.25F;

    public BlockTransporter()
    {
        super();
        this.setBlockName(Names.Blocks.TRANSPORTER);
        this.setHardness(2.0F);
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

            if (entityPlayer.getHeldItem() == null)
            {
                if (entityPlayer.isSneaking())
                {
                    INexusUpgrade up = te.removeTopUpgrade();
                    if (up != null)
                    {
                        if (!entityPlayer.capabilities.isCreativeMode)
                            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, up.getItemStack());
                    } else
                    {
                        INetworkModCore core = te.getActiveCore();
                        if (core != null)
                        {
                            if (!entityPlayer.capabilities.isCreativeMode)
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, core.getItemStack());
                            te.setActiveCore(null);
                        }
                    }
                }
            } else if (entityPlayer.getHeldItem().getItem() == ModItems.hyperspanner)
            {
                ItemHyperspanner hyperspanner = (ItemHyperspanner) entityPlayer.getHeldItem().getItem();
                hyperspanner.storeOrLinkCoordinates(world, entityPlayer.getHeldItem(), x, y, z);
                return true;

            } else if (entityPlayer.getHeldItem().getItem() instanceof INetworkModCore && te.getActiveCore() == null)
            {
                te.setActiveCore((INetworkModCore) entityPlayer.getHeldItem().getItem());
                if (!entityPlayer.capabilities.isCreativeMode)
                    entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);

            } else if (entityPlayer.getHeldItem().getItem() instanceof INexusUpgrade)
            {
                if (te.addUpgrade((INexusUpgrade) entityPlayer.getHeldItem().getItem()) && !entityPlayer.capabilities.isCreativeMode)
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
        TileEntity te = world.getTileEntity(x, y, z);
        AxisAlignedBB bb = null;

        if (te instanceof TileEntityTransporter) // Should always be true
        {
            float height = hasCore((TileEntityTransporter) te) ? CORE_HEIGHT : NEXUS_HEIGHT;

            switch (direction)
            {
                case DOWN:
                    bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, 1 - height + y, DISTANCE_FROM_EDGE + z, 1 - DISTANCE_FROM_EDGE + x, 1 + y, 1 - DISTANCE_FROM_EDGE + z);
                    break;
                case EAST:
                    bb = AxisAlignedBB.getBoundingBox(x, DISTANCE_FROM_EDGE + y, DISTANCE_FROM_EDGE + z, height + x, 1 - DISTANCE_FROM_EDGE + y, 1 - DISTANCE_FROM_EDGE + z);
                    break;
                case WEST:
                    bb = AxisAlignedBB.getBoundingBox(1 - height + x, DISTANCE_FROM_EDGE + y, DISTANCE_FROM_EDGE + z, 1 + x, 1 - DISTANCE_FROM_EDGE + y, 1 - DISTANCE_FROM_EDGE + z);
                    break;
                case SOUTH:
                    bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, DISTANCE_FROM_EDGE + y, z, 1 - DISTANCE_FROM_EDGE + x, 1 - DISTANCE_FROM_EDGE + y, height + z);
                    break;
                case NORTH:
                    bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, DISTANCE_FROM_EDGE + y, 1 - height + z, 1 - DISTANCE_FROM_EDGE + x, 1 - DISTANCE_FROM_EDGE + y, 1 + z);
                    break;
                case UP:
                default:
                    bb = AxisAlignedBB.getBoundingBox(DISTANCE_FROM_EDGE + x, y, DISTANCE_FROM_EDGE + z, 1 - DISTANCE_FROM_EDGE + x, height + y, 1 - DISTANCE_FROM_EDGE + z);
                    break;
            }
        }

        return bb;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
    {
        ForgeDirection direction = ForgeDirection.getOrientation(blockAccess.getBlockMetadata(x, y, z));
        float height = hasCore((TileEntityTransporter) blockAccess.getTileEntity(x, y, z)) ? CORE_HEIGHT : NEXUS_HEIGHT;

        switch (direction)
        {
            case DOWN:
                this.setBlockBounds(DISTANCE_FROM_EDGE, 1 - height, DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, 1, 1 - DISTANCE_FROM_EDGE);
                break;
            case EAST:
                this.setBlockBounds(0, DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, height, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE);
                break;
            case WEST:
                this.setBlockBounds(1 - height, DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 1, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE);
                break;
            case SOUTH:
                this.setBlockBounds(DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 0, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, height);
                break;
            case NORTH:
                this.setBlockBounds(DISTANCE_FROM_EDGE, DISTANCE_FROM_EDGE, 1 - height, 1 - DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, 1);
                break;
            case UP:
                this.setBlockBounds(DISTANCE_FROM_EDGE, 0, DISTANCE_FROM_EDGE, 1 - DISTANCE_FROM_EDGE, height, 1 - DISTANCE_FROM_EDGE);
                break;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd)
    {
        if (rnd.nextFloat() > .75 && hasCore((TileEntityTransporter) world.getTileEntity(x, y, z)))
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
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        TileEntityTransporter te = (TileEntityTransporter) world.getTileEntity(x, y, z);

        if (te.getActiveCore() != null)
        {
            EntityItem core = new EntityItem(world, x, y, z, te.getActiveCore().getItemStack());
            world.spawnEntityInWorld(core);
        }

        if (!te.getUpgrades().isEmpty())
            for (INexusUpgrade upgrade: te.getUpgrades())
            {
                EntityItem entityItem = new EntityItem(world, x, y, z, upgrade.getItemStack());
                world.spawnEntityInWorld(entityItem);
            }

        super.breakBlock(world, x, y, z, block, meta);
    }

    /**
     * Determines whether or not a given Nexus has a currently active Core.
     */
    private boolean hasCore(TileEntityTransporter te)
    {
        return te != null && te.getActiveCore() != null;
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
