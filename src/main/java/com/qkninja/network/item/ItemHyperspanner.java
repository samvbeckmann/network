package com.qkninja.network.item;

import com.qkninja.network.init.ModBlocks;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Messages;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.LogHelper;
import com.qkninja.network.utility.TransporterMode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

/**
 * Defines the hyperspanner tool.
 *
 * @author QKninja
 */
public class ItemHyperspanner extends ItemNetwork
{
    private IIcon[] icons;

    public ItemHyperspanner()
    {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Names.Items.HYPERSPANNER);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, 3);
        return this.icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.icons = new IIcon[4];

        for (int i = 0; i < 4; i++)
        {
            this.icons[i] = iconRegister.registerIcon(String.format("%s_%d", getUnwrappedUnlocalizedName(this.getUnlocalizedName()), i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (entityPlayer.isSneaking() && !world.isRemote)
        {
            MovingObjectPosition movingObjectPosition = this.getMovingObjectPositionFromPlayer(world, entityPlayer, true);

            if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingObjectPosition.blockX;
                int j = movingObjectPosition.blockY;
                int k = movingObjectPosition.blockZ;

                if (world.getBlock(i, j, k).equals(ModBlocks.transporter))
                {
                    TileEntity tileEntity = world.getTileEntity(i, j, k);
                    if (tileEntity != null && tileEntity instanceof TileEntityTransporter)
                    {
                        TileEntityTransporter te = (TileEntityTransporter) tileEntity;
                        TransporterMode mode = te.incrementMode();
                        if (ConfigValues.debugMode) LogHelper.info("New Block Mode: " + mode);
                    }
                    return itemStack;
                }
            }
            this.setDamage(itemStack, (itemStack.getItemDamage() + 2) % 4);
        }
        return itemStack;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
    {
        list.add(I18n.format(Messages.Tooltips.MODE, I18n.format(itemStack.getItemDamage() <= 1 ? Messages.Tooltips.ONE_WAY : Messages.Tooltips.TWO_WAY)));
        if (itemStack.getItemDamage() % 2 != 0)
        {
            list.add(I18n.format(Messages.Tooltips.HAS_DATA));
        }
    }

    /**
     * If the item has no coordinates stored, stores the coordinates of the current block.
     * If coordinates are stored, attempts to link the two coordinates.
     *
     * @param world the world
     * @param itemStack the hyperspanner used
     * @param x xCoord of block used on
     * @param y yCoord of block used on
     * @param z zCoord of block used on
     *
     * @return 1 if coordinates were stored, 2 if a link was attempted
     */
    public int storeOrLinkCoordinates(World world, ItemStack itemStack, int x, int y, int z)
    {
        if (itemStack.stackTagCompound == null)
            itemStack.setTagCompound(new NBTTagCompound());

        if (itemStack.getItemDamage() % 2 == 0)
        {
            itemStack.stackTagCompound.setInteger(Names.NBT.X_COORD, x);
            itemStack.stackTagCompound.setInteger(Names.NBT.Y_COORD, y);
            itemStack.stackTagCompound.setInteger(Names.NBT.Z_COORD, z);
            itemStack.setItemDamage(itemStack.getItemDamage() + 1);
            return 1;
        } else
        {
            int storedX = itemStack.stackTagCompound.getInteger(Names.NBT.X_COORD);
            int storedY = itemStack.stackTagCompound.getInteger(Names.NBT.Y_COORD);
            int storedZ = itemStack.stackTagCompound.getInteger(Names.NBT.Z_COORD);

            TileEntityTransporter te = (TileEntityTransporter) world.getTileEntity(storedX, storedY, storedZ);
            if (te != null)
            {
                if (te.addDestination(world, x, y, z))
                {
//                    world.markBlockForUpdate(x, y, z);
                }
            }

            if (itemStack.getItemDamage() >= 2)
            {
                TileEntityTransporter otherTE = (TileEntityTransporter) world.getTileEntity(x, y, z);
                if (otherTE.addDestination(world, storedX, storedY, storedZ));
//                    world.markBlockForUpdate(storedX, storedY, storedZ);
            }

            itemStack.setItemDamage(itemStack.getItemDamage() - 1);
            return 2;
        }
    }
}
