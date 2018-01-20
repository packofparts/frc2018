package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
    private final AHRS navX;

    public DriveSubsystem() {
        super("Drivetrain Subsystem");
        leftFront = new WPI_TalonSRX(RobotMap.TALON_LEFT_FRONT);
        leftRear = new WPI_TalonSRX(RobotMap.TALON_LEFT_REAR);
        rightFront = new WPI_TalonSRX(RobotMap.TALON_RIGHT_FRONT);
        rightRear = new WPI_TalonSRX(RobotMap.TALON_RIGHT_REAR);

        SpeedControllerGroup left = new SpeedControllerGroup(leftFront,
                leftRear);
        SpeedControllerGroup right = new SpeedControllerGroup(rightFront,
                rightRear);

        drive = new DifferentialDrive(left, right);
        navX = new AHRS(SPI.Port.kMXP);
    }

    public void arcadeDrive(double forward, double turn) {
        drive.arcadeDrive(forward, turn);
    }

    public void stop() {
        arcadeDrive(0.0, 0.0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArcadeDriveCommand());
    }

    public double getEncoderPositionLeft(){
        return leftFront.getSelectedSensorPosition(0);
    }

    public double getEncoderPositionRight(){
        return rightFront.getSelectedSensorPosition(0);
    }

    public double getEncoderVelocityLeft(){
        return leftFront.getSelectedSensorVelocity(0);
    }

    public double getEncoderVeloityRight(){
        return rightFront.getSelectedSensorVelocity(0);
    }

    public double getHeading(){
        double angle = navX.getAngle() % 360;
        return angle;
    }

    public double getTurnRate(){
        return navX.getRate();
    }

    public void resetGyro(){
        navX.reset();
    }

    public void resetEncoders(){
        leftFront.setSelectedSensorPosition(0,0,10);
        rightFront.setSelectedSensorPosition(0,0,10);
    }


    public double getEncoderPositionAverage() {
        return (getEncoderPositionLeft() + getEncoderPositionRight()) / 2;
    }

}
