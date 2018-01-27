package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightForwardCommand extends PIDCommand {
    private static final double p = 0.1;
    private static final double i = 0.0;
    private static final double d = 0.0;
    private static final double TOLERANCE = 0.1;
    private static final double MAX_SPEED = 0.5;

    private final double distance;
    private boolean hasRunPIDOnce = false;


    public DriveStraightForwardCommand(final double distance){
        super("Drive Straight Forward Command",p, i, d);

        this.distance = distance;
        getPIDController().setAbsoluteTolerance(TOLERANCE);
        getPIDController().setOutputRange(-MAX_SPEED, MAX_SPEED);
        getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage());
    }

    @Override
    protected void initialize() {
        getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage() + distance);
    }

    @Override
    protected double returnPIDInput() {
        hasRunPIDOnce = true;
        return Robot.driveSubsystem.getEncoderPositionAverage();
    }

    @Override
    protected void usePIDOutput(double output) {
        CommandGroup group = getGroup();
        if(group instanceof DriveStraightCommand){
            ((DriveStraightCommand) group).setForwardRate(output);
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
