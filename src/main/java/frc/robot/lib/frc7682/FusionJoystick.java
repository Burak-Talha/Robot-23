package frc.robot.lib.frc7682;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

public class FusionJoystick extends Joystick{

    private SlewRateLimiter slewRateLimiter;
    private double deadband = 0.1;
    private DesiredPosition currentDesiredValue = DesiredPosition.MID;
    

    public FusionJoystick(int port) {
        super(port);
        //TODO Auto-generated constructor stub
        slewRateLimiter = new SlewRateLimiter(0.5);
    }


    public void setDeadBand(double deadBand){
        this.deadband = deadBand;
    }

    public void setSlewRate(double slewRateGain){
        slewRateLimiter.reset(slewRateGain);
    }
    
    public DesiredPosition getDesiredPosition(){
        double value = super.getThrottle();
        if(value > 0.66){
            currentDesiredValue = DesiredPosition.UP;
        }
        else if(value > 0.33){
            currentDesiredValue = DesiredPosition.MID;
        }
        else{
            currentDesiredValue = DesiredPosition.DOWN;
        }
        return currentDesiredValue;
    }

    @Override
    public double getRawAxis(int axis) {
        double value = super.getRawAxis(axis);
        return slewRateLimiter.calculate(value - deadband);
    }
}
