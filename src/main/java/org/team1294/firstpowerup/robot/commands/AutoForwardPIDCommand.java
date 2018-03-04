package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

import java.util.function.Consumer;

public class AutoForwardPIDCommand extends PIDCommand {
    private final Consumer<Double> outputConsumer;
    private boolean hasRunPIDOnce = false;
    private double distance;

    public AutoForwardPIDCommand(final Consumer<Double> outputConsumer, final double distance, final double maxVelocity) {
        super(10, 0.0, 0.0);

        this.outputConsumer = outputConsumer;
        this.distance = distance;

        getPIDController().setContinuous(false);
        getPIDController().setOutputRange(-maxVelocity, maxVelocity);
        getPIDController().setAbsoluteTolerance(0.05);
    }

    @Override
    protected void initialize() {
        double initialPosition = Robot.driveSubsystem.getEncoderPositionAverage();
        getPIDController().setSetpoint(initialPosition + distance);

        SmartDashboard.putData("AutoForwardPID", getPIDController());

        hasRunPIDOnce = false;
    }

    @Override
    protected double returnPIDInput() {
        hasRunPIDOnce = true;
        return Robot.driveSubsystem.getEncoderPositionAverage();
    }

    @Override
    protected void usePIDOutput(double output) {
        System.out.printf("setpoint: %.2f, error: %.2f, sensor: %.2f%n", getPIDController().getSetpoint(), getPIDController().getError(), Robot.driveSubsystem.getHeading());
        outputConsumer.accept(output);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public boolean onTarget() {
        return hasRunPIDOnce && getPIDController().onTarget();
    }

    public void setSetpoint(final double distance) {
        this.distance = distance;
    }
}
