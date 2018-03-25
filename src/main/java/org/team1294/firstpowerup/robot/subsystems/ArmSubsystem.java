package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveArmWithJoystickCommand;

/**
 * @author Abhinav Diddee (heatblast016)
 */
public class ArmSubsystem extends Subsystem {
    private TalonSRX armMotor;
    private TalonSRX wristMotor;
    private TalonSRX extendMotor;
    private Wrist currentStatus;
    private double pos;

    private enum Wrist {
        IN(408), OUT(191);

        private final double angle;

        Wrist(double angle) {
            this.angle = angle;
        }

    }
    public enum Telescope {
        IN(0), OUT(97);
        public final double distance;

        Telescope(double distance) {
            this.distance = distance;
        }

    }
    public enum ArmHeight {
        SCALE(404), FLOOR(994), SWITCH(892);

        public final int height;

        ArmHeight(int height) {
            this.height = height;
        }

    }
    public ArmSubsystem() {
        super("Arm Subsystem");
        armMotor = new TalonSRX(RobotMap.TALON_ARM);
        wristMotor = new TalonSRX(RobotMap.TALON_WRIST);
        extendMotor = new TalonSRX(RobotMap.TALON_ARM_EXTENSION);
        currentStatus = Wrist.OUT;

        wristMotor.setNeutralMode(NeutralMode.Brake);
        extendMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.setNeutralMode(NeutralMode.Brake);

        armMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, RobotMap.CTRE_TIMEOUT_INIT);
        wristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, RobotMap.CTRE_TIMEOUT_INIT);
        extendMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, RobotMap.CTRE_TIMEOUT_INIT);

        // reset the encoders for the wrist and extend
        // we're assuming that when the robot starts up we're in transport config.
        // arm has a potentiometer, a scale of voltage - no need to reset
        wristMotor.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_INIT);
//        extendMotor.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_INIT);

        armMotor.config_kP(0, 1, RobotMap.CTRE_TIMEOUT_INIT);
        armMotor.config_kI(0, 0.01, RobotMap.CTRE_TIMEOUT_INIT);

        extendMotor.config_kP(0, 1.0, RobotMap.CTRE_TIMEOUT_INIT);

        armMotor.setNeutralMode(NeutralMode.Coast);
        wristMotor.setNeutralMode(NeutralMode.Coast);

        pos = Telescope.IN.distance;

        extendMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, RobotMap.CTRE_TIMEOUT_INIT);
        extendMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, RobotMap.CTRE_TIMEOUT_INIT);
        extendMotor.configReverseSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT_INIT);
        extendMotor.configForwardSoftLimitEnable(false, RobotMap.CTRE_TIMEOUT_INIT);
        setArmSoftLimits(44,1016);
    }
    public void resetEncoders(){
        extendMotor.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_PERIODIC);
        wristMotor.setSelectedSensorPosition(0, 0, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }

    public void toggleWristDeploy() {
        if (currentStatus == Wrist.OUT) {
            setWristIn();
        } else {
            setWristOut();
        }
    }

    public void setWristIn() {
            currentStatus = Wrist.IN;
            updateWristPosition();
    }

    public void setWristOut() {
        currentStatus = Wrist.OUT;
        updateWristPosition();
    }

    public void setExtendPID(double setpoint) {
        extendMotor.set(ControlMode.Position, setpoint);
    }

    public void setExtendMotionMagic(double setpoint) {
        extendMotor.set(ControlMode.MotionMagic, setpoint);
    }

    private void updateWristPosition() {
        wristMotor.set(ControlMode.Position, currentStatus.angle);
    }

    public void setArmHeight(double height) {
        armMotor.set(ControlMode.Position, height);
    }
    public void driveArmPercentOut(double percent) {
        armMotor.set(ControlMode.PercentOutput, percent);
    }

    public void driveExtendPercentOut(double percent) { extendMotor.set(ControlMode.PercentOutput, percent);}

    public void driveWristPercentOut(double percent) {
        wristMotor.set(ControlMode.PercentOutput, percent);
    }

    public double getArmHeight() {
        return armMotor.getSelectedSensorPosition(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveArmWithJoystickCommand());
    }

    public void setArmSoftLimits(int reverseSoftLimit, int forwardSoftLimit) {
        armMotor.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configReverseSoftLimitThreshold(reverseSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);

        armMotor.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configForwardSoftLimitThreshold(forwardSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }

    public void setExtendSoftLimits(int reverseSoftLimit, int forwardSoftLimit) {
        extendMotor.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        extendMotor.configReverseSoftLimitThreshold(reverseSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);

        extendMotor.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        extendMotor.configForwardSoftLimitThreshold(forwardSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }

    public boolean isWristIn()
    {
        return currentStatus == Wrist.IN;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("wrist enc", wristMotor.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Telescoping Encoder", extendMotor.getSelectedSensorPosition(0));
    }

    public double getExtensionSensorValue() {
        return extendMotor.getSelectedSensorPosition(0);
    }

    public double getWristAngle() {
        return 0.0; // todo
    }

    public void changeExtendPos(double change) {
        setExtendPos(pos + change);
    }

    /**
     * Warning: there is NO SANITY CHECKING on this value. the extension will immediately go to this value.
     * @param newPos
     */
    public void setExtendPos(double newPos) {
        if (newPos < pos) {
            extendMotor.set(ControlMode.PercentOutput, -0.2);
        } else if (newPos > pos) {
            extendMotor.set(ControlMode.PercentOutput, 0.2);
        } else {
            extendMotor.set(ControlMode.PercentOutput, 0);
        }
        pos = newPos;
//        extendMotor.set(ControlMode.Position, pos);
    }

    public int getTelescopePIDError() {
        return extendMotor.getClosedLoopError(0);
    }

    public int getWristError() {
        return wristMotor.getClosedLoopError(0);
    }
}