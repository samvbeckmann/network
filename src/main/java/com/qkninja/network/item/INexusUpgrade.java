package com.qkninja.network.item;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Defines an upgrade to a Nexus.
 * See {@link ItemRedLED} for an example implementation.
 *
 * @author QKninja
 */
public interface INexusUpgrade
{
    void onAdded(TileEntityTransporter te);

    void onRemoved(TileEntityTransporter te);

    ResourceLocation getTexture();

    ModelBase getModel();

    ItemStack getItemStack();
}
