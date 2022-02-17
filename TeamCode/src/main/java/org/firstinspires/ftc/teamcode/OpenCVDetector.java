package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Point;

public class OpenCVDetector extends OpenCvPipeline {
    // Creates the class instances of the telemetry object and the mat
    Telemetry telemetry;
    Mat mat = new Mat();
    // Set regions of interest (basically where we think the stones will be on the camera)
    static final Rect Left_ROI = new Rect(
        new Point(6, 7),
        new Point(7,8)
    );
    static final Rect Right_ROI = new Rect(
            new Point(9, 10),
            new Point(11,12)
    );
    // Copy the telementry into this instance of the object
    public OpenCVDetector(Telemetry t) {telemetry = t;}
    @Override
    // Begin the actual pipeline
    public Mat processFrame(Mat input) {
        // Set the colour and convert and move the copy the mat into this class
        Imgproc.cvtColor(input,mat,Imgproc.COLOR_RGB2HSV);
        // TODO: Edit these scalars
        Scalar LowColorBound = new Scalar(1,1,1);
        Scalar HighColorBound = new Scalar(1,1,1);
        Core.inRange(mat,LowColorBound,HighColorBound,mat);
        Mat leftDetect = mat.submat(Left_ROI);
        Mat rightDetect = mat.submat(Right_ROI);
        return null;
    }
}
