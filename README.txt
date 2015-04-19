Heap:
Las priority queue es un priority que siempre sale el menor valor y van entrando encolandose.

La interfaz(trait) es src/main/scala/quickcheck/Heap/Heap que define las funciones.

SCALACHECK
TODO a) 
Hay que escribir un conjunto de propiedades en QuickCheckHeap(src/main/scala/quickcheck/QuickCheck.scala)
¡¡Las propiedades de scalaCheck son propiedades que se han de cumplir siempre!!

Una propiedad ejemplo es min1. 
Esta propiedad comprueba que si inserta un elemento en una lista vacia, devuelve un el mismo elemento min.
property("min1") = forAll { a: Int => //forAll es una metodo de scalaCheck
									  //a es el paramentro de entrada
    	val h = insert(a, empty)
    	findMin(h) == a
  }
  
TODO b)
Hay que escribir un generador que lo utilice una propiedad:
property("gen1") = forAll { (h: H) =>		//forAll es un metodo de scalaCheck
											//h es el generador
  val m = if (isEmpty(h)) 0 else findMin(h)
  findMin(insert(m, h))==m
}

El generador:
lazy val genMap: Gen[Map[Int,Int]] = for {
  k <- arbitrary[Int]
  v <- arbitrary[Int]
  m <- oneOf(const(Map.empty[Int,Int]), genMap)
} yield m.updated(k, v)
  

Recomiendad 4 propiedades:
a) Insertar dos elementos en un heap vacio, encontrar el minimo del header deberia ser el menor de los elementos
b) Inserta un elemento en un heap vacio, borrar el minimo, debe devolve el head vacio.
c) Dado cualquier heap, debes obtener una secuencia ordenada de elemento si iterativamente encuentras y borras el minimo.
(Hint: recursion and helper functions are your friends.)
d) Encontrar el minimo de una meld de dos heaps, debe devolver el minimo del uno del otro.

Del FORO:
OTRO Test:
* Generate two random heaps
* Melt them into a single heap
* Create an ordered list from the melted heap
* Create an ordered lists from the two original heaps
* What do you expect regarding these two lists? :-)


Entorno:
> /home/nahuel/git/sbt/bin/sbt
> compile 
> test

¿Como ejecuto el QuickCheck? test desde la consola. 
Ejecuta el /src/test/scala/QuickCheckSuite.scala y dentro ejecuta el scalaCheck.

¿Como ejecuto mas detalladamente para ver los resultados de las properties?
En sbt
> test:console
##
object QuickCheckBinomialHeap extends QuickCheckHeap with BinomialHeap

scala > object QuickCheckBogus1BinomialHeap extends QuickCheckHeap with Bogus1BinomialHeap
scala > QuickCheckBogus1BinomialHeap.check

¿Como ejecuto un generador?









