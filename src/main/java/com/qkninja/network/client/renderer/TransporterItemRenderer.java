package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.ModelTransporter;
import com.qkninja.network.reference.Names;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


/**
 * dilithium-transportation, class made on 2015-07-13
 *
 * @author Sam Beckmann
 */
public class TransporterItemRenderer implements IItemRenderer
{
    protected ModelTransporter model;
    ResourceLocation texture = ResourceLocationHelper.getResourceLocation(Names.Models.TRANSPORTER + "0" + Names.Models.MODEL_SUFFIX);



    public TransporterItemRenderer()
    {
        model = new ModelTransporter();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.0F, 2.0F, 0.0F);
        GL11.glScalef(1.6F, 1.6F, 1.6F);

        switch (type)
        {
            case EQUIPPED:
                GL11.glTranslatef(0.3F, 0.3F, 0.3F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.3F, 0.7F, 0.3F);
        }

        GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
