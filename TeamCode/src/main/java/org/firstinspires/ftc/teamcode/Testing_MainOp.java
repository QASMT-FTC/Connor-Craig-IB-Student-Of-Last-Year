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
import com.qualcomm.robotcore.util.Range;

import java.text.DecimalFormat;

@TeleOp(name="[Testing] OmniwheelsOp", group="Iterative Opmode")

public class Testing_MainOp extends LinearOpMode {
    // Constant to change motor speed by x
    private final double ReduceByRight = 0.1;
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
    private DcMotor Extender;

    private Servo YeshwantFingers;
    // Set up variables for omni driving:
    private double drive;
    private double turn;
    private double left;
    private double right;
    private double turnOm;
    // Setable variables for things:
    // Yeshwant's Arms:
    private int armpos = 250;
    private boolean armmoving = false;
    private int armmovmultiplier = 1;
    private boolean manualArmControlDisabled = false;
    private double lastTime = 0;

    //Extender
    private double extenderPower = 0.2d;
    private boolean extenderMoving = false;

    // Yeshwant's Flag:
    private int flagpos = 4258;
    private boolean flagmoving = false;
    private int flagmovmultiplier = 20;
    private boolean manualFlagControlDisabled = false;


    // Claw:
    private boolean manualFingerControlDisabled;
    private Servo ServoRight;
    private Servo ServoLeft;
    private double target = ServoRight.MIN_POSITION;
    private boolean servoMoving = false;

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
        ServoLeft = hardwareMap.get(Servo.class, "ServoLeft");
        ServoRight = hardwareMap.get(Servo.class, "ServoRight");

