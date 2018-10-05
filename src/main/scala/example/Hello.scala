package example

import breeze.numerics._
import breeze.linalg._
import breeze.stats.mean

object Hello extends App {
  type Matrix = DenseMatrix[Double]
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

  val N = "Neurons"
  val R = "Run"

  val y = DenseMatrix(0.0, 1.0, 1.0, 0.0)
  var syn0 = createRandomMatrix(3, 4)
  var syn1 = createRandomMatrix(4, 1)

  var i = 1
  while (i < 60000) {
    val l0 = X

    val l1 = sigmoid(l0 * syn0)
    val l2 = sigmoid(l1 * syn1)

    val l2_error = y - l2
    if (i % 10000 == 0) {
      println("Error:" + mean(abs(l2_error)))
    }

    val l2_delta = l2_error :* sigmoidDerivation(l2)

    val l1_error = l2_delta * syn1.t
    val l1_delta = l1_error :* sigmoidDerivation(l1)

    syn1 += l1.t * l2_delta
    syn0 += l0.t * l1_delta

    i += 1
  }

  println("-------------------------------------")
  println(syn0)
  println("-------------------------------------")
  println(syn1)
  println("-------------------------------------")
}
