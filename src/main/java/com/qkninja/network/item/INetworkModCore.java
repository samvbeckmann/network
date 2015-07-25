package com.qkninja.network.item;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Implement this interface on Items that should act as Nexus Cores.
 *
 * See {@link ItemItemCore} for an example implementation.
 *
 * @author QKninja
 */
public interface INetworkModCore
{
    /**
     * Called when the Nexus is ready to perform an operation in import mode.
     *
     * @param nexus The Nexus to which this core is bound.
     */
    void onNexusImportCall(TileEntityTransporter nexus);

    /**
     * Called when the Nexus is ready to perform an operation in export mode.
     *
     * @param nexus The Nexus to which this core is bound.
     */
    void onNexusExportCall(TileEntityTransporter nexus);

    /**
     * Called when the Nexus is ready to perform an operation in neutral mode.
     *
     * @param nexus The Nexus to which this core is bound.
     */
    void onNexusNeutralCall(TileEntityTransporter nexus);

    /**
     * @return An ItemStack containing one of this item.
     */
    ItemStack getItemStack();

    /**
     * Used to get the model that should be rendered when this core is active.
     * Called on render of a Nexus containing this core.
     *
     * @return model that should be rendered
     */
    ModelBase getModel();

    /**
     * Used to get the texture that should be rendered when this core is active.
     * Called on render of a Nexus containing this core.
     *
     * @return texture that should be bound to the model.
     */
    ResourceLocation getTexture();
}
