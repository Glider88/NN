package example

import breeze.linalg.DenseMatrix
import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "Base NN functions: " should "work Direct Distribution" in {
    val x = DenseMatrix(
      (0.0, 0.0),
      (1.0, 0.0),
      (0.0, 1.0),
      (1.0, 1.0),
    )

    val s = List(
      DenseMatrix.ones[Double](2, 4),
      DenseMatrix.ones[Double](1, 4)
    )

    def id[A](x: A): A = x

    val result = Hello.directDistribution(x, s, id)

    result.toList should be (List((0.0), (1.0), (1.0), (2.0)))
  }
}
