package backend.image_processing.preprocess.masking

import backend.ALL_SPACES
import backend.Colourspace
import backend.image_processing.Image
import backend.image_processing.preprocess.PreprocessingNode
import org.bytedeco.opencv.global.opencv_core.bitwise_and
import org.bytedeco.opencv.global.opencv_imgproc.*
import org.bytedeco.opencv.opencv_core.Mat

/**
 * The node used for thresholding
 * @param minThreshold The minimum threshold to use
 * @property minThreshold The minimum threshold to use
 * @param maxThreshold The maximum threshold to use
 * @property maxThreshold The minimum threshold to use
 * @param binarise Should the image be converted to a binary mask?
 * @property binarise Should the image be converted to a binary mask?
 */
class ThresholdingNode(var minThreshold: Double, var maxThreshold: Double = 255.0, var binarise: Boolean = true): PreprocessingNode() {
    override val name: String = "Thresholding"
    override val help: String = "Performs a black and white threshold on the image."

    override val inputColourspaces: List<Colourspace> = ALL_SPACES
    override val outputColourspace: Colourspace get() = if (binarise) inputColourspace else Colourspace.GRAYSCALE

    override fun process(img: Image): Image = img.clone().apply { threshold(minThreshold, maxThreshold, binarise) }
}