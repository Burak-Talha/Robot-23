package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.frc254.Subsystem;

public class Infrastructure extends Subsystem{
    
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

    @Override
    public void readPeriodicInputs() {
     SmartDashboard.putString("Battery State :", getHealthState().name());   
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

    @Override
    public void stop() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean checkSystem() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        
    }

}
