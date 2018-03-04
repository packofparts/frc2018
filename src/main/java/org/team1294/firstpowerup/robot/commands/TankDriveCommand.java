package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

/**
 *
 */
public class TankDriveCommand extends Command {
    public TankDriveCommand() {
        super("Tank Drive");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        // intentionally empty
    }

    @Override
    protected void execute() {
        double left = Robot.oi.getDriveLeftY();
        double right = Robot.oi.getDriveRightY();
        Robot.driveSubsystem.tankDrive(left, right);
    }

    @Override
    protected void end() {
        Robot.driveSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
