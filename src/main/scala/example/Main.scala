package example

object Main extends App {
  //  L0     L1     L2
  //         ◯
  //  ◯      ◯
  //  ◯  S0  ◯  S1  ◯
  //  ◯      ◯
  //
  //  X             y

  val config = Perceptron.configure(List(3, 4, 1), 1000)

  val model = Perceptron.fit(
    List(
      List(0.0, 0.0, 1.0),
      List(0.0, 1.0, 1.0),
      List(1.0, 0.0, 1.0),
      List(1.0, 1.0, 1.0)
    ),
    List(List(0.0), List(1.0), List(1.0), List(0.0)),
    config
  )

  val result = Perceptron.predict(
    List(
      List(0.0, 0.0, 1.0),
      List(0.0, 1.0, 1.0),
      List(1.0, 0.0, 1.0),
      List(1.0, 1.0, 1.0)
    ),
    model
  )

  println(result)
}
