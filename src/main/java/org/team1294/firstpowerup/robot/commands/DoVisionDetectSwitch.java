package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

public class DoVisionDetectSwitch extends Command {

    public DoVisionDetectSwitch() {
        requires(Robot.visionSubsystem);
    }

    @Override
    protected void execute() {
        Robot.visionSubsystem.detectSwitch();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
