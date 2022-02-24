package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.*;

@TeleOp(name="ColorGetter", group="Iterative OpMode")
public class ColorGetter extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id",hardwareMap.appContext.getPackageName());
        WebcamName name = hardwareMap.get(WebcamName.class,"DuckEyes");
        OpenCvWebcam Camera = OpenCvCameraFactory.getInstance().createWebcam(name,cameraMonitorViewId);
        System.out.println("okay3");
        Camera.openCameraDevice();
        Camera.startStreaming(1280,720);
        while (opModeIsActive()) {
        }
        }
    }
