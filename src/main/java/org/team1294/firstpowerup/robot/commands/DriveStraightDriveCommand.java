package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

public class DriveStraightDriveCommand extends PIDCommand{
    private static final double p = 1.0;
    private static final double i = 0.0;
    private static final double d = 0.0;

    private DriveStraightCommand group;

    public DriveStraightDriveCommand(){
        super("Drive Straight Drive Command",p, i, d);
    }

    @Override
    protected void initialize() {
        CommandGroup group = getGroup();
        if (group instanceof DriveStraightCommand) {
            this.group = (DriveStraightCommand) group;
        } else {
            this.group = null;
        }
    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {
        if (group != null) {
            Robot.driveSubsystem.arcadeDrive(group.getForwardRate(),
                    group.getTurnRate());
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
