import java.io.IOException
import java.util.{InputMismatchException, Scanner}

/* test class

   all files should be removed from the output directory
   before running the program
 */
object Task extends App {

  val cutoff: Int = readCutoff()

  try {
    val th = new Thread(new TestRunner("in", "out", cutoff))
    th.start()
  } catch {
    case e: IOException => e.printStackTrace()
  }

  // reading user's input
  @scala.annotation.tailrec
  def readCutoff(min: Int = 0, max: Int = 100): Int = {
    val scanner: Scanner = new Scanner(System.in)
    println("set cutoff from 0 to 100 (80 is recommended): ")

    try {
      val res = scanner.nextInt

      if (res >= 0 && res <= 100)
        res
      else
        throw new InputMismatchException

    } catch {
      case e: InputMismatchException =>
        println("Only number values from 0 to 100 inclusively are allowed for cutoff")
        readCutoff()
    }
  }

}
