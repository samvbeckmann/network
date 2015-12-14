package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Messages;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Defines the Red LED ring item, used as a speed upgrade.
 *
 * @author QKninja
 */
public class ItemRedLED extends UpgradeBase
{
    public ItemRedLED()
    {
        super(Names.Items.RED_LED,
                new ModelUpgrade(),
                ResourceLocationHelper.getResourceLocation(Names.Models.RED_LED));
    }

    @Override
    public void onAdded(TileEntityTransporter te)
    {
        te.setDelay(1);
    }

    @Override
    public void onRemoved(TileEntityTransporter te)
    {
        te.setDelay(ConfigValues.transportDelay);
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean p_77624_4_)
    {
        list.add(I18n.format(Messages.Tooltips.UPGRADE));
        list.add(I18n.format(Messages.Tooltips.SPEED));
    }
}
