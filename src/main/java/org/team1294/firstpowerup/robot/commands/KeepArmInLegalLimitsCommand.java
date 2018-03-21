package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * @author Austin Jenchi (timtim17)
 */
public class KeepArmInLegalLimitsCommand extends CommandGroup {

    private final ExtensionPIDCommand extendPID;
    private final WristPIDCommand wristPID;

    public KeepArmInLegalLimitsCommand() {
        super("Keep arm in legal limits");
        extendPID = new ExtensionPIDCommand();
        addParallel(extendPID);
        wristPID = new WristPIDCommand();
        addSequential(wristPID);
    }

    @Override
    protected void execute() {
        double angle = Robot.armSubsystem.getArmHeight();
        if (angle <= -69) {
extendPID.setSetpoint(0);
wristPID.setSetpoint(69);
        } else if (angle <= -61) {
extendPID.setSetpoint(14);
wristPID.setSetpoint(61);
        } else if (angle <= -39) {
extendPID.setSetpoint(0);
wristPID.setSetpoint(39);
        } else if (angle <= 0) {
extendPID.setSetpoint(0);
wristPID.setSetpoint(75);
        } else if (angle <= 20) {
extendPID.setSetpoint(0);
wristPID.setSetpoint(31);
        } else if (angle <= 32) {
extendPID.setSetpoint(0);
wristPID.setSetpoint(0);
        } else if (angle <= 44) {
extendPID.setSetpoint(3);
wristPID.setSetpoint(-44);
        } else if (angle <= 50) {
extendPID.setSetpoint(8.5);
wristPID.setSetpoint(-50);
        } else {
            extendPID.setSetpoint(18);
            wristPID.setSetpoint(-58);
        }
    }

    private static class ExtensionPIDCommand extends PIDCommand {

        private static final double kP = 0.1;
        private static final double kI = 0.0;
        private static final double kD = 0.0;

        public ExtensionPIDCommand() {
            super("arm extension pid", kP, kI, kD);
            getPIDController().setOutputRange(-1.0, 1.0);
        }

        @Override
        protected double returnPIDInput() {
            return Robot.armSubsystem.getExtensionSensorValue();
        }

        @Override
        protected void usePIDOutput(double output) {
            Robot.armSubsystem.driveExtendPercentOut(output);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        public void setSetpoint(double setpoint) {
            getPIDController().setSetpoint(setpoint);
        }
    }

    private static class WristPIDCommand extends PIDCommand {

        private static final double kP = 0.1;
        private static final double kI = 0.0;
        private static final double kD = 0.0;

        public WristPIDCommand() {
            super("wrist pid", kP, kI, kD);
            getPIDController().setOutputRange(-1.0, 1.0);
        }

        @Override
        protected double returnPIDInput() {
            return Robot.armSubsystem.getWristAngle();
        }

        @Override
        protected void usePIDOutput(double output) {
            Robot.armSubsystem.driveWristPercentOut(output);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        public void setSetpoint(double setpoint) {
            getPIDController().setSetpoint(setpoint);
        }
    }
}
