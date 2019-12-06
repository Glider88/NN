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
      DenseMatrix.ones[Double](2, 3),
      DenseMatrix.ones[Double](3, 1)
    )

    def id[A](x: A): A = x

    val result = Hello.forwardPropagation(x, s, id)
    val expected = List(
      x,
      DenseMatrix(
        (0.0, 0.0, 0.0),
        (1.0, 1.0, 1.0),
        (1.0, 1.0, 1.0),
        (2.0, 2.0, 2.0),
      ),
      DenseMatrix((0.0), (3.0), (3.0), (6.0))
    )

    result should be (expected)
  }

  it should("work Back Propagation Deltas") in {
    val l = List(
      DenseMatrix(
        (0.0, 0.0),
        (1.0, 0.0),
        (0.0, 1.0),
        (1.0, 1.0),
      ),
      DenseMatrix(
        (0.0, 0.0, 0.0),
        (1.0, 1.0, 1.0),
        (1.0, 1.0, 1.0),
        (2.0, 2.0, 2.0),
      ),
      DenseMatrix((0.0), (3.0), (3.0), (6.0))
    )

    val s = List(
      DenseMatrix.ones[Double](2, 3),
      DenseMatrix.ones[Double](3, 1)
    )

    val y = DenseMatrix.zeros[Double](4, 1)

    def id[A](x: A): A = x

    val result = Hello.backPropagationDelta(l, y, s, id)

    val expected = List(
      DenseMatrix(
        (  0.0),
        ( -9.0),
        ( -9.0),
        (-36.0),
      ),
      DenseMatrix(
        (  0.0,   0.0,   0.0),
        ( -9.0,  -9.0,  -9.0),
        ( -9.0,  -9.0,  -9.0),
        (-72.0, -72.0, -72.0),
      ),
      DenseMatrix(
        (   0.0,    0.0),
        ( -27.0,    0.0),
        (   0.0,  -27.0),
        (-216.0, -216.0),
      ),
    )

    result should be (expected)
  }
}
