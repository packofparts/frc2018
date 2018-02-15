package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ToggleArmWristDeployCommand extends InstantCommand {
    public ToggleArmWristDeployCommand() {
        super("Toggle wrist");
        requires(Robot.armSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.armSubsystem.toggleWristDeploy();
    }
}
