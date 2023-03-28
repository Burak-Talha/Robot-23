package frc.robot.controlpanel;

import frc.robot.Constants;
import frc.robot.lib.frc7682.FusionJoystick;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

public class OperatorPanel implements IOperatorPanel{

    private FusionJoystick operatorStick;

    public OperatorPanel(){
        this.operatorStick = new FusionJoystick(Constants.JoystickConstants.OPERATOR_CONTROLLER_PORT);
    }

    @Override
    public DesiredPosition desiredTargetPosition() {
        if(operatorStick.getRawButton(4)){
            return DesiredPosition.UP;
        }
        else if(operatorStick.getRawButton(5)){
            return DesiredPosition.MID;
        }
        return DesiredPosition.DOWN;
    }

    @Override
    public double shoulderDegrees() {
        return operatorStick.getThrottle();
    }

    @Override
    public double xAxis(){
        return operatorStick.getX();
    }

    @Override
    public double yAxis(){
        return operatorStick.getY();
    }

    @Override
    public boolean getIn() {
        return operatorStick.getRawButton(1);
    }

    @Override
    public boolean getOut() {
        return operatorStick.getRawButton(2);
    }

   

}
