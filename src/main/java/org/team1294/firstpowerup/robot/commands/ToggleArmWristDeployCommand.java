package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ToggleArmWristDeployCommand extends Command {
    private static final double TOLLERANCE = 10;

    public ToggleArmWristDeployCommand() {
        super("Toggle wrist");
        requires(Robot.armSubsystem);
        setTimeout(3);
    }

    @Override
    protected void initialize() {
        Robot.armSubsystem.toggleWristDeploy();
    }

    @Override
    protected boolean isFinished() {
        return true;
//        return Math.abs(Robot.armSubsystem.getWristError()) < TOLLERANCE || isTimedOut();
    }
}
