package example

import breeze.linalg.DenseMatrix
import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "Base NN functions: " should "work Forward Propagation" in {
    val x = DenseMatrix(
      (0.0, 0.0),
      (1.0, 0.0),
      (0.0, 1.0),
      (1.0, 1.0),
    )

    val s = List(
      DenseMatrix.ones[Double](2, 4),
      DenseMatrix.ones[Double](4, 1)
    )

    def id[A](x: A): A = x

    val result = Hello.forwardPropagation(x, s, id)
    val expected = List(
      x,
      DenseMatrix(
        (0.0, 0.0, 0.0, 0.0),
        (1.0, 1.0, 1.0, 1.0),
        (1.0, 1.0, 1.0, 1.0),
        (2.0, 2.0, 2.0, 2.0),
      ),
      DenseMatrix((0.0), (4.0), (4.0), (8.0))
    )

    result should be (expected)
  }
}
