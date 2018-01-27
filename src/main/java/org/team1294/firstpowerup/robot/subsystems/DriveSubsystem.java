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

        leftRear.set(ControlMode.Follower, RobotMap.TALON_LEFT_FRONT);
        rightRear.set(ControlMode.Follower, RobotMap.TALON_RIGHT_FRONT);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                0);
        leftFront.setSensorPhase(true);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                0);
        rightFront.setSensorPhase(false);

        navX = new AHRS(SPI.Port.kMXP);

        drive = new DifferentialDrive(leftFront, rightFront);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Encoder", getEncoderPositionLeft());
        SmartDashboard.putNumber("Right Encoder", getEncoderPositionRight());
        SmartDashboard.putNumber("Average Encoder", getEncoderPositionAverage());
        SmartDashboard.putNumber("Gyro Angle", getHeading());
    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, turn);
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

    public double getEncoderPositionLeft(){
        return leftFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderPositionRight(){
        return rightFront.getSelectedSensorPosition(0) * kEncoderScale;
    }

    public double getEncoderVelocityLeft(){
        return leftFront.getSelectedSensorVelocity(0) * kEncoderScale;
    }

    public double getEncoderVelocityRight(){
        return rightFront.getSelectedSensorVelocity(0) * kEncoderScale;
    }

    public double getHeading(){
        return navX.getAngle() % 360;
    }

    public double getTurnRate(){
        return navX.getRate();
    }

    public void resetGyro(){
        navX.reset();
    }

    public double getEncoderPositionAverage() {
        return (getEncoderPositionLeft() + getEncoderPositionRight()) / 2;
    }

}
