package com.qkninja.network.reference;

/**
 * Contains strings to link to language file for
 * messages displayed to the user.
 *
 * @author QKninja
 */
public final class Messages
{
    public static final class Tooltips
    {
        public static final String MODE = "network.tooltips.mode";
        public static final String HAS_DATA = "network.tooltips.hasdata";
        public static final String ONE_WAY = "network.tooltips.oneway";
        public static final String TWO_WAY = "network.tooltips.twoway";
    }

    public static final class Configuration
    {
        public static final String DIFFICULTY = "difficulty";
        public static final String DIFFICULTY_DESCRIPTION = "The difficulty of the mod. 0 = easy, 1 = normal, 2 = hard";

        public static final String TRANSPORT_DELAY = "transportDelay";
        public static final String TRANSPORT_DELAY_DESCRIPTION = "A Nexus will perform an operation every this many ticks";

        public static final String MAX_DISTANCE_SQUARED = "maxDistanceSquared";
        public static final String MAX_DISTANCE_DESCRIPTION = "The maximum distance, squared, connections between nexuses can be made";
    }
}
