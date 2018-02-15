package org.team1294.firstpowerup.robot;

/**
 * A map of constants for ports and other magic numbers in the code. Other classes refer to these
 * constants (i.e. {@code RobotMap.SOME_CONSTANT}.
 */
public class RobotMap {
    public static final int JOYSTICK_DRIVE_LEFT = 0;
    public static final int JOYSTICK_DRIVE_RIGHT = 1;
    public static final int JOYSTICK_GAMEMECH = 2;

    public static final int TALON_RIGHT_FRONT = 1;
    public static final int TALON_RIGHT_REAR = 2;
    public static final int TALON_LEFT_FRONT = 3;
    public static final int TALON_LEFT_REAR = 4;
    public static final int TALON_CLIMB = 5;
    public static final int TALON_ARM = 6;
    public static final int TALON_INTAKE_LEFT = 7;
    public static final int TALON_INTAKE_RIGHT = 8;

    public static final int SENSOR_POT = 0;
    public static final int SENSOR_INTAKE_BEAMBREAK = 0;

    public static final int CTRE_TIMEOUT_INIT = 10;
    public static final int CTRE_TIMEOUT_PERIODIC = 0;
}
