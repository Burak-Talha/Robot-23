package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.lib.frc254.Subsystem;

public class Intake extends Subsystem{


    CANSparkMax intakeMaster;


    Intake(){
        intakeMaster = new CANSparkMax(7, MotorType.kBrushless);
    }

    private static Intake intake = null;

    public Intake getInstance(){
        if(intake == null){
            intake = new Intake();
        }
        return intake;
    }


    public void restoreFactory(){
        intakeMaster.restoreFactoryDefaults();
    }

    public void stopSystem(){
        intakeMaster.stopMotor();
    }

    public void openBrakeMode(){
        setNeutralMode(IdleMode.kBrake);
    }

    public void openCoastMode(){
        setNeutralMode(IdleMode.kCoast);
    }

    private void setNeutralMode(IdleMode mode){
        intakeMaster.setIdleMode(mode);
    }

    private static class PeriodicIO{
        public double demand;
        
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
