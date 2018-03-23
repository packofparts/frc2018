package org.team1294.firstpowerup.robot.commands
import edu.wpi.first.wpilibj.command.Command
import org.team1294.firstpowerup.robot.Robot
import org.team1294.firstpowerup.robot.subsystems.ArmSubsystem

class ExtendoArmInOutCommand(val telescope : ArmSubsystem.Telescope) : Command("Extendo arm out command") {

    override fun initialize() {
        Robot.armSubsystem.setExtendPos(telescope.distance)
    }

    private val TOLERANCE: Double = 10.0

    override fun isFinished(): Boolean {
        return Math.abs(Robot.armSubsystem.telescopePIDError) <= TOLERANCE
    }
}