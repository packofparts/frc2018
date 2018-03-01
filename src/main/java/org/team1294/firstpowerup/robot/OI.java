package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team1294.firstpowerup.robot.commands.IntakeInCommand;
import org.team1294.firstpowerup.robot.commands.IntakeOutCommand;

/**
 * The class representing the OI, or operator's interface. This class contains
 * references to all of the {@link edu.wpi.first.wpilibj.Joystick Joysticks}
 * used at the DS. You can also use the joystick instances to access the
 * individual {@link JoystickButton JoystickButtons}, and you can link
 * {@link edu.wpi.first.wpilibj.command.Command Commands} to run when they
 * are pressed.
 */
public class OI {
    private XboxController driveJoystickLeft;
//    private Joystick driveJoystickRight;
    private XboxController gameMech;

    public OI() {
        driveJoystickLeft = new XboxController(RobotMap.JOYSTICK_DRIVE_LEFT);
//        driveJoystickRight = new Joystick(RobotMap.JOYSTICK_DRIVE_RIGHT);
        gameMech = new XboxController(RobotMap.JOYSTICK_GAMEMECH);

        JoystickButton inButton = new JoystickButton(gameMech, 1);
        JoystickButton outButton = new JoystickButton(gameMech, 2);
        inButton.whileActive(new IntakeInCommand());
        outButton.whileActive(new IntakeOutCommand());
    }

    public double getDriveLeftX() {
        return driveJoystickLeft.getX(GenericHID.Hand.kLeft);
    }

    public double getDriveLeftY() {
        return -driveJoystickLeft.getY(GenericHID.Hand.kLeft);
    }

    public double getDriveRightY() {
        return -driveJoystickLeft.getY(GenericHID.Hand.kRight);
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
