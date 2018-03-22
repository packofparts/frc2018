package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveClimbMotorCommand extends Command {
    private static final double DEADZONE = 0.25;

    public DriveClimbMotorCommand() {
        super("DriveClimbMotorCommand");
        requires(Robot.climbSubsystem);
    }

    @Override
    protected void execute() {
        double value = Robot.oi.getClimbY();
        if (Math.abs(value) < DEADZONE) {
            Robot.climbSubsystem.stop();
        } else {
            Robot.climbSubsystem.driveTalon(value);
        }
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
