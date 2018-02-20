package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

public class DoVisionDetectCube extends Command {

    public DoVisionDetectCube() {
        requires(Robot.visionSubsystem);
    }

    @Override
    protected void execute() {
        Robot.visionSubsystem.detectCrate();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
