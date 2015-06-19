package com.qkninja.network.init;

import com.qkninja.network.block.BlockNetwork;
import com.qkninja.network.block.BlockTransporter;
import com.qkninja.network.reference.Names;
import com.qkninja.network.reference.Reference;
import com.qkninja.network.tileentity.TileEntityTransporter;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Initializes the blocks and tile entities in the mod.
 *
 * @author QKninja
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockNetwork transporter = new BlockTransporter();

    public static void init()
    {
        GameRegistry.registerBlock(transporter, Names.Blocks.TRANSPORTER);

        GameRegistry.registerTileEntity(TileEntityTransporter.class, Names.TileEntities.TILE_TRANSPORTER);
    }
}
