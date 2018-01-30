package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;

public class AutoDriveCommand extends CommandGroup {

    private final DriveCommand driveCommand;
    private final ForwardPIDCommand forwardPIDCommand;
    private final TurnPIDCommand turnPIDCommand;

    private final double desiredHeading;

    private double forwardRate;
    private double turnRate;

    public AutoDriveCommand(final double distance) {
        this(distance, Robot.driveSubsystem.getHeading());
    }

    public AutoDriveCommand(final double distance, final double heading) {
        this(distance, heading, 1);
    }

    public AutoDriveCommand(final double distance, final double heading, final double velocity) {
        this(distance, heading, velocity, 1);
    }

    public AutoDriveCommand(final double distance, final double heading, final double velocity, final double turnRate) {
        super("AutoDriveCommand(" + heading + ", " + distance + ", " + velocity + ", " + turnRate + ")");

        desiredHeading = heading;
        driveCommand = new DriveCommand();
        forwardPIDCommand = new ForwardPIDCommand(distance);
        turnPIDCommand = new TurnPIDCommand();

        addParallel(driveCommand);
        addParallel(forwardPIDCommand);
        addParallel(turnPIDCommand);

        setTimeout(15);
    }

    @Override
    protected void initialize() {
        // do nothing
    }

    @Override
    protected void end() {
        // do nothing
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || (forwardPIDCommand.onTarget() && turnPIDCommand.onTarget());
    }


    private class DriveCommand extends Command {

        public DriveCommand() {
            requires(Robot.driveSubsystem);
        }

        @Override
        protected void execute() {
            Robot.driveSubsystem.arcadeDrive(forwardRate, turnRate);
        }

        @Override
        protected boolean isFinished() {
            return false;
        }
    }

    private class ForwardPIDCommand extends PIDCommand {

        private final double distance;
        private boolean hasRunPIDOnce = false;

        public ForwardPIDCommand(final double distance) {
            super(1.0, 0.1, 0.0);

            this.distance = distance;

            double p = SmartDashboard.getNumber("AutoDriveCommand.ForwardPID.p", 1.0);
            double i = SmartDashboard.getNumber("AutoDriveCommand.ForwardPID.i", 0.1);
            double d = SmartDashboard.getNumber("AutoDriveCommand.ForwardPID.d", 0.0);
            double tolerance = SmartDashboard
                .getNumber("AutoDriveCommand.ForwardPID.tolerance", 0.01);
            double maxOutput = SmartDashboard
                .getNumber("AutoDriveCommand.ForwardPID.maxOutput", 0.5);

            getPIDController().setP(p);
            getPIDController().setI(i);
            getPIDController().setD(d);
            getPIDController().setAbsoluteTolerance(tolerance);
            getPIDController().setOutputRange(-maxOutput, maxOutput);
        }

        @Override
        protected void initialize() {
            getPIDController()
                .setSetpoint(Robot.driveSubsystem.getEncoderPositionAverage() + distance);
        }

        @Override
        protected double returnPIDInput() {
            hasRunPIDOnce = true;
            return Robot.driveSubsystem.getEncoderPositionAverage();
        }

        @Override
        protected void usePIDOutput(double output) {
            forwardRate = output;
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        public boolean onTarget() {
            return hasRunPIDOnce && getPIDController().onTarget();
        }
    }

    public class TurnPIDCommand extends PIDCommand {

        private boolean hasRunPIDOnce = false;

        public TurnPIDCommand() {
            super(1.0, 0.0, 0.0);

            double p = SmartDashboard.getNumber("AutoDriveCommand.TurnPID.p", 1.0);
            double i = SmartDashboard.getNumber("AutoDriveCommand.TurnPID.i", 0.0);
            double d = SmartDashboard.getNumber("AutoDriveCommand.TurnPID.d", 0.0);
            double tolerance = SmartDashboard
                .getNumber("AutoDriveCommand.TurnPID.tolerance", 5.0);
            double maxOutput = SmartDashboard
                .getNumber("AutoDriveCommand.TurnPID.maxOutput", 0.08);

            getPIDController().setP(p);
            getPIDController().setI(i);
            getPIDController().setD(d);
            getPIDController().setAbsoluteTolerance(tolerance);
            getPIDController().setOutputRange(-maxOutput, maxOutput);

            getPIDController().setAbsoluteTolerance(tolerance);
            getPIDController().setInputRange(0, 360);
            getPIDController().setContinuous(true);
            getPIDController().setOutputRange(-maxOutput, maxOutput);

            getPIDController().setSetpoint(desiredHeading);
        }

        @Override
        protected double returnPIDInput() {
            hasRunPIDOnce = true;
            return Robot.driveSubsystem.getHeading();
        }

        @Override
        protected void usePIDOutput(double output) {
            turnRate = output;
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        public boolean onTarget() {
            return hasRunPIDOnce && getPIDController().onTarget();
        }
    }
}
