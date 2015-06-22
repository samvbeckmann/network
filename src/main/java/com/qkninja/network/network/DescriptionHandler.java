package com.qkninja.network.network;

import com.qkninja.network.Network;
import com.qkninja.network.reference.Names;
import com.qkninja.network.reference.Reference;
import com.qkninja.network.tileentity.TileEntityNetwork;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.tileentity.TileEntity;

/**
 * dilithium-transportation, class made on 6/22/2015.
 *
 * @author Sam Beckmann
 */
public class DescriptionHandler extends SimpleChannelInboundHandler<FMLProxyPacket>
{
    public static final String CHANNEL = Reference.MOD_ID + Names.Channels.DESCRIPTION_CHANNEL;

    static
    {
        NetworkRegistry.INSTANCE.newChannel(CHANNEL, new DescriptionHandler());
    }

    public static void init()
    {
        // NOOP
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket msg) throws Exception
    {
        ByteBuf buf = msg.payload();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        TileEntity te = Network.proxy.getClientPlayer().worldObj.getTileEntity(x, y, z);
        if (te instanceof TileEntityNetwork)
        {
            ((TileEntityNetwork) te).readFromPacket(buf);
        }
    }
}
