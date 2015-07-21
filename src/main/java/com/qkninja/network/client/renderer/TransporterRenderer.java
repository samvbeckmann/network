package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.ModelCrystal;
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
 * Renderer for the transporter block and the attached core.
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

    /**
     * Renders both the nexus frame and whatever core is attached.
     */
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        TileEntityTransporter transporter = (TileEntityTransporter) te;
        ForgeDirection direction = ForgeDirection.getOrientation(te.getBlockMetadata());
        if (direction == null) direction = ForgeDirection.UP;

        GL11.glPushMatrix();
        setTranslationFromDirection(direction, x, y, z);
        ResourceLocation texture = ResourceLocationHelper.getResourceLocation(Names.Models.TRANSPORTER + transporter.getMode().ordinal() + Names.Models.MODEL_SUFFIX);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glPushMatrix();
        setRotationFromDirection(direction);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        if (transporter.getActiveCore() != null)
        {
            texture = transporter.getActiveCore().getTexture();
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            setCoreTranslationFromTime(transporter);
            setCoreRotationFromTime(transporter);
            transporter.getActiveCore().getModel().render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /**
     * Sets the transloation for the renderer based on the orientation of the Nexus
     *
     * @param direction Side of block on which the Nexus is sitting.
     * @param x xCoord
     * @param y yCoord
     * @param z zCoord
     */
    private void setTranslationFromDirection(ForgeDirection direction, double x, double y, double z)
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

    /**
     * Sets the rotation of the rendered based on the orientation of the Nexus
     *
     * @param direction Side of block on which the Nexus is sitting.
     */
    private void setRotationFromDirection(ForgeDirection direction)
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

    /**
     * Sets the rotation of core, which depends upon the current world time.
     *
     * @param te Nexus being rendered
     */
    private void setCoreRotationFromTime(TileEntityTransporter te)
    {
        long offsetTime = te.getWorldObj().getTotalWorldTime() + te.getStartingRotation();
        float rotation = (float) offsetTime % 360;
        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Sets the translation of the core, which depends upon the current world time.
     *
     * @param te Nexus being rendered
     */
    private void setCoreTranslationFromTime(TileEntityTransporter te)
    {
        float translate = (float) Math.sin((te.getWorldObj().getTotalWorldTime() + te.getStartingRotation()) * 0.02);
        GL11.glTranslatef(0.0F, translate * 0.02F, 0.0F);
    }
}
