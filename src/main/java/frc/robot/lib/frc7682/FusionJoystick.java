package frc.robot.lib.frc7682;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;

public class FusionJoystick extends Joystick{

    private SlewRateLimiter slewRateLimiter;
    private double deadband = 0.1;
    private DesiredValue currentDesiredValue = DesiredValue.MID;

    public enum DesiredValue{
        UP,
        MID,
        DOWN
    }
    

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
    
    public DesiredValue getDesiredPosition(){
        double value = super.getThrottle();
        if(value > 0.66){
            currentDesiredValue = DesiredValue.UP;
        }
        else if(value > 0.33){
            currentDesiredValue = DesiredValue.MID;
        }
        else{
            currentDesiredValue = DesiredValue.DOWN;
        }
        return currentDesiredValue;
    }

    @Override
    public double getRawAxis(int axis) {
        double value = super.getRawAxis(axis);
        return slewRateLimiter.calculate(value - deadband);
    }
}
