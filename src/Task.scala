
import java.io.IOException
import java.util.Scanner


object Task extends App {

  val scanner: Scanner = new Scanner(System.in)
  println("set cutoff from 0 to 100 (80 is recommended): ")
  val cutoff: Int = scanner.nextInt

  try{
    var th = new Thread(new TestRunner("in", "out", cutoff))
    th.start()
  } catch {
    case e :IOException => e.printStackTrace()
  }

}
