package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {

    public static class ModeConstants{
        public static class Idle {
            public static final double IDLE_MODE_SHOULDER_ANGLE = 0;
            public static final double IDLE_MODE_TURRET_ANGLE = 0;
            public static final double IDLE_MODE_EXTENSIBLE_LENGTH = 0;
        }
    }

    public static class AutoConstants{
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double KP = 0;
        public static final double KI = 0;
        public static final double KD = 0;
        public static final double KF = 0;
        
        public static final double KS_VOLTS = 0.22;
        public static final double KV_VOLT_SECONDS_PER_METER = 1.98;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.2;
    }

    public static class DriveConstants{
        public static final int MASTER_LEFT_ID = 0;
        public static final int SLAVE_LEFT_ID = 1;
        public static final int MASTER_RIGHT_ID = 2;
        public static final int SLAVE_RIGHT_ID = 3;

        public static final double BALANCE_KP = 0;
        public static final double BALANCE_KI = 0;
        public static final double BALANCE_KD = 0;
        public static final double BALANCE_KF = 0;

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
        public static final double BY_HAND_DEGREES_OFFSET = 0.001;
        public static final double ANGLE_OF_MOVEMENT = 240;
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
        public static final double RANGE_OF_MOVEMENT = 0.4;
        // Calculations
        public static final double K_ARMTICK2DEGREE = 360 / SHOULDER_PPR;
        public static final double K_ARM_HEIGHT_M = 0.40;
        public static final double K_EXTENSIBLE_TICK2METER = 0;
    }

    public static class TurretConstants{
        public static double TURRET_UP_LIMIT = 720;
        public static double TURRET_DOWN_LIMIT = -720;

        public static final int TURRET_MASTER_ID = 8;
        public static final int TURRET_SLAVE_ID = 9;

        public static final double TURRET_KP = 0;
        public static final double TURRET_KI = 0;
        public static final double TURRET_KD = 0;
        public static final double TURRET_KF = 0;

        public static final double K_TURRET_TICKS2DEGREE = 360 / 46;
    }

    public static class IntakeConstants{
        public static final int INTAKE_MASTER_ID = 7;
        public static final double INTAKE_RPM_RATE = 400 / 20;
        public static final double INTAKE_CONFIDANCE_DOWN_LIMIT = 0.5;

        public static final double INTAKE_KP = 0;
        public static final double INTAKE_KI = 0;
        public static final double INTAKE_KD = 0;
        public static final double INTAKE_KF = 0;
    }

    public static class VisionConstants{
        public static String CAM_NAME = "";
        // Will be add transformations for camera position
    }
    
}
