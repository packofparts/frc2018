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

    public DriveSubsystem() {
        super("Drivetrain Subsystem");

        leftFront = new WPI_TalonSRX(RobotMap.TALON_LEFT_FRONT);
        leftRear = new WPI_TalonSRX(RobotMap.TALON_LEFT_REAR);
        rightFront = new WPI_TalonSRX(RobotMap.TALON_RIGHT_FRONT);
        rightRear = new WPI_TalonSRX(RobotMap.TALON_RIGHT_REAR);

        leftFront.setInverted(true);
        leftRear.setInverted(true);

        leftRear.set(ControlMode.Follower, RobotMap.TALON_LEFT_FRONT);
        rightRear.set(ControlMode.Follower, RobotMap.TALON_RIGHT_FRONT);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                0);
        leftFront.setSensorPhase(true);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                0);
        rightFront.setSensorPhase(true);

        navX = new AHRS(SPI.Port.kMXP);

        drive = new DifferentialDrive(leftFront, rightFront);

        SmartDashboard.putNumber("LeftTalon.p", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
        SmartDashboard.putNumber("LeftTalon.i", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 10));
        SmartDashboard.putNumber("LeftTalon.d", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 10));

        SmartDashboard.putNumber("RightTalon.p", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 10));
        SmartDashboard.putNumber("RightTalon.i", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 10));
        SmartDashboard.putNumber("RightTalon.d", rightFront.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 10));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Encoder", getEncoderPositionLeft());
        SmartDashboard.putNumber("Right Encoder", getEncoderPositionRight());
        SmartDashboard.putNumber("Average Encoder", getEncoderPositionAverage());
        SmartDashboard.putNumber("Gyro Angle", getHeading());

        SmartDashboard.putNumber("Left Raw Encoder", leftFront.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Right Raw Encoder", rightFront.getSelectedSensorVelocity(0));


        leftFront.configSetParameter(ParamEnum.eProfileParamSlot_P, SmartDashboard.getNumber("LeftTalon.p", 1.0), 0, 0, 10);
        leftFront.configSetParameter(ParamEnum.eProfileParamSlot_I, SmartDashboard.getNumber("LeftTalon.i", 0), 0, 0, 10);
        leftFront.configSetParameter(ParamEnum.eProfileParamSlot_D, SmartDashboard.getNumber("LeftTalon.d", 0), 0, 0, 10);

        rightFront.configSetParameter(ParamEnum.eProfileParamSlot_P, SmartDashboard.getNumber("RightTalon.p", 1.0), 0, 0, 10);
        rightFront.configSetParameter(ParamEnum.eProfileParamSlot_I, SmartDashboard.getNumber("RightTalon.i", 0), 0, 0, 10);
        rightFront.configSetParameter(ParamEnum.eProfileParamSlot_D, SmartDashboard.getNumber("RightTalon.d", 0), 0, 0, 10);
//        SmartDashboard.putNumber("LeftTalon.p", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 0));
//        SmartDashboard.putNumber("LeftTalon.i", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 0));
//        SmartDashboard.putNumber("LeftTalon.d", leftFront.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 0));
    }

    public void arcadeDrive(double forward, double turn) {
        drive.setSafetyEnabled(true);
        drive.arcadeDrive(forward, turn);
    }

    public void autoDrive(double left, double right) {
        drive.setSafetyEnabled(false);
        leftFront.set(ControlMode.Velocity, left);
        rightFront.set(ControlMode.Velocity, right);
    }

    public void stop() {
        arcadeDrive(0.0, 0.0);
    }

    public void resetEncoders() {
        leftFront.setSelectedSensorPosition(0, 0, 0);
        rightFront.setSelectedSensorPosition(0, 0, 0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveCommand());
    }

    public double getEncoderPositionLeft() {
        return leftFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderPositionRight() {
        return rightFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderVelocityLeft() {
        return leftFront.getSelectedSensorVelocity(0) * kEncoderScale;
    }

    public double getEncoderVelocityRight() {
        return rightFront.getSelectedSensorVelocity(0) * kEncoderScale;
    }

    public double getHeading() {
        return navX.getAngle() % 360;
    }

    public double getTurnRate() {
        return navX.getRate();
    }

    public void resetGyro() {
        navX.reset();
    }

    public double getEncoderPositionAverage() {
        return (getEncoderPositionLeft() + getEncoderPositionRight()) / 2;
    }

}
