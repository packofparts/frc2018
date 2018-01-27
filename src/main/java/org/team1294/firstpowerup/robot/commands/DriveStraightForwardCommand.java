package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightForwardCommand extends PIDCommand {
    private static /* final */ double p = 0.5;
    private static /* final */ double i = 0.0;
    private static final double d = 0.0;
    private static final double TOLERANCE = 0.1;
    private static final double MAX_SPEED = 0.5;

    private final double distance;
    private boolean hasRunPIDOnce = false;
    private DriveStraightCommand group;


    public DriveStraightForwardCommand(final double distance){
        super("Drive Straight Forward Command",p, i, d);

        this.distance = distance;
        getPIDController().setAbsoluteTolerance(TOLERANCE);
        getPIDController().setOutputRange(-MAX_SPEED, MAX_SPEED);
        getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage());

        SmartDashboard.putNumber("DSFC/p", 1.0);
        SmartDashboard.putNumber("DSFC/i", 0.1);
    }

    @Override
    protected void initialize() {
        getPIDController().setP(SmartDashboard.getNumber("DSFC/p", 1.0));
        getPIDController().setI(SmartDashboard.getNumber("DSFC/i", 0.1));

        getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage() + distance);
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
        return Robot.driveSubsystem.getEncoderPositionAverage();
    }

    @Override
    protected void usePIDOutput(double output) {
        if(group != null){
            group.setForwardRate(output);
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
