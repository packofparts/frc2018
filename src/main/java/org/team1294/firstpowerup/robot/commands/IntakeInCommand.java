package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class IntakeInCommand extends Command {
    private static final double SPEED = 1.0;
    boolean hasTimeout = false;

    public IntakeInCommand() {
        super("Drive intake in");
        requires(Robot.intakeSubsystem);
    }

    public IntakeInCommand(int timeout) {
        super();
        setName(getName() + " (" + timeout + " sec timeout)");
        setTimeout(timeout);
        hasTimeout = true;
    }

    @Override
    protected void execute() {
        Robot.intakeSubsystem.driveIntake(SPEED);
    }

    @Override
    protected boolean isFinished() {
        return hasTimeout && isTimedOut();
    }
}
