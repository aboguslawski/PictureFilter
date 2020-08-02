import java.awt.Color
import java.awt.image.BufferedImage

class ImageTest1(img: BufferedImage) extends Test(img){

  override def score(): Int = {
    (100 - (100 * luminance() / 255)).toInt
  }

  private def luminance(): Double = {
    @scala.annotation.tailrec
    def helper(x: Int, y: Int, acc: Double, pixels: Int): Double = (x,y) match{
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

object ImageTest1{
  def apply(img: BufferedImage):ImageTest1 = new ImageTest1(img)
}
