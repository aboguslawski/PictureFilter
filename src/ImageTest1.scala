import java.awt.Color
import java.awt.image.BufferedImage

/*
  first test of picture's brightness
*/
class ImageTest1(img: BufferedImage) extends Test(img) {

  // returns a score in scale 0 - 100 where:
  // 0 - perfectly white picture
  // 100 - perfectly black picture
  override def score(): Int = {
    (100 - (100 * luminance() / 255)).toInt
  }

  // processes every fifth pixel from given picture
  // sums luminance of RGB values from every processed pixel
  // and divides them by number of processed pixels
  // returns 'average' luminance of a picture
  private def luminance(): Double = {

    @scala.annotation.tailrec
    def helper(x: Int, y: Int, acc: Double, pixels: Int): Double = (x, y) match {
      case (w, h) if (w >= img.getWidth - 5 && h >= img.getHeight - 5) =>
        acc / pixels
      case (w, h) if (w >= img.getWidth - 5) =>
        helper(0, h + 5, acc, pixels)
      case (w, h) =>
        val color: Color = new Color(img.getRGB(w, h))
        val lum: Double = 0.299 * color.getRed + 0.587 * color.getGreen + 0.114 * color.getRed
        helper(w + 5, h, acc + lum, pixels + 1)
    }

    helper(0, 0, 0, 0)
  }
}

/* ImageTest1a

 entire image is processed and every part is equally valuable to a result
 photography with dark face and bright background might get "better" score
 than photography with bright face and dark background

 */
object ImageTest1a {
  def apply(img: BufferedImage): ImageTest1 = new ImageTest1(img)
}

/* ImageTest1b

 it's very likely that face is put in the middle of taken picture
 so only central part of picture is taken into consideration

 */
object ImageTest1b {
  def apply(img: BufferedImage): ImageTest1 = new ImageTest1(crop(img))

  def crop(img: BufferedImage): BufferedImage = {
    val centerX: Int = img.getWidth() / 2
    val centerY: Int = img.getHeight() / 2
    val radius: Int = (Math.min(img.getHeight / 2, img.getWidth / 2) * 0.8).toInt

    img.getSubimage(centerX - radius, centerY - radius, 2 * radius, 2 * radius)
  }
}
