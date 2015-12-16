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
     * @param nexus The Nexus to which this itemCore is bound.
     */
    void onNexusImportCall(TileEntityTransporter nexus);

    /**
     * Called when the Nexus is ready to perform an operation in export mode.
     *
     * @param nexus The Nexus to which this itemCore is bound.
     */
    void onNexusExportCall(TileEntityTransporter nexus);

    /**
     * Called when the Nexus is ready to perform an operation in neutral mode.
     *
     * @param nexus The Nexus to which this itemCore is bound.
     */
    void onNexusNeutralCall(TileEntityTransporter nexus);

    /**
     * @return An ItemStack containing one of this item.
     */
    ItemStack getItemStack();

    /**
     * Used to get the model that should be rendered when this itemCore is active.
     * Called on render of a Nexus containing this itemCore.
     *
     * @return model that should be rendered
     */
    ModelBase getModel();

    /**
     * Used to get the texture that should be rendered when this itemCore is active.
     * Called on render of a Nexus containing this itemCore.
     *
     * @return texture that should be bound to the model.
     */
    ResourceLocation getTexture();

    /**
     * @return if items should be allowed to be transported to a nexus with this core.
     */
    boolean canHandleItems();

    /**
     * @return if fluids should be allowed to be transported to a nexus with this core.
     */
    boolean canHandleFluids();

    /**
     * @return if the core overrides redstone inputs to the Nexus.
     * Returning true will prevent the Nexus from being turned off when receiving a
     * redstone signal when this core is active in a Nexus.
     */
    boolean overridesRedstone();
}
