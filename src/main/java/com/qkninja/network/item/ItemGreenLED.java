package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.Messages;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Defines the Green LED ring item, used as an efficiency upgrade.
 *
 * @author QKninja
 */
public class ItemGreenLED extends UpgradeBase
{
    public ItemGreenLED()
    {
        super(Names.Items.GREEN_LED,
                new ModelUpgrade(),
                ResourceLocationHelper.getResourceLocation(Names.Models.GREEN_LED));
    }
    @Override
    public void onAdded(TileEntityTransporter te)
    {
        te.setHighCapacity(true);
    }

    @Override
    public void onRemoved(TileEntityTransporter te)
    {
        te.setHighCapacity(false);
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_)
    {
        list.add(I18n.format(Messages.Tooltips.UPGRADE));
        list.add(I18n.format(Messages.Tooltips.CAPACITY));
    }
}
