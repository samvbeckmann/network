package com.qkninja.network.item;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * dilithium-transportation, class made on 2015-07-23
 *
 * @author Sam Beckmann
 */
public interface INexusUpgrade
{
    void onAdded(TileEntityTransporter te);

    void onRemoved(TileEntityTransporter te);

    ResourceLocation getTexture();

    ModelBase getModel();

    ItemStack getItemStack();
}
