/*
Copyright 2018 FIRST Tech Challenge Team 13710

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp

public class OmniWheelsOp extends LinearOpMode {
    // Configure Items from Control Hub 1:
    // Configure 1st Control Hub: The perfect one, just like Connor
    private Blinker Connor_Craig_IB_Student_Of_Last_Year;
    // Configure IMU from Control Hub 1:
    private Gyroscope Spinny_Connor_Craig;
    // Configure front motors from Control Hub 1:
    private DcMotor frontLeft;
    private DcMotor frontRight;
    // Configure back motors from Control Hub 1:
    private DcMotor backLeft;
    private DcMotor backRight;
    // Configure Items from Control Hub 2:
    // Configure 2nd Control Hub:
    private Blinker Yeshwant_The_Duck_Snr;
    // Configure Yeshwant's Body parts
    private DcMotor YeshwantArms;
    private DcMotor YeshwantFlag;
    private Servo YeshwantFingers;


    // Set up variables for omni driving:
    private double drive;
    private double turn;
    private double left;
    private double right;
    private double turnOm;
    // Setable variables for things:
    // Yeshwant's Arms:
    private double armpos = 250;
    private boolean armmoving = false;
    private double armmovmultiplier = 20;
    private boolean manualArmControlDisabled = false;
    // Yeshwant's Flag:
    private double flagpos = 250;
    private boolean flagmoving = false;
    private double flagmovmultiplier = 20;
    private boolean manualFlagControlDisabled = false;
    // Yeshwant's Fingers:
    private boolean manualFingerControlDisabled;
    @Override
    public void runOpMode() {
        // Configure Items from Control Hub 1:
        // Configure 1st Control Hub: The perfect one, just like Connor
        Connor_Craig_IB_Student_Of_Last_Year = hardwareMap.get(Blinker.class, "Connor Craig IB Student Of Last Year");
        // Configure IMU from Control Hub 1:
        Spinny_Connor_Craig = hardwareMap.get(Gyroscope.class, "imu");
        // Configure front motors from Control Hub 1:
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        // Configure back motors from Control Hub 1:
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        // Configure Items from Control Hub 2:
        // Configure 2nd Control Hub:
        Yeshwant_The_Duck_Snr = hardwareMap.get(Blinker.class,"Yeshwant The Duck Snr");
        // Configure Yeshwant's Body parts
        YeshwantArms = hardwareMap.get(DcMotor.class,"YeshwantArms");
        YeshwantFlag = hardwareMap.get(DcMotor.class,"YeshwantFlag");
        YeshwantFingers = hardwareMap.get(Servo.class,"YeshwantFingers");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Setting modes for non-position motors (i.e. ones where power is controlled,
        // rather than the target position.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Reset encoders for Position motors (Step 1)
        YeshwantArms.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YeshwantFlag.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set the zero power behaviour to brake - we'll change it for the smoothness of motion later (Step 2)!
        YeshwantFlag.setZeroPowerBehaviour(DcMotor.ZeroPowerBehaviour.BRAKE);
        YeshwantFlag.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Initialisation:
            telemetry.addData("Status", "Running");
            // Real Driving Code:
            // Set drive and turn from left stick
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.left_stick_x;
            // Calculate Overall Movement
            left  = drive + turn;
            right = drive - turn;
            frontLeft.setPower(left);
            telemetry.addData("Setting front left power: ", frontLeft.getPower());
            frontRight.setPower(-right);
            telemetry.addData("Setting front RIGHT power: ", frontRight.getPower());

            backLeft.setPower(drive);
            telemetry.addData("Setting back left power: ", backLeft.getPower());
            backRight.setPower(-drive);
            telemetry.addData("Setting back right power: ", backRight.getPower());
            //Omni Wheels Code
            turnOm  =  gamepad1.right_stick_x;
            if(turnOm<0) {
                // Move left out and right in
                frontRight.setPower(-turnOm);
                backRight.setPower(turnOm);
                frontLeft.setPower(-turnOm);
                backLeft.setPower(turnOm);
            }
            else {
                // Move left in and right out
                frontRight.setPower(turnOm);
                backRight.setPower(-turnOm);
                frontLeft.setPower(turnOm);
                backLeft.setPower(-turnOm);

            }
            // Limb Movement
            if (gamepad2.a) {
                manualFlagControlDisabled = true;
                // If the flag is not at the starting position, i.e. retracted,
                // move flag to starting position
                if (YeshwantFlag.getTargetPosition()!=0) {
                    telemetry.addData("Retracting Yeshwant Flag");
                    // Set target position (Step 3)
                    YeshwantFlag.setTargetPosition(flagpos);
                    // Set mode to run to position (Step 4)
                    YeshwantFlag.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    // Set power (Step 5)
                    YeshwantFlag.setPower(1);
                    // In case you are wondering, there is no need for the flagMoving
                    // variable here, because, the motor will not brake until the end of
                    // the movement.
                }
                else {
                    manualFlagControlDisabled = true;
                    telemetry.addData("Extending Yeshwant Flag");
                    // Set target position (Step 3)
                    YeshwantFlag.setTargetPosition(flagpos);
                    // Set mode to run to position (Step 4)
                    YeshwantFlag.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    // Set power (Step 5)
                    YeshwantFlag.setPower(1);
                    // In case you are wondering, there is no need for the flagMoving
                    // variable here, because, the motor will not brake until the end of
                    // the movement.
                }
            }
            // Toggle fingers
            if (gamepad2.x) {
                // Please note it will close when halfway open. This is not a rounding process.
                manualFingerControlDisabled = true;
                if (YeshwantFingers.getPosition!=-1) {
                    YeshwantFingers.setPosition(-1);
                }
                else {
                    YeshwantFingers.setPosition(1)
                }
            }
            if (gamepad2.y) {

            }
            if (gamepad2.dpad_up && manualFlagControlDisabled==false) {
                telemetry.addData("Moving Flag Up",YeshwantFlag.getTargetPosition());
                // Set target position (Step 3)
                YeshwantFlag.setTargetPosition(YeshwantFlag.getTargetPosition()+flagmovmultiplier);
                // Set mode to run to position (Step 4)
                YeshwantFlag.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // Set power (Step 5)
                YeshwantFlag.setPower(1);
                // Set flag moving variable to true, to make the arm coast, to prevent
                // the jerking motion.
                flagMoving = true;
            }
            if (gamepad2.right_trigger>=0) {
                manualArmControlDisabled = true;
                telemetry.addData("Extending Arm",YeshwantArms.getTargetPosition());
                YeshwantArms.setTargetPosition(armpos);
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                YeshwantArms.setPower(1);
            }
            if (gamepad2.left_trigger>=0) {
                manualArmControlDisabled = true;
                telemetry.addData("Retracting Arm",YeshwantArms.getTargetPosition());
                YeshwantArms.setTargetPosition(0);
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                YeshwantArms.setPower(1);
            }
            if (gamepad2.dpad_down && manualFlagControlDisabled == false) {
                telemetry.addData("Moving Flag Down",YeshwantFlag.getTargetPosition());
                // Set target position (Step 3)
                YeshwantFlag.setTargetPosition(YeshwantFlag.getTargetPosition()-flagmovmultiplier);
                // Set mode to run to position (Step 4)
                YeshwantFlag.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // Set power (Step 5)
                YeshwantFlag.setPower(1);
                // Set flag moving variable to true, to make the arm coast, to prevent
                // the jerking motion.
                flagMoving = true;
            }
            // Set the position of Yeshwant's fingers
            if (!manualFingerControlDisabled) {
                YeshwantFingers.setPosition(gamepad2.right_stick_x);
            }
            // Set the position of Yeshwant's arms
            if (!manualArmControlDisabled) {
                YeshwantArms.setTargetPosition(YeshwantArms.getTargetPosition() * +armmovmultiplier(gamepad2.left_stick_y));
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                YeshwantArms.setPower(1);
                armMoving = true;
            }

            if (armMoving) {
                YeshwantArms.setZeroPowerBehaviour(DcMotor.ZeroPowerBehaviour.COAST);
            }
            else {
                YeshwantArms.setZeroPowerBehaviour(DcMotor.ZeroPowerBehaviour.BRAKE);
            }
            if (flagMoving) {
                YeshwantFlag.setZeroPowerBehaviour(DcMotor.ZeroPowerBehaviour.COAST);
            }
            else {
                YeshwantFlag.setZeroPowerBehaviour(DcMotor.ZeroPowerBehaviour.BRAKE);
            }
            telemetry.update();
        }
    }
}