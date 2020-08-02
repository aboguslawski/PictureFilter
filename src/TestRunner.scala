import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files

import javax.imageio.ImageIO

/* takes all pictures from given directory,
   puts them through the test, renames them
   and saves renamed copies to the output directory

 */

class TestRunner(val input: String, val output: String, val cutoff: Int) extends Runnable {

  private val inputDir: File = new File(input)

  // only files with specified extensions will be added to the list
  private val images: List[File] = inputDir.listFiles.filter(x =>
    x.getName.endsWith(".jpg") ||
      x.getName.endsWith(".png") ||
      x.getName.endsWith(".jpeg")
  ).toList

  // Runnable method
  override def run(): Unit = {
    println(Thread.currentThread().getName +
      " is running.")
    images.foreach(process)
  }

  // method calculates a score of given file and saves renamed copy to the output directory
  private def process(file: File): Unit = {
    val img: BufferedImage = ImageIO.read(file)
    val score = ImageTest1b(img).score()

    println(renameFile(file, score).getName)

    copyFile(file, renameFile(file, score))
  }

  private def copyFile(source: File, destination: File): Unit = {
    Files.copy(source.toPath, destination.toPath)
  }

  private def renameFile(in: File, score: Int): File = {
    // this will be appended to the name of a file
    val toBeAppended: String = {
      if (score >= cutoff) "_dark_" + score
      else "_bright_" + score
    }

    // separating extension from actual name of a file
    val i: Int = in.getName.lastIndexOf(".")
    val prefix = in.getName.substring(0, i)
    val suffix = in.getName.substring(i)

    // returning renamed file
    new File(output + "/" + prefix + toBeAppended + suffix)
  }

}