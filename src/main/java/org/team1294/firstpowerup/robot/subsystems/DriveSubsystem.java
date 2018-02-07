package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.ParamEnum;
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

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    private static final double kEncoderScale = 0.00026093732850;

    private final WPI_TalonSRX leftFront;
    private final WPI_TalonSRX leftRear;
    private final WPI_TalonSRX rightFront;
    private final WPI_TalonSRX rightRear;
    private final DifferentialDrive drive;
    private final AHRS navX;
    public static final String ENCODER_PREFIX = "Drive/Encoders/";

    public DriveSubsystem() {
        super("Drivetrain Subsystem");

        leftFront = new WPI_TalonSRX(RobotMap.TALON_LEFT_FRONT);
        leftRear = new WPI_TalonSRX(RobotMap.TALON_LEFT_REAR);
        rightFront = new WPI_TalonSRX(RobotMap.TALON_RIGHT_FRONT);
        rightRear = new WPI_TalonSRX(RobotMap.TALON_RIGHT_REAR);

        leftRear.set(ControlMode.Follower, RobotMap.TALON_LEFT_FRONT);
        rightRear.set(ControlMode.Follower, RobotMap.TALON_RIGHT_FRONT);

        leftFront.setInverted(true);
        leftRear.setInverted(true);

        leftFront.selectProfileSlot(0, 10);
        rightFront.selectProfileSlot(0, 10);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        leftFront.setSensorPhase(false);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rightFront.setSensorPhase(false);

        /*
         * TODO: Retune Velocity PID.
         * This code sets the output to be within [-1, 1], like the AutoDrive command expects.
         * That screws up the PID.
         */

        leftFront.configNominalOutputForward(0, 10);
        leftRear.configNominalOutputForward(0, 10);
        rightFront.configNominalOutputForward(0, 10);
        rightRear.configNominalOutputForward(0, 10);

        leftFront.configNominalOutputReverse(0, 10);
        leftRear.configNominalOutputReverse(0, 10);
        rightFront.configNominalOutputReverse(0, 10);
        rightRear.configNominalOutputReverse(0, 10);

        leftFront.configPeakOutputForward(1, 10);
        leftRear.configPeakOutputForward(1, 10);
        rightFront.configPeakOutputForward(1, 10);
        rightRear.configPeakOutputForward(1, 10);

        leftFront.configPeakOutputReverse(-1, 10);
        leftRear.configPeakOutputReverse(-1, 10);
        rightFront.configPeakOutputReverse(-1, 10);
        rightRear.configPeakOutputReverse(-1, 10);

        drive = new DifferentialDrive(leftFront, rightFront);

        SmartDashboard.putNumber("LeftTalon.p", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
        SmartDashboard.putNumber("LeftTalon.i", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 10));
        SmartDashboard.putNumber("LeftTalon.d", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 10));

        SmartDashboard.putNumber("RightTalon.p", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
        SmartDashboard.putNumber("RightTalon.i", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 10));
        SmartDashboard.putNumber("RightTalon.d", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 10));

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

        leftFront.config_kP(0, SmartDashboard.getNumber("LeftTalon.p", 0.5),
                0);
        leftFront.config_kI(0, SmartDashboard.getNumber("LeftTalon.i",
                0.005),
                0);
        leftFront.config_kD(0, SmartDashboard.getNumber("LeftTalon.d", 0.0),
                0);

        rightFront.config_kP(0, SmartDashboard.getNumber("RightTalon.p", 0.5),
                0);
        rightFront.config_kI(0, SmartDashboard.getNumber("RightTalon.i",
                0.005),
                0);
        rightFront.config_kD(0, SmartDashboard.getNumber("RightTalon.d", 0.0),
                0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveCommand());
    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, -turn);
    }

    public void autoDrive(double left, double right) {
        leftFront.set(ControlMode.Velocity, -left);
        rightFront.set(ControlMode.Velocity, right);
    }

    public void stop() {
        arcadeDrive(0.0, 0.0);
    }

    public double getEncoderPositionLeft() {
        return leftFront.getSelectedSensorPosition(0) * kEncoderScale;
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
        return getRawEncoderVelocityLeft() * kEncoderScale;
    }

    public double getEncoderVelocityRight() {
        return getRawEncoderVelocityRight() * kEncoderScale;
    }

    public void resetEncoders() {
        leftFront.setSelectedSensorPosition(0, 0, 0);
        rightFront.setSelectedSensorPosition(0, 0, 0);
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
