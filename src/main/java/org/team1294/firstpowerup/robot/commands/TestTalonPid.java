package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;

public class TestTalonPid extends Command {

    public TestTalonPid() {
        requires(Robot.driveSubsystem);
    }

    @Override
    protected void initialize() {
        Robot.driveSubsystem.setSafetyEnabled(false);
    }

    @Override
    protected void execute() {
        Robot.driveSubsystem.autoDrive(500, 500);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
