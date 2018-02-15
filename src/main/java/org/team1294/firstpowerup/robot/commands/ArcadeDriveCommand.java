package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

/**
 *
 */
public class ArcadeDriveCommand extends Command {
    public ArcadeDriveCommand() {
        super("Arcade Drive");
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSubsystem.setSafetyEnabled(true);
    }

    @Override
    protected void execute() {
        double forward = Robot.oi.getDriveLeftY();
        double turn = Robot.oi.getDriveLeftX();
        Robot.driveSubsystem.arcadeDrive(forward, turn);
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
