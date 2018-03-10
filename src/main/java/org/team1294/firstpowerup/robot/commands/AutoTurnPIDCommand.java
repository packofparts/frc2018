package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

import java.util.function.Consumer;

public class AutoTurnPIDCommand extends PIDCommand {
    public static final double ABSOLUTE_TOLERANCE = 20;
    private final Consumer<Double> outputConsumer;
    private boolean hasRunPIDOnce = false;

    /**
     * Runs a heading PID with the current heading and the given maxRate.
     * @param outputConsumer the method to call with the output of the PID
     * @param maxRate the max allowed turn rate
     */
    public AutoTurnPIDCommand(final Consumer<Double> outputConsumer, final double maxRate) {
        this(outputConsumer, Robot.driveSubsystem.getHeading(), maxRate);
    }

    /**
     * Runs a heading PID to the given heading and maxRate
     * @param outputConsumer the method to call with the output of the PID
     * @param heading the setPoint of the PID
     * @param maxRate the max allowed turn rate
     */
    public AutoTurnPIDCommand(final Consumer<Double> outputConsumer, final double heading, final double maxRate) {
        super(0.025, 0.0, 0.0);

        this.outputConsumer = outputConsumer;

        getPIDController().setInputRange(0, 360);
        getPIDController().setContinuous(true);
        getPIDController().setOutputRange(-maxRate, maxRate);
        getPIDController().setSetpoint(heading);
        getPIDController().setAbsoluteTolerance(ABSOLUTE_TOLERANCE);
    }

    @Override
    protected void initialize() {
        SmartDashboard.putData("autoturnPID", getPIDController());
        hasRunPIDOnce = false;
    }

    @Override
    protected double returnPIDInput() {
        hasRunPIDOnce = true;
        return Robot.driveSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        outputConsumer.accept(output);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public boolean onTarget() {
        return hasRunPIDOnce && getPIDController().onTarget() && Math.abs(Robot.driveSubsystem.getTurnRate()) < 0.01;
    }

    public void setSetpoint(double heading) {
        getPIDController().setSetpoint(heading);
    }
}
