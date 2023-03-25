package frc.robot.lib.frc7682;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;

public class FusionJoystick extends Joystick{

    private SlewRateLimiter slewRateLimiter;
    private double deadband = 0.1;
    private double inputFactor = 1.0;
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

    public void setInputFactor(double inputFactor){
        this.inputFactor = inputFactor;
    }

    @Override
    public double getRawAxis(int axis) {
        double value = super.getRawAxis(axis);
        return slewRateLimiter.calculate(value - deadband)*inputFactor;
    }
}
