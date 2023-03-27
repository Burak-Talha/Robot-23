package frc.robot.controlpanel;

import frc.robot.Constants;
import frc.robot.lib.frc7682.FusionJoystick;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

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
        return operatorStick.getTwist();
    }

    @Override
    public double turretDegrees() {
        return operatorStick.getDirectionDegrees();
    }

    @Override
    public boolean GETTING() {
        return operatorStick.getRawButton(1);
    }

    @Override
    public boolean POSTING() {
        return operatorStick.getRawButton(2);
    }

    @Override
    public boolean TURTLING() {
        return operatorStick.getRawButton(3);
    }

    @Override
    public boolean BALANCING() {
        return operatorStick.getRawButton(4);
    }

    @Override
    public boolean MANUAL() {
        return operatorStick.getRawButton(5);
    }

}
