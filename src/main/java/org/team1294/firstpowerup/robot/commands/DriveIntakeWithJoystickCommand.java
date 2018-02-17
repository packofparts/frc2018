package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveIntakeWithJoystickCommand extends Command {
    public DriveIntakeWithJoystickCommand() {
        super("Drive intake with joystick");
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void execute() {
        Robot.intakeSubsystem.driveIntake(Robot.oi.getIntakeAxis());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
