package com.qkninja.network.item;

import com.qkninja.network.client.model.ModelUpgrade;
import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * dilithium-transportation, class made on 2015-07-24
 *
 * @author Sam Beckmann
 */
public class ItemRedLED extends ItemNetwork implements INexusUpgrade
{
    public ItemRedLED()
    {
        super();
        setUnlocalizedName(Names.Items.RED_LED);
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
    public ResourceLocation getTexture()
    {
        return ResourceLocationHelper.getResourceLocation(Names.Models.RED_LED);
    }

    @Override
    public ModelBase getModel()
    {
        return new ModelUpgrade();
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(this);
    }
}
