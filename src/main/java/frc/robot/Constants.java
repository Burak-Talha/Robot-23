package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {

    public static class DriveConstants{
        public static final int MASTER_LEFT_ID = 0;
        public static final int SLAVE_LEFT_ID = 1;
        public static final int MASTER_RIGHT_ID = 2;
        public static final int SLAVE_RIGHT_ID = 3;

        public static final double WHEEL_DIAMETER_CM = 50.24;
        public static final double ENCODER_PPR = 1024;
        public static final double REDUCTION_RATE = 0.25;
        public static final double KDRIVETICK2METER = WHEEL_DIAMETER_CM / ENCODER_PPR * REDUCTION_RATE;

        public static final DifferentialDriveKinematics DIFFERENTIAL_DRIVE_KINEMATICS = new DifferentialDriveKinematics(0.27);
    }

    public static class ArmConstants{

        // Shoulder
        public static final int MASTER_ROTATABLE_ID = 4;
        public static final int SLAVE_ROTATABLE_ID = 5;
        public static final double SHOULDER_KP = 0;
        public static final double SHOULDER_KI = 0;
        public static final double SHOULDER_KD = 0;
        public static final double SHOULDER_KF = 0;
        public static final double SHOULDER_UP_LIMIT = 0;
        public static final double SHOULDER_DOWN_LIMIT = 0;
        public static final double k_ARM_ANGLE_OFFSET = 30;
        public static final double SHOULDER_PPR = 48;
        // Extensible
        public static final int MASTER_EXTENSION_ID = 6;
        public static final double MAX_ARM_LENGTH = 1.5;
        public static final double DEFAULT_ARM_LENGTH = 1.2;
        public static final double EXTENSIBLE_KP = 0;
        public static final double EXTENSIBLE_KI = 0;
        public static final double EXTENSIBLE_KD = 0;
        public static final double EXTENSIBLE_KF = 0;
        public static final double EXTENSIBLE_UP_LIMIT = 0;
        public static final double EXTENSIBLE_DOWN_LIMIT = 0;
        // Calculations
        public static final double K_ARMTICK2DEGREE = 360 / SHOULDER_PPR;
        public static final double K_ARM_HEIGHT_M = 0.40;
        public static final double K_EXTENSIBLE_TICK2METER = 0;
    }

    public static class VisionConstants{
        public static String CAM_NAME = "";
    }
    
}
