package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveStraightDriveCommand extends PIDCommand{
    private static final double p = 0.1;
    private static final double i = 0.0;
    private static final double d = 0.0;

    public DriveStraightDriveCommand(){
        super("Drive Straight Drive Command",p, i, d);
    }


    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
