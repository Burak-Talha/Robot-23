package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class Infrastructure {
    
    private final PowerDistribution powerDistribution;

    private HealthState currentHealthState;

    public enum HealthState{
        GOOD,
        UNDERLOAD,
        BAD
    }

    public Infrastructure(){
        powerDistribution = new PowerDistribution(0, ModuleType.kRev);
    }

    public static final Infrastructure getInstance(){
        return new Infrastructure();
    }

    public void handle(){
        if(getVoltage() < 7.5 && getTemperature() > 50){
            currentHealthState = HealthState.BAD;
        } else if(getVoltage() < 11 && getTemperature() > 45){
            currentHealthState = HealthState.UNDERLOAD;
        }
        else{
            currentHealthState = HealthState.GOOD;
        }
    }
    
    public HealthState getHealthState(){
        return currentHealthState;
    }

    public double getVoltage(){
        return powerDistribution.getVoltage();
    }

    public double getTemperature(){
        return powerDistribution.getTemperature();
    }

}
