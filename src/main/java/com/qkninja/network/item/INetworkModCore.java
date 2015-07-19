package com.qkninja.network.item;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.item.ItemStack;

/**
 * dilithium-transportation, class made on 2015-07-18
 *
 * @author Sam Beckmann
 */
public interface INetworkModCore
{
    /**
     * Called when
     */
    void onNexusImportCall(TileEntityTransporter nexus);

    void onNexusExportCall(TileEntityTransporter nexus);

    void onNexusNeutralCall(TileEntityTransporter nexus);

    ItemStack getItemStack();
}
