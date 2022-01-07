package application.backend.postprocess.fitting

import application.backend.Colourspace
import application.backend.postprocess.PostprocessingNode
import org.bytedeco.opencv.global.opencv_imgproc.CV_HOUGH_GRADIENT
import org.bytedeco.opencv.global.opencv_imgproc.HoughCircles
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector

// Param 1 is for edge detection
// Param 2 is for centre detection. Lower for lower standards of what is a circle.
data class CircleFitting(val minDist: Double = 20.0, val param1: Double = 200.0, val param2: Double = 100.0,
                         val minRadius: Int = 0, val maxRadius: Int = 0) : PostprocessingNode() {
    override val help: String = "Finds circle in the pictures and returns the biggest one."
    override val entries: List<String> = listOf("centre_x", "centre_y", "radius")

    override val inputColourspaces: List<Colourspace> = listOf(Colourspace.GRAYSCALE)
    override var inputColourspace: Colourspace = Colourspace.GRAYSCALE

    override fun process(img: Mat): List<Any> {
        val circles = Vec3fVector()
        HoughCircles(img, circles, CV_HOUGH_GRADIENT, 1.0, minDist, param1, param2, minRadius, maxRadius)

        val circlesList = arrayListOf<List<Float>>()
        for (i in 0 until circles.size()) {
            val circle = circles[i]
            circlesList.add(listOf(circle[0], circle[1], circle[2]))
        }

        return circlesList.maxByOrNull { it[2] } ?: listOf(-1, -1, -1)
    }
}