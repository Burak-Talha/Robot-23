package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc254.util.SynchronousPIDF;

public class Gripper extends Subsystem{


    CANSparkMax intakeMaster;
    RelativeEncoder intakeEncoder;
    SynchronousPIDF intakePidf;
    SuperStructure superStructure = SuperStructure.getInstance();

    public enum IntakeMode{
        AUTO,
        MANUAL
    }

    private IntakeMode currentIntakeMode = IntakeMode.MANUAL;

    Gripper(){
        intakeMaster = new CANSparkMax(Constants.GripperConstants.INTAKE_MASTER_ID, MotorType.kBrushless);
        intakePidf = new SynchronousPIDF(Constants.GripperConstants.INTAKE_KP, Constants.GripperConstants.INTAKE_KI, Constants.GripperConstants.INTAKE_KD, Constants.GripperConstants.INTAKE_KF);
        intakeEncoder = intakeMaster.getEncoder();
    }

    private static Gripper intake = null;

    public static Gripper getInstance(){
        if(intake == null){
            intake = new Gripper();
        }
        return intake;
    }

    public class PeriodicIO{
        public double intakeRPM;
    }

    private PeriodicIO periodicIO = new PeriodicIO();

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        intakeMaster.set(periodicIO.intakeRPM);
        
        if(!isEncoderFaultExist() && currentIntakeMode == IntakeMode.AUTO){
            calculateOuttakeDemand();
        }
    }

    public void setIntakeMode(IntakeMode mode){
        currentIntakeMode = mode;
    }

    public void setSetpointOuttake(){
        intakePidf.setSetpoint(Constants.GripperConstants.INTAKE_RPM_RATE * superStructure.getTargetDistanceToArm());
    }

    private void calculateOuttakeDemand(){
        periodicIO.intakeRPM = intakePidf.calculate(getRPM());
    }

    public void manualOut(){
        periodicIO.intakeRPM = -500;
    }

    public void manualIn(){
        periodicIO.intakeRPM = 500;
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

    public boolean isEncoderFaultExist(){
        return intakeMaster.getFault(FaultID.kSensorFault);
    }

    public double getRPM(){
        return intakeEncoder.getVelocity();
    }

    @Override
    public void stop() {
        intakeMaster.stopMotor();
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
