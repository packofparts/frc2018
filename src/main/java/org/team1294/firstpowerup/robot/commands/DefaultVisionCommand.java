package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

public class DefaultVisionCommand extends Command {

    public DefaultVisionCommand() {
        requires(Robot.visionSubsystem);
    }

    @Override
    protected void execute() {
        Robot.visionSubsystem.detectNothing();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
