package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class WaveArmSillyFunTimeCommand extends Command {
    private Command upCommand;
    private Command downCommand;
    boolean goingUp;

    public WaveArmSillyFunTimeCommand() {
        super("Win button command");
        downCommand = new SetArmHeightCommand(622);
        upCommand = new SetArmHeightCommand(500);
    }

    @Override
    protected void initialize() {
        goingUp = false;
        downCommand.start();
    }

    @Override
    protected void execute() {
        if (goingUp && !upCommand.isRunning()) {
            goingUp = false;
            downCommand.start();
        } else if (!goingUp && !downCommand.isRunning()) {
            goingUp = true;
            upCommand.start();
        }
    }

    @Override
    protected void end() {
        Robot.armSubsystem.driveArmPercentOut(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
