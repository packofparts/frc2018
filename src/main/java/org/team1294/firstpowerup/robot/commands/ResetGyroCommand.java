package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ResetGyroCommand extends InstantCommand {
    public ResetGyroCommand() {
        super("Reset Gyro");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSubsystem.resetGyro();
    }
}
