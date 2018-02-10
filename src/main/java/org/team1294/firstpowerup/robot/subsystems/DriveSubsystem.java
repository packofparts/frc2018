package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.ArcadeDriveCommand;
import org.team1294.firstpowerup.robot.commands.GyroAssistDriveCommand;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    private static final double kEncoderScale = 0.00026093732850;
    public static final String ENCODER_PREFIX = "Drive/Encoders/";

    private final WPI_TalonSRX leftFront;
    private final WPI_TalonSRX leftRear;
    private final WPI_TalonSRX rightFront;
    private final WPI_TalonSRX rightRear;
    private final DifferentialDrive drive;
    private final AHRS navX;

    public DriveSubsystem() {
        super("Drivetrain Subsystem");

        leftFront = new WPI_TalonSRX(RobotMap.TALON_LEFT_FRONT);
        leftRear = new WPI_TalonSRX(RobotMap.TALON_LEFT_REAR);
        rightFront = new WPI_TalonSRX(RobotMap.TALON_RIGHT_FRONT);
        rightRear = new WPI_TalonSRX(RobotMap.TALON_RIGHT_REAR);

        leftRear.follow(leftFront);
        rightRear.follow(rightFront);

        leftFront.configOpenloopRamp(1, RobotMap.CTRE_TIMEOUT_INIT);
        rightFront.configOpenloopRamp(1, RobotMap.CTRE_TIMEOUT_INIT);

        rightFront.setInverted(true);
        rightRear.setInverted(true);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.CTRE_TIMEOUT_INIT);
        leftFront.setSensorPhase(false);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.CTRE_TIMEOUT_INIT);
        rightFront.setSensorPhase(true);

        drive = new DifferentialDrive(leftFront, rightFront);

        navX = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber(ENCODER_PREFIX + "Left/Pos", getEncoderPositionLeft());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Right/Pos", getEncoderPositionRight());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Avg/Pos", getEncoderPositionAverage());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Left/Vel", getEncoderVelocityLeft());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Right/Vel", getEncoderVelocityRight());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Left/VelRaw", getRawEncoderVelocityLeft());
        SmartDashboard.putNumber(ENCODER_PREFIX + "Right/VelRaw", getRawEncoderVelocityRight());

        SmartDashboard.putNumber("Drive/Gyro/Angle", getHeading());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GyroAssistDriveCommand());
    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, turn);
    }

    public void autoDrive(double left, double right) {
        leftFront.set(ControlMode.Velocity, -left);
        rightFront.set(ControlMode.Velocity, right);
    }

    public void stop() {
        arcadeDrive(0.0, 0.0);
    }

    public double getEncoderPositionLeft() {
        return -leftFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderPositionRight() {
        return rightFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderPositionAverage() {
        return (getEncoderPositionLeft() + getEncoderPositionRight()) / 2;
    }

    public double getRawEncoderVelocityLeft() {
        return leftFront.getSelectedSensorVelocity(0);
    }

    public double getRawEncoderVelocityRight() {
        return rightFront.getSelectedSensorVelocity(0);
    }

    public double getEncoderVelocityLeft() {
        return -getRawEncoderVelocityLeft() * kEncoderScale;
    }

    public double getEncoderVelocityRight() {
        return getRawEncoderVelocityRight() * kEncoderScale;
    }

    public void resetEncoders() {
        leftFront.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_PERIODIC);
        rightFront.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }

    public double getHeading() {
        return Math.abs(navX.getAngle() % 360);
    }

    public double getTurnRate() {
        return navX.getRate();
    }

    public void resetGyro() {
        navX.reset();
    }

    public void setSafetyEnabled(boolean safetyEnabled) {
        drive.setSafetyEnabled(safetyEnabled);
    }
}
