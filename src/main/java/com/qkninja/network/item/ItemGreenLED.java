package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.item.ItemStack;

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
        // TODO
    }

    @Override
    public void onRemoved(TileEntityTransporter te)
    {
        // TODO
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(this);
    }
}
