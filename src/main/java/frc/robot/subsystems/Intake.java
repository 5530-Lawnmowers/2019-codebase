/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public static int minArmHeight = 0;
  public static int maxArmHeight = 0;

  public static WPI_TalonSRX intakeTRSX1 = new WPI_TalonSRX(RobotMap.I1);
  public static WPI_TalonSRX intakeTRSX2 = new WPI_TalonSRX(RobotMap.I2);

  public static DigitalInput hatchSwitch = new DigitalInput(RobotMap.HS);
  public static DigitalInput ballSwitch = new DigitalInput(RobotMap.BS);


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
