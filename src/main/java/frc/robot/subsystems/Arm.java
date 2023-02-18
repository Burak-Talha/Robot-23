package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;

public class Arm extends Subsystem{

    private final CANSparkMax rotatableMaster;
    private final CANSparkMax rotatableSlave;
    private final CANSparkMax extensibleMotor;

    private RelativeEncoder extensibleEncoder;
    private RelativeEncoder rotatableEncoder;

    private PeriodicIO periodicIO = new PeriodicIO();

    public static Arm arm = null;

    public static Arm getInstance(){
        if(arm == null){
            arm = new Arm();
        }
        return arm;
    }

    Arm(){
        rotatableMaster = new CANSparkMax(4, MotorType.kBrushless);
        rotatableSlave = new CANSparkMax(5, MotorType.kBrushless);
        extensibleMotor = new CANSparkMax(6, MotorType.kBrushless);

        extensibleEncoder = extensibleMotor.getEncoder();
        rotatableEncoder = rotatableMaster.getEncoder();

        rotatableSlave.follow(rotatableMaster);
    }

    public static class PeriodicIO {
        double currentAngle;
        double currentExtensionDistance;
        double extensibleDemand;
        double rotatableDemand;
        double currentAngleSetpoint;
        double currentExtensionSetpoint;
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub

        rotatableMaster.set(periodicIO.rotatableDemand);
        extensibleMotor.set(periodicIO.extensibleDemand);
    }

    @Override
    public void readPeriodicInputs() {
        
        periodicIO.currentAngle = getShoulderAngle();
        periodicIO.currentExtensionDistance = getExtensionDistance();

        // Example Usage
        SmartDashboard.putNumber("Arm Angle", periodicIO.currentAngle);
    }


        public void setXYZ(){}

        // Motor Controllers Configurations
        public IdleMode getExtensibleMotorMode(){
            return extensibleMotor.getIdleMode();
        }

        public IdleMode getRotatableMotorMode(){
            return rotatableMaster.getIdleMode();
        }
    
        public void restoreFactory(){
            extensibleMotor.restoreFactoryDefaults();
            rotatableMaster.restoreFactoryDefaults();
            rotatableSlave.restoreFactoryDefaults();
        }
    
        public void stopSystem(){
            extensibleMotor.stopMotor();
            rotatableMaster.stopMotor();
        }
    
        public void openBrakeMode(){
            setNeutralMode(IdleMode.kBrake);
        }
    
        public void openCoastMode(){
            setNeutralMode(IdleMode.kCoast);
        }
    
        private void setNeutralMode(IdleMode mode){
            rotatableMaster.setIdleMode(mode);
            rotatableSlave.setIdleMode(mode);
            extensibleMotor.setIdleMode(mode);
        }

    public double getRotatableEncoderPpr(){
        return rotatableEncoder.getCountsPerRevolution() / 4;
    }

    public double getExtensibleEncoderPpr(){
        return extensibleEncoder.getCountsPerRevolution() / 4;
    }

    public double getShoulderAngle(){
        return getRotatableEncoderPpr() * Constants.ArmConstants.K_ARMTICK2DEGREE;
    }

    public double getExtensionDistance(){
        return getExtensibleEncoderPpr() * Constants.ArmConstants.K_EXTENSIBLE_TICK2METER;
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
