package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveIntakeWithJoystickCommand extends Command {
    private static final double TOLLERANCE = 0.1;
    private static final double BACKDRIVE = 0.03;

    public DriveIntakeWithJoystickCommand() {
        super("Drive intake with joystick");
        requires(Robot.intakeSubsystem);
    }

    @Override
    protected void execute() {
        double intakeAxis = Robot.oi.getIntakeAxis();
        if (Math.abs(intakeAxis) > TOLLERANCE) {
            Robot.intakeSubsystem.driveIntake(intakeAxis);
        } else {
            Robot.intakeSubsystem.driveIntake(BACKDRIVE);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
