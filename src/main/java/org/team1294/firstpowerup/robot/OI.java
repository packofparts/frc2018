package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
//    private Joystick driveJoystickRight;
    private XboxController gameMech;

    public OI() {
        driveJoystickLeft = new Joystick(RobotMap.JOYSTICK_DRIVE_LEFT);
//        driveJoystickRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT);
        gameMech = new XboxController(RobotMap.JOYSTICK_GAMEMECH);
    }

    public double getDriveLeftX() {
        return driveJoystickLeft.getX();
    }

    public double getDriveLeftY() {
        return -driveJoystickLeft.getY();
    }

    public double getDriveRightY() {
        return 0.0;//-driveJoystickRight.getY();
    }
    
    public double getClimbY() {
        return gameMech.getY(GenericHID.Hand.kLeft);
    }

    public double getIntakeAxis() {
        return gameMech.getTriggerAxis(GenericHID.Hand.kRight)
                - gameMech.getTriggerAxis(GenericHID.Hand.kLeft);
    }
    public int getBumpers() {
        boolean left = gameMech.getBumper(GenericHID.Hand.kLeft);
        boolean right = gameMech.getBumper(GenericHID.Hand.kRight);
        if(right && !left){
            return 1;
        } else if(!right && left){
            return 2;
        }
        return 0;
    }
    public double getArmY() {
        return gameMech.getY(GenericHID.Hand.kRight);
    }

    public double getGMPOV() {
        return gameMech.getPOV();
    }
}
