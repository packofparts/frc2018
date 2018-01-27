package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ResetGyroCommand extends Command {
    public ResetGyroCommand() {
        super("Reset Gyro");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSubsystem.resetGyro();
    }

    @Override
    protected void execute() {
        // nothing, finishes immediately
    }

    @Override
    protected void end() {
        // nothing
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
