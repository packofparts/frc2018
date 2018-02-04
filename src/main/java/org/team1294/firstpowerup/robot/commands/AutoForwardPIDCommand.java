package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

import java.util.function.Consumer;

public class AutoForwardPIDCommand extends PIDCommand {
    private final Consumer<Double> outputConsumer;
    private boolean hasRunPIDOnce = false;
    private double initialPosition = 0;
    private final double distance;

    public AutoForwardPIDCommand(final Consumer<Double> outputConsumer, final double distance, final double maxVelocity) {
        super(1.0, 0.1, 0.0);

        this.outputConsumer = outputConsumer;
        this.distance = distance;

//        getPIDController().setInputRange(-1, 1);
        getPIDController().setContinuous(false);
        getPIDController().setOutputRange(-maxVelocity, maxVelocity);
        getPIDController().setSetpoint(distance);

        SmartDashboard.putNumber("AutoForwardPIDCommand.p", 0.66);
        SmartDashboard.putNumber("AutoForwardPIDCommand.i", 2.095);
        SmartDashboard.putNumber("AutoForwardPIDCommand.d", 1.397);
        SmartDashboard.putNumber("AutoForwardPIDCommand.tolerance", 0.01);

    }

    @Override
    protected void initialize() {
        initialPosition = Robot.driveSubsystem.getEncoderPositionAverage();
        getPIDController().setP(SmartDashboard.getNumber("AutoForwardPIDCommand.p", 0.66));
        getPIDController().setI(SmartDashboard.getNumber("AutoForwardPIDCommand.i", 2.095));
        getPIDController().setD(SmartDashboard.getNumber("AutoForwardPIDCommand.d", 1.397));
        getPIDController().setAbsoluteTolerance(SmartDashboard.getNumber("AutoForwardPIDCommand.tolerance", 0.01));
        getPIDController().setSetpoint(initialPosition + distance);
        System.out.println("I was here too");
    }

    @Override
    protected double returnPIDInput() {
        hasRunPIDOnce = true;
        return Robot.driveSubsystem.getEncoderPositionAverage();
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
        return hasRunPIDOnce && getPIDController().onTarget();
    }

    public void setSetpoint(final double distance) {
        getPIDController().setSetpoint(initialPosition + distance);
    }
}
