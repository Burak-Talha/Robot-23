package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {

    public static class DriveConstants{
        public static final double WHEEL_DIAMETER_CM = 50.24;
        public static final double ENCODER_PPR = 1024;
        public static final double REDUCTION_RATE = 0.25;
        public static final double KDRIVETICK2METER = WHEEL_DIAMETER_CM / ENCODER_PPR * REDUCTION_RATE;

        public static final DifferentialDriveKinematics DIFFERENTIAL_DRIVE_KINEMATICS = new DifferentialDriveKinematics(0.27);
    }

    public static class ArmConstants{
        public static final double DEFAULT_ARM_LENGTH = 1.2;
        public static final double k_ARM_ANGLE_OFFSET = 30;
        public static final double ARM_PPR = 48;
        public static final double K_ARMTICK2DEGREE = 360 / ARM_PPR;
        public static final double K_EXTENSIBLE_TICK2METER = 0;
    }

    public static class VisionConstants{
        public static String CAM_NAME = "";
    }
    
}
