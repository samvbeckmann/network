package com.qkninja.network.client.particle;

import com.qkninja.network.reference.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Defines a spark particle, that travels along the connection paths.
 *
 * @author Sam Beckmann
 */
public class EntityFXSpark extends EntityFX
{
    private static final ResourceLocation texture = Textures.Particles.SPARK;

    private float opacity;
    private boolean doFade;

    public EntityFXSpark(World world, double xPos, double yPos, double zPos, double motionX, double motionY, double motionZ, int lifespan, float opacity, boolean doFade)
    {
        super(world, xPos, yPos, zPos);
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleMaxAge = lifespan;
        this.noClip = true;
        this.opacity = opacity;
        this.doFade = doFade;
    }

    @Override
    public void renderParticle(Tessellator tess, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        float PScale = 0.01F * this.particleScale;
        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        tess.startDrawingQuads();
        tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, opacity);
        tess.addVertexWithUV((double) (x - par3 * PScale - par6 * PScale), (double) (y - par4 * PScale), (double) (z - par5 * PScale - par7 * PScale), 0, 0);
        tess.addVertexWithUV((double) (x - par3 * PScale + par6 * PScale), (double) (y + par4 * PScale), (double) (z - par5 * PScale + par7 * PScale), 1, 0);
        tess.addVertexWithUV((double) (x + par3 * PScale + par6 * PScale), (double) (y + par4 * PScale), (double) (z + par5 * PScale + par7 * PScale), 1, 1);
        tess.addVertexWithUV((double) (x + par3 * PScale - par6 * PScale), (double) (y - par4 * PScale), (double) (z + par5 * PScale - par7 * PScale), 0, 1);
        tess.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public int getFXLayer()
    {
        return 3;
    }

    public void onUpdate()
    {
        if (particleAge > particleMaxAge) this.setDead();
        if (Minecraft.getMinecraft().gameSettings.particleSetting == 2) this.setDead();

        if (worldObj.isRemote) this.motionHandler();
        if (doFade) opacity -= .01;

        this.particleAge++;
    }

    public void motionHandler()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
