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
        public static final String ITEM_CORE = "itemCore";
        public static final String HYPERSPANNER = "hyperspanner";
        public static final String RED_LED = "redLED";
        public static final String BLUE_LED = "blueLED";
        public static final String GREEN_LED = "greenLED";
        public static final String FLUID_CORE = "fluidCore";
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
        public static final String MODE = "mode";
        public static final String CORE = "core";
        public static final String ITEMS = "items";
        public static final String DELAY = "delay";
        public static final String MAX_DISTANCE = "maxDistance";
        public static final String UPGRADES = "upgrades";
        public static final String HIGH_CAPACITY = "highCapacity";
    }

    public static final class TileEntities
    {
        public static final String TILE_TRANSPORTER = "tileTransporter";
    }

    public static final class Channels
    {
        public static final String DESCRIPTION_CHANNEL = "Description";
    }

    public static final class Models
    {
        private static final String MODEL_PREFIX = "textures/models/";
        public static final String MODEL_SUFFIX = ".png";

        public static final String TRANSPORTER = MODEL_PREFIX + "transporter";
        public static final String CRYSTAL = MODEL_PREFIX + "crystal" + MODEL_SUFFIX;
        public static final String RED_LED = MODEL_PREFIX + "redLED" + MODEL_SUFFIX;
        public static final String BLUE_LED = MODEL_PREFIX + "blueLED" + MODEL_SUFFIX;
        public static final String GREEN_LED = MODEL_PREFIX + "greenLED" + MODEL_SUFFIX;
        public static final String FLUID_CRYSTAL = MODEL_PREFIX + "fluidCrystal" + MODEL_SUFFIX;
    }
}
