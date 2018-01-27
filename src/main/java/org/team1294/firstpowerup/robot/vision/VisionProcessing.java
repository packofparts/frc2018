package org.team1294.firstpowerup.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VisionProcessing {

  private final SwitchTargetPipeline switchTargetPipeline;

  public VisionProcessing() {
    switchTargetPipeline = new SwitchTargetPipeline();
  }

  public VisionProcessingResult processSwitchTargetFrame(Mat frame) {
    // run the grip pipeline
    switchTargetPipeline.process(frame);

    // get the bounding rect for each contour
    List<Rect> rects = switchTargetPipeline
        .filterContoursOutput()
        .stream()
        .map(contour -> Imgproc.boundingRect(contour))
        .collect(Collectors.toList());

    // get every combination of pairs
    Stream<PairOfRect> pairs = rects.stream()
        .flatMap(i -> rects.stream().filter(j -> !i.equals(j)).map(j -> new PairOfRect(i, j)));

    // get the best (lowest) scoring pair
    Optional<PairOfRect> bestPair = pairs.min((a, b) -> scoreSwitchContourPair(b).compareTo(scoreSwitchContourPair(a)));


    boolean gearTargetAcquired = bestPair.isPresent();
    double degreesOffCenter = 0;
    double pixelsOffCenter = 0;
    if (bestPair.isPresent()) {
      PairOfRect pair = bestPair.get();
      // calculate how many degrees off center
      double halfWidth = frame.width() / 2;
      pixelsOffCenter = pair.centerX() - halfWidth;
      double percentOffCenter = pixelsOffCenter  / halfWidth;
      degreesOffCenter = percentOffCenter * 30;
    }

    VisionProcessingResult result = new VisionProcessingResult();
    result.setTargetAcquired(gearTargetAcquired);
    result.setDegreesOffCenter(degreesOffCenter);
    return result;
  }

  public Double scoreSwitchContourPair(PairOfRect pair) {
    double score = 0;

    // difference in height
    score += Math.abs(pair.getA().height - pair.getB().height);

    // difference in width
    score += Math.abs(pair.getA().width - pair.getB().width);

    // difference in center Y
    double rect1CenterY = pair.getA().y + pair.getA().height / 2;
    double rect2CenterY = pair.getB().y + pair.getB().height / 2;
    score += Math.abs(rect1CenterY - rect2CenterY);

    // difference between combined rect width:height ratio and the ideal of 2.05
    // todo figure out ideal ratio for this years vision target
    double combinedWidth;
    double combinedHeight;
    if (pair.getA().x < pair.getB().x) {
      combinedWidth = pair.getA().x + pair.getB().x + pair.getB().width;
    } else {
      combinedWidth = pair.getB().x + pair.getA().x + pair.getA().width;
    }
    if (pair.getA().y < pair.getB().y) {
      combinedHeight = pair.getA().y + pair.getB().y + pair.getB().height;
    } else {
      combinedHeight = pair.getB().y + pair.getA().y + pair.getA().height;
    }

    double combinedRatio = combinedWidth / combinedHeight;
    score += Math.abs(combinedRatio - 2.05);

    return score;
  }
}
