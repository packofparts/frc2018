package org.team1294.firstpowerup.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
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
    private XboxController driveJoystick;

//    private Button exampleButton;

    public OI() {
        driveJoystick = new XboxController(RobotMap.JOYSTICK_DRIVE);

//        JoystickButton aButton = new JoystickButton(driveJoystick, 1);
//        aButton.whileHeld(new AutoDriveCommand(0, 0, 0, .75));
//
//        JoystickButton bButton = new JoystickButton(driveJoystick, 2);
//        bButton.whileHeld(new AutoDriveCommand(-1, 0, 1, 0));

        JoystickButton aButton = new JoystickButton(driveJoystick, 1);
        aButton.whileHeld(new TestTalonPid());
    }

    public double getDriveLeftX() {
        return driveJoystick.getX(GenericHID.Hand.kLeft);
    }

    public double getDriveLeftY() {
        return driveJoystick.getY(GenericHID.Hand.kLeft);
    }

    public double getClimbY() {
        return driveJoystick.getY(GenericHID.Hand.kRight);
    }
}
