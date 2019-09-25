package webcam.com.webcam;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Test {

	public static final String FILENAME = "output.mp4";

	public static void main(String[] args) throws Exception {
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		grabber.start();
		Frame grabbedImage = grabber.grab();

		CanvasFrame canvasFrame = new CanvasFrame("Cam");
		// canvasFrame.setCanvasSize(grabbedImage.width(), (grabbedImage).height());

		System.out.println("framerate = " + grabber.getFrameRate());
		grabber.setFrameRate(grabber.getFrameRate());
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(FILENAME, grabber.getImageWidth(),
				grabber.getImageHeight());

		recorder.setVideoCodec(13);
		recorder.setFormat("mp4");
		// recorder.setPixelFormat(avutil.AV_AFD_16_9);
		recorder.setFrameRate(1);
		recorder.setVideoBitrate(10 * 1024 * 1024);

		recorder.start();
		while (canvasFrame.isVisible() && (grabbedImage = grabber.grab()) != null) {
			canvasFrame.showImage(grabbedImage);
			recorder.record(grabbedImage);
		}
		recorder.stop();
		grabber.stop();
		canvasFrame.dispose();
	}
}