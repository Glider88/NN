package example

import breeze.linalg._
import example.Matrix.MatrixList
import example.Matrix.Matrix
import example.Matrix.createRandomMatrix
import example.Matrix.paired
import example.Matrix.sigmoid
import example.NeuralNetwork.run
import example.NeuralNetwork.forwardPropagation

object Perceptron {
  case class Config(layers: List[Int], iteration: Int)
  case class TrainedModel(s_final: MatrixList)

  def configure(layers: List[Int], iteration: Int): Config = {
    Config(layers, iteration)
  }

  def fit(x: List[List[Double]], y: List[List[Double]], config: Config): TrainedModel = {
    val s_init: MatrixList = paired(config.layers).map(l => createRandomMatrix(l._1, l._2))
    val x_matrix: Matrix = DenseMatrix(x.map(_.toArray):_*)
    val y_matrix: Matrix = DenseMatrix(y.map(_.toArray):_*)
    val s_final: MatrixList = run(x_matrix, y_matrix, s_init, config.iteration)
    TrainedModel(s_final)
  }

  def predict(x: List[List[Double]], model: TrainedModel): List[List[Double]] = {
    val x_matrix: Matrix = DenseMatrix(x.map(_.toArray):_*)
    val l = forwardPropagation(
      x_matrix,
      model.s_final,
      sigmoid
    )

    val y: Matrix = l.last
    y.toArray.toList.map(List(_))
  }
}
