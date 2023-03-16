package frc.robot.lib.frc7682;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import frc.robot.Constants;

public class ArmOdometry {

    private Pose3d armPosition;

    public static ArmOdometry armOdometry = null;

    public static final ArmOdometry getInstance(){
        if(armOdometry == null){
            armOdometry = new ArmOdometry();
        }
        return armOdometry;
    }

    public ArmOdometry(){
        armPosition = new Pose3d();
    }

    public void update(double turretAngle, double shoulderAngle, double armLength){
        double armX = (Math.cos(turretAngle) * (Math.cos(shoulderAngle) * armLength));
        double armY = (Math.sin(turretAngle) * (Math.cos(shoulderAngle) * armLength));
        double armZ = (Constants.ArmConstants.DEFAULT_ARM_LENGTH + (Math.sin(shoulderAngle) * armLength));
        armPosition = new Pose3d(armX, armY, armZ, new Rotation3d());
    }

    public Pose3d getEstimatedPosition(){
        return armPosition;
    }

    public void reset(){
        armPosition = new Pose3d();
    }
    
}
