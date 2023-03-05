package frc.robot.subsystems;

import frc.robot.RobotState;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc7682.TargetFinder;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;
import frc.robot.lib.frc7682.TargetFinder.TargetType;
import frc.robot.subsystems.Arm.ProcessingType;

public class SuperStructure extends Subsystem{

    private RobotState robotState = RobotState.getInstance();
    private TargetFinder targetFinder = TargetFinder.getInstance();
    private Arm arm = Arm.getInstance();
    private Turret turret = Turret.getInstance();
    private Drive drive = Drive.getInstance();
    private Infrastructure infrastructure = Infrastructure.getInstance();

    public enum SystemState{
        GETTING,
        POSTING,
        TURTLING,
        BALANCING,
        IDLE,
        MANUAL
    }

    private static SuperStructure superStructure = null;

    public static SuperStructure getInstance(){
        if(superStructure == null){
            superStructure = new SuperStructure();
        }
        return superStructure;
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        robotState.update(drive.getRotation2d(), drive.getLeftEncoderMeter(), drive.getRightEncoderMeter());
        targetFinder.update(robotState.armPositionPose3d());
    }

    // General PID Setpoint methods
    

    // General information methods
    public boolean isArmReady(){
        return false;
    }

    public boolean isTurretReady(){
        return false;
    }

    public boolean isExtensibleReady(){
        return false;
    }

    public double getTargetDistanceToArm(){
        return 0;
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
