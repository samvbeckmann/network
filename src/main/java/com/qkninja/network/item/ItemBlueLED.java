package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Messages;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Defines the Blue LED ring item, used as a 4x distance upgrade.
 *
 * @author QKninja
 */
public class ItemBlueLED extends UpgradeBase
{
    public ItemBlueLED()
    {
        super(Names.Items.BLUE_LED,
              new ModelUpgrade(),
              ResourceLocationHelper.getResourceLocation(Names.Models.BLUE_LED));
    }

    @Override
    public void onAdded(TileEntityTransporter te)
    {
        te.setConnectionDistance(ConfigValues.maxDistanceSq * 4);
    }

    @Override
    public void onRemoved(TileEntityTransporter te)
    {
        te.setConnectionDistance(ConfigValues.maxDistanceSq);
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
        list.add(I18n.format(Messages.Tooltips.RANGE));
    }
}
