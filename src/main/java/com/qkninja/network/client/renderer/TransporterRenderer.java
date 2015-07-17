package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.ModelTransporter;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Renderer for the transporter block
 *
 * @author QKninja
 */
public class TransporterRenderer extends TileEntitySpecialRenderer
{
    private final ModelTransporter model;

    public TransporterRenderer()
    {
        this.model = new ModelTransporter();
    }

    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        TileEntityTransporter transporter = (TileEntityTransporter) te;
        ForgeDirection direction = ForgeDirection.getOrientation(te.getBlockMetadata());
        if (direction == null) direction = ForgeDirection.UP;
        GL11.glPushMatrix();
        getTranslationFromDirection(direction, x, y, z);
        ResourceLocation textures = ResourceLocationHelper.getResourceLocation(Names.Models.TRANSPORTER + transporter.getMode().ordinal() + Names.Models.MODEL_SUFFIX);
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        GL11.glPushMatrix();
        getRotationFromDirection(direction);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    private void getTranslationFromDirection(ForgeDirection direction, double x, double y, double z)
    {
        switch (direction)
        {
            case DOWN:
                GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
                break;
            case NORTH:
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z - 0.5F);
                break;
            case SOUTH:
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 1.5F);
                break;
            case EAST:
                GL11.glTranslatef((float) x + 1.5F, (float) y + 0.5F, (float) z + 0.5F);
                break;
            case WEST:
                GL11.glTranslatef((float) x - 0.5F, (float) y + 0.5F, (float) z + 0.5F);
                break;
            case UP:
            default:
                GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
                break;
        }
    }

    private void getRotationFromDirection(ForgeDirection direction)
    {
        switch (direction)
        {
            case DOWN:
                GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
                break;
            case SOUTH:
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                break;
            case NORTH:
                GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                break;
            case WEST:
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                break;
            case EAST:
                GL11.glRotatef(-90F, 0.0F, 0.0F, 1.0F);
                break;
        }
    }
}
