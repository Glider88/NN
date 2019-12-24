package example

import example.Matrix.MatrixList
import example.Matrix.Matrix
import example.Matrix.sigmoid
import example.Matrix.sigmoidDerivation

import scala.annotation.tailrec

object NeuralNetwork {
  def forwardPropagation(x: Matrix, s: MatrixList, normalizer: Matrix => Matrix): MatrixList =
    (List(x) /: s) {
      (l: MatrixList, s: Matrix) => l :+ normalizer(l.last * s)
    }

  def backPropagationDelta(l: MatrixList, y: Matrix, s: MatrixList, normalizer: Matrix => Matrix): MatrixList = {
    val s_ext = s :+ (y - l.last)
    ((l zip s_ext) :\ List.empty[Matrix]) {
      (l_s: (Matrix, Matrix), l_norm: MatrixList) => {
        val ln = l_s._1
        val sn = l_s._2
        l_norm match {
          case Nil => List(sn *:* normalizer(ln))
          case l_delta =>
            val l_err = l_delta.last * sn.t
            l_delta :+ (l_err *:* normalizer(ln))
        }
      }
    }.reverse
  }

  def updateSyn(s: MatrixList, l: MatrixList, l_delta: MatrixList): MatrixList =
    (s, l.init, l_delta.tail).zipped.toList.map(sll => sll._1 + sll._2.t * sll._3)

  def dim(ml: MatrixList): List[String] = ml.map(m => s"${m.rows}x${m.cols}")

  @tailrec
  def run(x: Matrix, y: Matrix, s: MatrixList, iteration: Int): MatrixList = {
    iteration match {
      case i if i <= 0 => s
      case _ =>
        val l = forwardPropagation(x, s, sigmoid)
        val l_delta = backPropagationDelta(l, y, s, sigmoidDerivation)
        val new_s = updateSyn(s, l, l_delta)
        run(x, y, new_s, iteration - 1)
    }
  }
}
