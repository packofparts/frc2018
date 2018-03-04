package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * The goal of this command is to provide the ability to drive in teleop with ArcadeDrive, like
 * {@link ArcadeDriveCommand}, but with heading compensation - that is, if the driver is
 * intentionally trying to drive straight, do the best we can using gyro input to keep the robot on
 * course.
 *
 * @author Austin Jenchi (timtim17)
 */
public class GyroAssistTankDriveCommand extends PIDCommand {
    private static final double TURN_DEADBAND = 0.25;
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kTolerance = 5;
    private static final double MAX_TURN_RATE = 0.5;

    private enum TurnMode {OFF, ASSIST}

    private TurnMode currentMode;

    public GyroAssistTankDriveCommand() {
        super("Gyro Assist Tank Drive Command", kP, kI, kD);
        requires(Robot.driveSubsystem);

        currentMode = TurnMode.OFF;

        getPIDController().setAbsoluteTolerance(kTolerance);
        getPIDController().setInputRange(0, 360);
        getPIDController().setContinuous(true);
        getPIDController().setOutputRange(-MAX_TURN_RATE, MAX_TURN_RATE);
        getPIDController().disable();
    }

    @Override
    protected void initialize() {
        // do nothing?
    }

    @Override
    protected void execute() {
        double joyTurnIn = Robot.oi.getDriveRightY() - Robot.oi.getDriveLeftY();
        boolean shouldBeOn = Math.abs(joyTurnIn) <= TURN_DEADBAND && Math.abs(Robot.driveSubsystem.getTurnRate()) < 0.1;

        switch (currentMode) {
            case OFF:
                setSetpoint(Robot.driveSubsystem.getHeading());
                if (shouldBeOn) {
                    getPIDController().enable();
                    currentMode = TurnMode.ASSIST;
                } else {
                    // this is a bit lazy, but usePIDOutput uses a given output from the PID
                    // to turn with the drive subsystem... so instead of rewriting it treat
                    // the joystick input as if it were output from the PID
                    usePIDOutput(joyTurnIn);
                }
                break;

            case ASSIST:
                if (!shouldBeOn) {
                    getPIDController().disable();
                    usePIDOutput(joyTurnIn);
                    currentMode = TurnMode.OFF;
                }
                break;
        }
    }

    @Override
    protected void end() {
        Robot.driveSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected double returnPIDInput() {
        return Robot.driveSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        double joyForward = (Robot.oi.getDriveLeftY() + Robot.oi.getDriveRightY()) / 2;
        Robot.driveSubsystem.arcadeDrive(joyForward, output);
    }
}
