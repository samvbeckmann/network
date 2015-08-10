package com.qkninja.network.reference;

/**
 * Contains strings to link to language file for
 * messages displayed to the user.
 *
 * @author QKninja
 */
public final class Messages
{
    private static final String BASE_PREFIX = "network.";

    public static final class Tooltips
    {
        private static final String PREFIX = BASE_PREFIX + "tooltips.";

        public static final String MODE = PREFIX + "mode";
        public static final String HAS_DATA = PREFIX + "hasdata";
        public static final String ONE_WAY = PREFIX + "oneway";
        public static final String TWO_WAY = PREFIX + "twoway";
    }

    public static final class Configuration // TODO: De-Localize
    {
        public static final String DIFFICULTY = "difficulty";
        public static final String DIFFICULTY_DESCRIPTION = "The difficulty of the mod. 0 = easy, 1 = normal, 2 = hard";

        public static final String TRANSPORT_DELAY = "transportDelay";
        public static final String TRANSPORT_DELAY_DESCRIPTION = "A Nexus will perform an operation every this many ticks";

        public static final String MAX_DISTANCE_SQUARED = "maxDistanceSquared";
        public static final String MAX_DISTANCE_DESCRIPTION = "The maximum distance, squared, connections between nexuses can be made";

        public static final String SPARK_SPEED_FACTOR = "sparkSpeedFactor";
        public static final String SPARK_SPEED_DESCRIPTION = "The distance sparks travel in 1 tick. Higher values = slower sparks.";
    }

    public static final class Waila
    {
        private static final String PREFIX = BASE_PREFIX + "waila.";

        public static final String WAILA_MODE = PREFIX + "mode";
        public static final String WAILA_INVENTORY = PREFIX + "inventory";
        public static final String WAILA_FLUID = PREFIX + "fluid";
    }

    public static final class Strings
    {
        public static final String MODE_NEUTRAL = BASE_PREFIX + "mode_neutral";
        public static final String MODE_IMPORT = BASE_PREFIX + "mode_import";
        public static final String MODE_EXPORT = BASE_PREFIX + "mode_export";
    }
}
