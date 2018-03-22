package org.team1294.firstpowerup.robot.commands
import edu.wpi.first.wpilibj.command.Command
import org.team1294.firstpowerup.robot.Robot

class ExtendoArmInOutCommand(val goingIn : Boolean) : Command("Extendo arm out command") {
    private val max : Double = 4000.0
    private val min : Double = 15.0
    private var target : Double = 0.0

    init {
        if(goingIn)
        {
            Robot.armSubsystem.setExtendPID(min)
            target = min
        }
        else
        {
            Robot.armSubsystem.setExtendPID(max)
            target = max
        }
    }
    override fun isFinished(): Boolean {
        return Robot.armSubsystem.extensionSensorValue == target
    }
}