package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.item.ItemStack;

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
}
