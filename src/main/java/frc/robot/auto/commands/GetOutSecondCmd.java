// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class GetOutSecondCmd extends CommandBase {
  Intake intake;

  double startTime;
  double second;

  /** Creates a new GetInSecondCmd. */
  public GetOutSecondCmd(double second) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = Intake.getInstance();
    this.second = second;
    startTime = Timer.getFPGATimestamp();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.manualIn();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return startTime + second < Timer.getFPGATimestamp();
  }
}
