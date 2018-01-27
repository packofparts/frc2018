package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

public class DriveStraightTurnCommand extends PIDCommand {
    private static final double p = 0.1;
    private static final double i = 0.0;
    private static final double d = 0.0;
    private static final double TOLERANCE = 0.1;
    private static final double MAX_RATE = 0.5;

    private boolean hasRunPIDOnce = false;
    private DriveStraightCommand group;

    public DriveStraightTurnCommand() {
        super("Drive Straight Turn Command", p, i, d);
        getPIDController().setAbsoluteTolerance(TOLERANCE);
        getPIDController().setInputRange(0, 360);
        getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    }

    @Override
    protected void initialize() {
        getPIDController().setSetpoint(Robot.driveSubsystem.getHeading());
        CommandGroup group = getGroup();
        if (group instanceof DriveStraightCommand) {
            this.group = (DriveStraightCommand) group;
        } else {
            this.group = null;
        }
    }

    @Override
    protected double returnPIDInput() {
        hasRunPIDOnce = true;
        return Robot.driveSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (group != null) {
            group.setTurnRate(output);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public boolean onTarget() {
        return hasRunPIDOnce && getPIDController().onTarget();
    }
}
