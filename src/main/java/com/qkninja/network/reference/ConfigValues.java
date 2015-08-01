package com.qkninja.network.reference;

/**
 * Values loaded from the configuration file are stored here
 *
 * @author QKninja
 */
public class ConfigValues
{
    public static int transportDelay = 20;
    public static int maxDistanceSq = 256;
    public static int difficulty = 1;
    public static boolean debugMode = false; /* No config file value, only set in code */
    public static int sparkSpeedFactor = 10;
    public static int maxUpgradeNumber = 3;
    public static boolean highCapacity = false;
    public static int standardNumberPickup = 1;
    public static int highCapacityPickup = 64;
}
