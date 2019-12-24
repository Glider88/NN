package example

import breeze.linalg.DenseMatrix
import breeze.numerics.exp

object Matrix {
  type Matrix = DenseMatrix[Double]
  type MatrixList = List[Matrix]
  def sigmoid(x: Matrix): Matrix = 1.0 / (exp(-x) + 1.0)
  def sigmoidDerivation(x: Matrix): Matrix = x *:* (1.0 - x)
  def fillByRandom(empty: Matrix): Matrix = empty * 2.0 - 1.0
  def createRandomMatrix(rows: Int, cols: Int): Matrix = fillByRandom(DenseMatrix.rand(rows, cols))

  def paired[A](list: List[A]): List[(A, A)] = list.init.zip(list.tail)
}
