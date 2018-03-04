package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 *
 * This is for driving the arm with a joystick. However, in theory the movement should be stopped by
 * the Talon's soft limits set in {@link SetArmHeightCommand}.
 */
public class DriveArmWithJoystickCommand extends Command {
    private static final double DEADZONE = 0.1;

    public DriveArmWithJoystickCommand() {
        super("Drive arm with joystick");
        requires(Robot.armSubsystem);
    }

    @Override
    protected void execute() {
        double value = Robot.oi.getArmY();
        if (Math.abs(value) < DEADZONE) {
            Robot.armSubsystem.driveArmPercentOut(0);
        } else {
            Robot.armSubsystem.driveArmPercentOut(value);
        }
        //testing telescoping arm
        // some quick code to test the wrist
        double pov = Robot.oi.getGMPOV();
        if (pov == 0) {
            Robot.armSubsystem.driveWristPercentOut(0.25);
        } else if (pov == 180) {
            Robot.armSubsystem.driveWristPercentOut(-0.25);
        } else {
            Robot.armSubsystem.driveWristPercentOut(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
