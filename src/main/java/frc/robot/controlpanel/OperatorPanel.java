package frc.robot.controlpanel;

import frc.robot.Constants;
import frc.robot.lib.frc7682.FusionJoystick;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.subsystems.SuperStructure.SystemState;

public class OperatorPanel implements IOperatorPanel{

    private FusionJoystick operatorStick;

    OperatorPanel(FusionJoystick operatorStick){
        this.operatorStick = new FusionJoystick(Constants.JoystickConstants.OPERATOR_CONTROLLER_PORT);
    }

    @Override
    public DesiredPosition desiredTargetPosition() {
        double value = operatorStick.getThrottle();
        if(value > 0.66){
            return DesiredPosition.UP;
        }
        else if(value > 0.33){
            return DesiredPosition.MID;
        }
        else{
            return DesiredPosition.DOWN;
        }
    }

    @Override
    public double shoulderDegrees() {
        // TODO Auto-generated method stub
        return operatorStick.getTwist();
    }

    @Override
    public double turretDegrees() {
        return operatorStick.getDirectionDegrees();
    }

    @Override
    public SystemState GETTING() {
        return operatorStick.getRawButton(1) ? SystemState.GETTING : SystemState.MANUAL;
    }

    @Override
    public SystemState POSTING() {
        return operatorStick.getRawButton(2) ? SystemState.POSTING : SystemState.MANUAL;
    }

    @Override
    public SystemState TURTLING() {
        return operatorStick.getRawButton(3) ? SystemState.TURTLING : SystemState.MANUAL;
    }

    @Override
    public SystemState BALANCING() {
        return operatorStick.getRawButton(4) ? SystemState.BALANCING : SystemState.MANUAL;
    }

    @Override
    public SystemState MANUAL() {
        return SystemState.MANUAL;
    }
    
}
