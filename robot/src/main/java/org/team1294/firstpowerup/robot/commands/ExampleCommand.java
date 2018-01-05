package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * An example {@link Command}. Commands manipulate subsystems based on input,
 * either prerecorded values or {@link org.team1294.firstpowerup.robot.OI} input.
 */
public class ExampleCommand extends Command {
    public ExampleCommand() {
        super("Example Command");
        requires(Robot.exampleSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.exampleSubsystem.exampleRunForwardFullSpeed();
    }

    @Override
    protected void execute() {
        // TODO: Method stub
    }

    @Override
    protected boolean isFinished() {
        return false;   // loop infinitely
    }

    @Override
    protected void end() {
        Robot.exampleSubsystem.exampleStop();
    }
}
