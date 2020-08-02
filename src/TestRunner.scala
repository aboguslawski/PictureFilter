import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files

import javax.imageio.ImageIO

class TestRunner(val input: String, val output: String, val cutoff: Int) {

  private val inputDir: File = new File(input)
  private val images: List[File] = inputDir.listFiles.filter(x =>
    x.getName.endsWith(".jpg") ||
      x.getName.endsWith(".png") ||
      x.getName.endsWith(".jpeg")
  ).toList

  def run(): Unit = {
    images.foreach(process)
  }

  private def process(file: File): Unit = {
    val img: BufferedImage = ImageIO.read(file)
    val score = ImageTest1(img).score()

    println(renameFile(file, score).getName)

    copyFile(file, renameFile(file, score))
  }

  private def copyFile(source: File, destination: File): Unit = {
    Files.copy(source.toPath, destination.toPath)
  }

  private def renameFile(in: File, score: Int): File = {
    val toBeAppended: String = {
      if (score >= cutoff) "_dark_" + score
      else "_bright_" + score
    }

    val i: Int = in.getName.lastIndexOf(".")
    val prefix = in.getName.substring(0, i)
    val suffix = in.getName.substring(i)

    new File(output + "/" + prefix + toBeAppended + suffix)
  }

}