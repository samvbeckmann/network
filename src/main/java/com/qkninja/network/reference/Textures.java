package com.qkninja.network.reference;

import com.qkninja.network.utility.ResourceLocationHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Contains names of textures used in the Network mod.
 *
 * @author QKninja
 */
public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Particles
    {
        private static final String PARTICLE_SHEET_LOCATION = "textures/particles/";

        public static final ResourceLocation SPARK = ResourceLocationHelper.getResourceLocation(PARTICLE_SHEET_LOCATION + "spark.png");
    }
}
