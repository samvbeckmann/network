package com.qkninja.network.client.renderer;

import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.TileEntityTrackerHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * dilithium-transportation, class made on 6/22/2015.
 *
 * @author Sam Beckmann
 */
public class LineRenderer
{
    private Minecraft mc;

    public LineRenderer(Minecraft mc)
    {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onWorldRenderLast(RenderWorldLastEvent event)
    {
        List<TileEntityTransporter> transporters = TileEntityTrackerHelper.findTransporters();
        for (TileEntityTransporter t : transporters)
        {
            List<DistanceHandler> locations = t.getExportLocations();
            for (DistanceHandler l : locations)
            {
                renderLine(event, t.xCoord, t.yCoord, t.zCoord, l.getX(), l.getY(), l.getZ());
            }
        }
    }

    private void renderLine(RenderWorldLastEvent event, int x1, int y1, int z1, int x2, int y2, int z2)
    {
        EntityClientPlayerMP player = mc.thePlayer;

        double playerX = player.prevPosX + (player.posX - player.prevPosX) *event.partialTicks;
        double playerY = player.prevPosY + (player.posY - player.prevPosY) *event.partialTicks;
        double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) *event.partialTicks;

        GL11.glPushMatrix();
//        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);


        GL11.glTranslated(-playerX, -playerY, -playerZ);
        GL11.glColor4ub((byte) 0, (byte) 145, (byte) 229, (byte) 50);
//        float mx = -1250;
//        float my = 9;
//        float mz = 1700;
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glBegin(GL11.GL_LINES);

        GL11.glVertex3f(x1 + 0.5f, y1 + 0.5f, z1 + 0.5f);
        GL11.glVertex3f(x2 + 0.5f, y2 + 0.5f, z2 + 0.5f);
        GL11.glEnd();
//        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }
}
