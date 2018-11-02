package example

import breeze.numerics._
import breeze.linalg._
import breeze.stats.mean

object Hello extends App {
  type Matrix = DenseMatrix[Double]
  type MatrixList = List[Matrix]
  def sigmoid(x: Matrix): Matrix = 1.0 / (exp(-x) + 1.0)
  def sigmoidDerivation(x: Matrix): Matrix = x :* (1.0 - x)
  def fillByRandom(empty: Matrix): Matrix = empty * 2.0 - 1.0
  def createRandomMatrix(rows: Int, cols: Int): Matrix = fillByRandom(DenseMatrix.rand(rows, cols))

  val X = DenseMatrix(
    (0.0, 0.0, 1.0),
    (0.0, 1.0, 1.0),
    (1.0, 0.0, 1.0),
    (1.0, 1.0, 1.0)
  )

  val y = DenseMatrix(0.0, 1.0, 1.0, 0.0)

//  L0     L1     L2
//         ◯
//  ◯      ◯
//  ◯  S0  ◯  S1  ◯
//  ◯      ◯
//
//  X             y
//
// L2_error = y - L2
// L2_norm  = L2_error * sigmoid'(L2)
//
// L1_error = L2_norm * S1
// L1_norm  = L1_error * sigmoid'(L1)
//
// S1 = S1 + L1 * L2_norm
// S0 = S0 + L0 * L1_norm



//     L0     L1     L2     L3     L4     L5
//
//            ◯
//     ◯      ◯      ◯      ◯      ◯
//     ◯  S0  ◯  S1  ◯  S2  ◯  S3  ◯  S4  ◯
//     ◯      ◯      ◯      ◯      ◯
//            ◯      ◯
//
//     X                                  y
//
//
//    L5_error = y - L5
//    L5_norm  = L5_error * sigmoid'(L5)
//
//    L4_error = L5_norm * S4
//    L4_norm  = L4_error * sigmoid'(L4)
//
//    L3_error = L4_norm * S3
//    L3_norm  = L3_error * sigmoid'(L3)
//
//    L2_error = L3_norm * S2
//    L2_norm  = L2_error * sigmoid'(L2)
//
//    L1_error = L2_norm * S1
//    L1_norm  = L1_error * sigmoid'(L1)
//
//
//    L0  | L1       | L2       | L3       | L4       | L5
//        | L1_error | L2_error | L3_error | L4_error | L5_error
//        | L1_norm  | L2_norm  | L3_norm  | L4_norm  | L5_norm
//    S0  | S1       | S2       | S3       | S4       |
//
//
//    S4 = S4 + L4 * L5_norm
//    S3 = S3 + L3 * L4_norm
//    S2 = S2 + L2 * L3_norm
//    S1 = S1 + L1 * L2_norm
//    S0 = S0 + L0 * L1_norm
//
//
//


  // val E = 60000
  // val R = 4
  var S: MatrixList = List(3, 4, 1).init.zip(List(3, 4, 1).tail).map(l => createRandomMatrix(l._1, l._2))
  var L: MatrixList = List()
  var D: MatrixList = List()


  var syn0 = createRandomMatrix(3, 4)
  var syn1 = createRandomMatrix(4, 1)

  // var i = 1
  // while (i < E) {

    L = S.foldLeft(List(X))((l: MatrixList, s: Matrix) => l :+ sigmoid(l.last * s))




    // if (i % 10000 == 0) {
    //   println("Error:" + mean(abs(l2_error)))
    // }



    val l0 = X
    val l1 = sigmoid(l0 * syn0)
    val l2 = sigmoid(l1 * syn1)


    val l2_error = y - l2
    val l2_delta = l2_error :* sigmoidDerivation(l2)

    val l1_error = l2_delta * syn1.t
    val l1_delta = l1_error :* sigmoidDerivation(l1)

    syn1 += l1.t * l2_delta
    syn0 += l0.t * l1_delta



    // i += 1
  // }

  println("-------------------------------------")
  println(syn0)
  println("-------------------------------------")
  println(syn1)
  println("-------------------------------------")
}
