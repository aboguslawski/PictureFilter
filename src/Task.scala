
import java.io.IOException
import java.util.Scanner


object Task extends App {

  val scanner: Scanner = new Scanner(System.in)
  println("set cutoff from 0 to 100 (80 is recommended): ")
  val cutoff: Int = scanner.nextInt

  try{
    new TestRunner("in", "out", cutoff).run()
  } catch {
    case e :IOException => e.printStackTrace()
  }

}