        Extender = hardwareMap.get(DcMotor.class, "extender");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Setting modes for non-position motors (i.e. ones where power is controlled,
        // rather than the target position.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset encoders for Position motors (Step 1)
        YeshwantArms.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        YeshwantFlag.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set the zero power behaviour to brake - we'll change it for the smoothness of motion later (Step 2)!
        YeshwantFlag.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        YeshwantArms.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // set current time
        ElapsedTime runtime = new ElapsedTime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // Initialisation:
            telemetry.addData("Status", "Running");
            flagmoving = false;
            armmoving = false;
            extenderMoving = false;
            // Real Driving Code:
            // Set drive and turn from left stick
//            drive = -gamepad1.left_stick_y;
//            turn  =  gamepad1.left_stick_x;
//            left  = drive + turn;
//            right = drive - turn;
//            // Calculate Overall Movement
//            if (drive > 1) {
//                drive = drive / 2;
//            }
//            if (left > 1) {
//                left = left / 2;
//                right = right / 2;
//            }
//            if (right > 1) {
//                left = left / 2;
//                right = right / 2;
//            }
//            else {
//                //Do nothing
//            }
//            //Omni Wheels Code
//            turnOm  =  gamepad1.right_stick_x;
//            if(turnOm<0) {
//                // Move left out and right in
//                frontRight.setPower(-turnOm);
//                backRight.setPower(turnOm);
//                frontLeft.setPower(-turnOm);
//                backLeft.setPower(turnOm);
//            }
//            else if(turnOm>0) {
//                // Move left in and right out
//                frontRight.setPower(-turnOm);
//                backRight.setPower(turnOm);
//                frontLeft.setPower(-turnOm);
//                backLeft.setPower(turnOm);
//
//            }
//            else {
//                //Do Nothing
//            }
            mecanum(gamepad1);
            // Limb Movement
            if (gamepad2.a && YeshwantFlag.isBusy()==false) {
                manualFlagControlDisabled = true;
                // If the flag is not at the starting position, i.e. retracted,
                // move flag to starting position
                if (YeshwantFlag.getCurrentPosition()!=0) {
                    telemetry.addLine("Retracting Yeshwant Flag");
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
                    telemetry.addLine("Extending Yeshwant Flag");
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
            // Toggle Claw
            if (gamepad2.x) {
                // Please note it will close when halfway open. This is not a rounding process.
                servoMoving = true;
                manualFingerControlDisabled = true;
                if (ServoLeft.getPosition()==target) {
                    if (ServoLeft.getPosition()!=ServoLeft.MIN_POSITION) {
                        ServoLeft.setPosition(ServoLeft.MIN_POSITION);
                        target = ServoLeft.MIN_POSITION;
                    }
                    else {
                        ServoLeft.setPosition(ServoLeft.MAX_POSITION);
                        target = ServoLeft.MAX_POSITION;
                    }
                }
            }

            /*
            if (gamepad2.dpad_up && manualFlagControlDisabled==false) {

                // set delta time = current time - last time
                double deltaTime = runtime.milliseconds() - lastTime;

                //set the lastTime
                lastTime = runtime.milliseconds();


                if (deltaTime<=500&&YeshwantFlag.isBusy()==false) {
                    // Set flag moving variable to true, to make the arm coast, to prevent
                    // the jerking motion.
                    flagmoving = true;
                    YeshwantFlag.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    YeshwantFlag.setPower(1);
                }
            }
            if (gamepad2.dpad_up && manualFlagControlDisabled && gamepad2.left_bumper) {
                // Emergency Override Code
                telemetry.addData("ALERT! Emergency Override of Flag!",YeshwantFlag.getTargetPosition());
                // Set target position (Step 3)
                YeshwantFlag.setTargetPosition(YeshwantFlag.getCurrentPosition()+flagmovmultiplier);
                // Set mode to run to position (Step 4)
                YeshwantFlag.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // Set power (Step 5)
                YeshwantFlag.setPower(1);
                // Set flag moving variable to true, to make the arm coast, to prevent
                // the jerking motion.
                flagmoving = true;
                // Give control of flag back to user
                manualFlagControlDisabled = false;
            }
            */

            if (gamepad2.right_trigger>0) {
                manualArmControlDisabled = true;
                telemetry.addData("Extending Arm",YeshwantArms.getTargetPosition());
                YeshwantArms.setTargetPosition(armpos);
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                YeshwantArms.setPower(1);
            }
            if (gamepad2.left_trigger>0) {
                manualArmControlDisabled = true;
                telemetry.addData("Retracting Arm",YeshwantArms.getTargetPosition());
                YeshwantArms.setTargetPosition(0);
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                YeshwantArms.setPower(1);
            }
            /*
            if (gamepad2.dpad_down && manualFlagControlDisabled == false) {

                // set delta time = current time - last time
                double deltaTime = runtime.milliseconds() - lastTime;

                //set the lastTime
                lastTime = runtime.milliseconds();


                if (deltaTime<=500&&YeshwantFlag.isBusy()==false) {
                    // Set flag moving variable to true, to make the arm coast, to prevent
                    // the jerking motion.
                    flagmoving = true;
                    YeshwantFlag.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    YeshwantFlag.setPower(0.5);
                }
            }
            */

            // Set the position of Yeshwant's fingers
            if (!manualFingerControlDisabled) {
                YeshwantFingers.setPosition(gamepad2.right_stick_x);
            }
            telemetry.addData("PowerData",armmovmultiplier*gamepad2.left_stick_y);
            // Set the position of Yeshwant's arms
            if (!manualArmControlDisabled && gamepad2.left_stick_y!=0) {
                telemetry.addLine("Connor");
                YeshwantArms.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                YeshwantArms.setPower(armmovmultiplier*-gamepad2.left_stick_y);
                armmoving = true;
            }

            //Extender movement
            if (gamepad2.dpad_up){
                Extender.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
                //CHANGE THIS POWER
                Extender.setPower(extenderPower);
                extenderMoving = true;

            } else if (!gamepad2.dpad_up && !gamepad2.dpad_down){
                Extender.setPower(0);
                extenderMoving = false;
            }

            if (gamepad2.dpad_down){
                Extender.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
                //CHANGE THIS POWER
                Extender.setPower(-extenderPower);
                extenderMoving = true;
            } else if (!gamepad2.dpad_up && !gamepad2.dpad_down){
                Extender.setPower(0);
                extenderMoving = false;
            }


            // Allow Yeshwant Arm override
            if (gamepad2.left_stick_y>=0 && !manualFlagControlDisabled && gamepad2.left_bumper) {
                // Emergency Override Code
                telemetry.addData("ALERT! Emergency Override of Arm!",YeshwantArms.getTargetPosition());
                // Set target position (Step 3)
                YeshwantArms.setTargetPosition(YeshwantArms.getCurrentPosition()+flagmovmultiplier);
                // Set mode to run to position (Step 4)
                YeshwantArms.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // Set power (Step 5)
                YeshwantArms.setPower(1);
                // Set arm moving variable to true, to make the arm coast, to prevent
                // the jerking motion.
                armmoving = true;
                // Give control of flag back to user
                manualArmControlDisabled = false;
            }

            //Extender for arm

            if (YeshwantArms.isBusy()) {
                armmoving = true;
            }
            if (YeshwantFlag.isBusy()) {
                flagmoving = true;
            }

            if (armmoving) {
                armmoving = false;
            }
            else {
                YeshwantArms.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                YeshwantArms.setPower(0);
            }
            if (flagmoving) {
                YeshwantFlag.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                flagmoving = false;
            }
            else {
                YeshwantFlag.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                YeshwantFlag.setPower(0);
            }
            telemetry.addData("Arm Moving",armmoving);
            telemetry.addData("Extender Moving", extenderMoving);
            //telemetry.addData("Flag Moving",flagmoving);
            telemetry.addData("Flag Position",YeshwantFlag.getCurrentPosition());
            telemetry.addData("Arm Disabled",manualArmControlDisabled);
            frontLeft.setPower(-left);
            telemetry.addData("Setting front left power: ", frontLeft.getPower());
            frontRight.setPower(right);
            telemetry.addData("Setting front RIGHT power: ", frontRight.getPower());

            backLeft.setPower(-drive);
            telemetry.addData("Setting back left power: ", backLeft.getPower());
            backRight.setPower(drive);
            telemetry.addData("Setting back right power: ", backRight.getPower());

            telemetry.addData("servo Moving: ", servoMoving);

            telemetry.update();
        }

    }
    public void mecanum(Gamepad gamepad){

        double r = Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x) - Math.PI / 4;
        double rightX = -gamepad.right_stick_x;
        double[] v = new double[4];
        v[0] = r * Math.cos(robotAngle) + rightX;
        v[1] = r * Math.sin(robotAngle) - rightX;
        v[2] = r * Math.sin(robotAngle) + rightX;
        v[3] = r * Math.cos(robotAngle) - rightX;
        double max = 0;
        for (int i = 0; i < 4; i++) {
            if(Math.abs(v[i]) > max) max = Math.abs(v[i]);
        }
        max = Range.clip(max,-1,1);
        DecimalFormat df = new DecimalFormat("0.00");
        telemetry.addData("Status", "max: " + max);
        for (int i = 0; i < 4; i++) {
            double e = v[i] / max;
            telemetry.addData("Status", df.format(e) + " " + df.format(v[i]) + " " + df.format(max));
            v[i] = e;
        }
        frontLeft.setPower(v[0]);
        frontRight.setPower(v[1]);
        backLeft.setPower(v[2]);
        backRight.setPower(v[3]);
        telemetry.addData("Status", v[0]);
        telemetry.addData("Status", v[1]);
        telemetry.addData("Status", v[2]);
        telemetry.addData("Status", v[3]);
    }
}