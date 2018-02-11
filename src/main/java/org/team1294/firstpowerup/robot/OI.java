package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team1294.firstpowerup.robot.commands.TestTalonPid;

/**
 * The class representing the OI, or operator's interface. This class contains
 * references to all of the {@link edu.wpi.first.wpilibj.Joystick Joysticks}
 * used at the DS. You can also use the joystick instances to access the
 * individual {@link JoystickButton JoystickButtons}, and you can link
 * {@link edu.wpi.first.wpilibj.command.Command Commands} to run when they
 * are pressed.
 */
public class OI {
    private Joystick driveJoystickLeft;
    private Joystick driveJoystickRight;

    public OI() {
        driveJoystickLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT);
        driveJoystickRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT);
    }

    public double getDriveLeftX() {
        return driveJoystickLeft.getX();
    }

    public double getDriveLeftY() {
        return -driveJoystickLeft.getY();
    }

    public double getDriveRightY() {
        return -driveJoystickRight.getY();
    }
    
//    public double getClimbY() {
//        return driveJoystick.getY(GenericHID.Hand.kRight);
//    }
}
