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
    public DriveArmWithJoystickCommand() {
        super("Drive arm with joystick");
        requires(Robot.armSubsystem);
    }

    @Override
    protected void execute() {
        Robot.armSubsystem.driveArmPercentOut(Robot.oi.getArmY());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
