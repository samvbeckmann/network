package com.qkninja.network.reference;

/**
 * Reference class for all the String names in the mod.
 *
 * @author QKninja
 */
public final class Names
{
    public static final class Items
    {
        public static final String CORE = "core";
        public static final String HYPERSPANNER = "hyperspanner";
    }

    public static final class Blocks
    {
        public static final String TRANSPORTER = "transporter";
    }

    public static final class NBT
    {
        public static final String CUSTOM_NAME = "CustomName";
        public static final String X_COORD = "xCoord";
        public static final String Y_COORD = "yCoord";
        public static final String Z_COORD = "zCoord";
        public static final String COUNTER = "counter";
        public static final String DISTANCE = "distance";
        public static final String LOCATIONS = "locations";
    }

    public static final class TileEntities
    {
        public static final String TILE_TRANSPORTER = "tileTransporter";
    }

    public class Channels
    {
        public static final String DESCRIPTION_CHANNEL = "Description";
    }

    public class Models
    {
        private static final String MODEL_PREFIX = "textures/models/";

        public static final String TRANSPORTER = MODEL_PREFIX + "transporter";

        public static final String MODEL_SUFFIX = ".png";
    }
}
