package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.ArcadeDriveCommand;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    private WPI_TalonSRX leftFront;
    private WPI_TalonSRX leftRear;
    private WPI_TalonSRX rightFront;
    private WPI_TalonSRX rightRear;
    private DifferentialDrive drive;

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
        leftFront.setSensorPhase(false);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                0);
        rightFront.setSensorPhase(true);

        drive = new DifferentialDrive(leftFront, rightFront);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Encoder", leftFront
                .getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Right Encoder", rightFront
                .getSelectedSensorPosition(0));
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
}
