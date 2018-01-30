package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

import java.util.function.Consumer;

public class AutoTurnPIDCommand extends PIDCommand {
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
        super(1.0, 0.0, 0.0);

        this.outputConsumer = outputConsumer;

        getPIDController().setP(SmartDashboard.getNumber("AutoTurnPIDCommand.p", 1.0));
        getPIDController().setI(SmartDashboard.getNumber("AutoTurnPIDCommand.i", 0.0));
        getPIDController().setD(SmartDashboard.getNumber("AutoTurnPIDCommand.d", 0.0));
        getPIDController().setAbsoluteTolerance(SmartDashboard.getNumber("AutoTurnPIDCommand.tolerance", 5.0));
        getPIDController().setInputRange(0, 360);
        getPIDController().setContinuous(true);
        getPIDController().setOutputRange(-maxRate, maxRate);
        getPIDController().setSetpoint(heading);
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
        return hasRunPIDOnce && getPIDController().onTarget();
    }

    public void setSetpoint(double heading) {
        getPIDController().setSetpoint(heading);
    }
}
