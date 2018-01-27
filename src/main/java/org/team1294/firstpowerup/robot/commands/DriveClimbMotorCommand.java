package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveClimbMotorCommand extends Command {
    public DriveClimbMotorCommand() {
        super("DriveClimbMotorCommand");
        requires(Robot.climbSubsystem);
    }

    @Override
    protected void initialize() {
        // nothing
    }

    @Override
    protected void execute() {
        Robot.climbSubsystem.driveTalon(Robot.oi.getClimbY());
    }

    @Override
    protected void end() {
        Robot.climbSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
