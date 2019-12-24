package example

import breeze.linalg.DenseMatrix
import example.Matrix.Matrix
import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "Base NN functions: " should "work Forward Propagation" in {
    //     L0     L1     L2
    //
    //     ◯      ◯
    //     ◯  S0  ◯  S1  ◯
    //            ◯
    //
    //     X             y

    val x: Matrix = DenseMatrix(
      (0.0, 0.0),
      (1.0, 0.0),
      (0.0, 1.0),
      (1.0, 1.0),
    )

    val s0: Matrix = DenseMatrix(
      (2.0, 2.0, 2.0),
      (2.0, 2.0, 2.0),
    )

    val s1: Matrix = DenseMatrix(
      (2.0),
      (2.0),
      (2.0),
    )

    def halve(x: Matrix): Matrix = x / 2.0

    /*
      l0 = X
      l1 = sigmoid((l0 * s0)) = (x * s0) / 2

       (4x2)    (2x3)         (4x3)
        0 0                   0 0 0              0 0 0
        1 0  *  2 2 2  / 2 =  2 2 2   /  2 =     1 1 1
        0 1     2 2 2         2 2 2              1 1 1
        1 1                   4 4 4              2 2 2


      l2 = sigmoid((l1 * s1)) = (l1 * s1) / 2

      (4x3)   (3x1)       (4x1)
      0 0 0     2           0          0
      1 1 1  *  2  / 2  =   6  / 2 =   3
      1 1 1     2           6          3
      2 2 2                12          6
    */

    val result = NeuralNetwork.forwardPropagation(x, List(s0, s1), halve)
    val expected = List(
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
      DenseMatrix(
        (0.0),
        (3.0),
        (3.0),
        (6.0)
      )
    )

    result should be (expected)
  }

  it should("work Back Propagation Deltas") in {
    //     L0     L1     L2
    //
    //     ◯      ◯
    //     ◯  S0  ◯  S1  ◯
    //            ◯
    //
    //     X             y

    val l0: Matrix = DenseMatrix(
      (0.0, 0.0),
      (1.0, 0.0),
      (0.0, 1.0),
      (1.0, 1.0),
    )

    val l1: Matrix = DenseMatrix(
      (0.0, 0.0, 0.0),
      (1.0, 1.0, 1.0),
      (1.0, 1.0, 1.0),
      (2.0, 2.0, 2.0),
    )

    val l2: Matrix = DenseMatrix(
      (0.0),
      (3.0),
      (3.0),
      (6.0)
    )

    val s0: Matrix = DenseMatrix(
      (1.0, 1.0, 1.0),
      (1.0, 1.0, 1.0),
    )

    val s1: Matrix = DenseMatrix(
      (1.0),
      (1.0),
      (1.0),
    )

    val y: Matrix = DenseMatrix(
      (0.0),
      (0.0),
      (0.0),
      (0.0),
    )

    /*
      l2_error = y - l2

      0     0      0
      0  -  3  =  -3
      0     3     -3
      0     6     -6


      l2_delta  = l2_error *:* sigmoid'(l2) = l2_error *:* l2

       0       0      0
      -3  *:*  3  =  -9
      -3       3     -9
      -6       6    -36


      l1_error = l2_delta * s1.T

      (4x1)   (1x3)        (4x3)
        0                0   0   0
       -9  *  1 1 1  =  -9  -9  -9
       -9               -9  -9  -9
      -36              -36 -36 -36


      l1_delta  = l1_error *:* sigmoid'(l1)

        0   0   0       0 0 0      0    0    0
       -9  -9  -9  *:*  1 1 1  =  -9   -9   -9
       -9  -9  -9       1 1 1     -9   -9   -9
      -36 -36 -36       2 2 2    -72  -72  -72


      l0_error = l1_delta * s0.T

           (4x3)       (3x2)         (4x2)
        0    0    0     1 1         0     0
       -9   -9   -9  *  1 1    =  -27   -27
       -9   -9   -9     1 1       -27   -27
      -72  -72  -72              -216  -216


      l0_delta  = l0_error *:* sigmoid'(l0)

         0     0       0 0        0     0
       -27   -27  *:*  1 0  =   -27     0
       -27   -27       0 1        0   -27
      -216  -216       1 1     -216  -216
    */

    def id(x: Matrix): Matrix = x

    val result = NeuralNetwork.backPropagationDelta(List(l0, l1, l2), y, List(s0, s1), id)

    val expected = List(
      DenseMatrix(
        (   0.0,    0.0),
        ( -27.0,    0.0),
        (   0.0,  -27.0),
        (-216.0, -216.0),
      ),
      DenseMatrix(
        (  0.0,   0.0,   0.0),
        ( -9.0,  -9.0,  -9.0),
        ( -9.0,  -9.0,  -9.0),
        (-72.0, -72.0, -72.0),
      ),
      DenseMatrix(
        (  0.0),
        ( -9.0),
        ( -9.0),
        (-36.0),
      ),
    )

    result should be (expected)
  }

  it should("work updating syn") in {
    val l0: Matrix = DenseMatrix(
      (1.0, 0.0),
    )

    val l1: Matrix = DenseMatrix(
      (1.0, 0.0, 1.0),
    )

    val l2: Matrix = DenseMatrix(
      (1.0),
    )

    val s0: Matrix = DenseMatrix(
      (1.0, 1.0, 1.0),
      (1.0, 1.0, 1.0),
    )

    val s1: Matrix = DenseMatrix(
      (1.0),
      (1.0),
      (1.0),
    )

    val l0_delta: Matrix = DenseMatrix(
      (2.0, 1.0),
    )

    val l1_delta: Matrix = DenseMatrix(
      (2.0, 2.0, 2.0),
    )

    val l2_delta: Matrix = DenseMatrix(
      (2.0),
    )

    //    s1 = s1 + l1.T *:* l2_delta
    //    s0 = s0 + l0.T *:* l1_delta

    val result = NeuralNetwork.updateSyn(List(s0, s1), List(l0, l1, l2), List(l0_delta, l1_delta, l2_delta))

    val expected = List(
      DenseMatrix(
        (3.0, 3.0, 3.0),
        (1.0, 1.0, 1.0),
      ),
      DenseMatrix(
        (3.0),
        (1.0),
        (3.0),
      )
    )

    result should be (expected)
  }
}
