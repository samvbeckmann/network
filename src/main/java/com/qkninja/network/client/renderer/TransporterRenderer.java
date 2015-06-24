package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.TransporterModel;
import com.qkninja.network.reference.Names;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * dilithium-transportation, class made on 6/23/2015.
 *
 * @author Sam Beckmann
 */
public class TransporterRenderer extends TileEntitySpecialRenderer
{
    private final TransporterModel model;

    public TransporterRenderer()
    {
        this.model = new TransporterModel();
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        ResourceLocation textures = ResourceLocationHelper.getResourceLocation(Names.Models.TRANSPORTER + te.getBlockMetadata() + Names.Models.MODEL_SUFFIX);
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /* Perhaps makes the lighting right */
//    private void adjustLightFixture(World world, int i, int j, int k, Block block)
//    {
//        Tessellator tess = Tessellator.instance;
//        float brightness = block.getLightValue(world, i, j, k);
//        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
//        int modulousModifier = skyLight % 65536;
//        int divModifier = skyLight / 65536;
//        tess.setColorOpaque_F(brightness, brightness, brightness);
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
//    }
}
