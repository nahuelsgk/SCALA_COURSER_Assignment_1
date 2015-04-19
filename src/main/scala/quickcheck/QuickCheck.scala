package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._  //para usar las funciones de matematicas

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  //Insertar un elemento en heap vacio y buscar el minimo, debe devolver el minimo
  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  //HINT1: Insertar dos elementos en un heap vacio, encontrar el minimo del header deberia ser el menor de los elementos
  property("insert2elementsEmptyHeap") = forAll {(a: A, b:A) =>
    val h = insert(a, empty) //Inserto el primer elemento en un heap vacio
    val i = insert(b, h)     //Inserto el segundo elemento en el heap anteriror
    val min = findMin(i)     //Busco el min del heap con los dos elementos     
    val min_input = if (a < b) a else b //Busco cual de mis inputs es menor
    min == min_input          //El menor de mis inputs ha de ser igual al menor de elemeneto del  heaps anteriormente creado
  }
  
  //HINT2: Inserta un elemento en un heap vacio, borrar el minimo, debe devolve el head vacio.
  property("insertElementIntoEmptyHeadAndDeleteMin") = forAll{ a: A => 
      val h   = insert(a, empty)
      isEmpty(deleteMin(h))
  }
  
  //Este es un ejemplo COURSERA usando un generedor
  //Si insertas un valor que sabes que es igual que ele minimo y busca el minimo, deben ser iguales
  property("gen1") = forAll { (h: H) =>     //siendo h un heap
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h))==m
  }
  
  //Hint 3: dado cualquier heap, 
  //debes obtener una secuencia ordenada de elemento si iterativamente encuentras y borras el minimo.
  property("sortedElements") = forAll { (h:H) =>
    isSeqSortedIfISearchTheMinimumAndDeleted(h)
  }
  
  def isSeqSortedIfISearchTheMinimumAndDeleted(h: H): Boolean = {
    if (isEmpty(h)) true
    else {
      val minimum = findMin(h)
      val h2 = deleteMin(h)
      
      // O esta vacio O iterativamente miro si el menor encontrado es menor o igual al siguiente
      isEmpty(h2) || (minimum <= findMin(h2) && isSeqSortedIfISearchTheMinimumAndDeleted(h2))  
    }
  }
  
  //Hint 4:
  //d) Encontrar el minimo de una meld de dos heaps, debe devolver el minimo del uno o del otro.
  property("minimumTwoHeaps") = forAll { (h1: H, h2: H) => 
    val h_melded = meld(h1, h2)
    findMin(h_melded) == min(findMin(h1), findMin(h2)) 
  }
  
  //https://gist.github.com/wh5a/7394082
  property("meldTwoHeaps") = forAll { (h1: H, h2: H) =>
    //Mover el minimo de un heap a otro y fusionarlos
    //ha de ser igual a la fusion de los originales
    heapEqual(meld(h1, h2),meld(deleteMin(h1), insert(findMin(h1), h2)))
  } 
  
  //PENDIENTE
  //https://class.coursera.org/reactive-002/forum/thread?thread_id=85
  //That I did was a property test that receives an arbitrary list of Ints. The test inserts all the Ints in the list into an initially empty Heap.
  //Then, using findMin and deleteMin I create a List with all the Ints from the Heap. Finally I check that the list obtained from the heap is equal to the list received by the test (you'll have to sort the arbitrary list)
  
  //Dos heaps son iguales si buscamos el minimo iterativamente 
  def heapEqual(h1: H, h2: H): Boolean =
      if (isEmpty(h1) && isEmpty(h2)) true
      else {
        val m1 = findMin(h1)
        val m2 = findMin(h2)
        m1 == m2 && heapEqual(deleteMin(h1), deleteMin(h2)) 
  }
  //Este un generadpr de MAP[Int]
  /*lazy val genMap: Gen[Map[Int,Int]] = for { 
  k <- arbitrary[Int]                        //Generas un entero
  v <- arbitrary[Int]                        //Generas otro entero
  m <- oneOf(const(Map.empty[Int,Int]), genMap) //Seleccionas uno, y llamada recursiva para el resto de valores
} yield m.updated(k, v) //
  */
  lazy val genList: Gen[List[Int]] = for {
    n <-arbitrary[Int]
    l <-oneOf(const(List.empty[Int]), genList )
  }yield n::l

  lazy val genHeap: Gen[H] = for {            //lazy: se ejecuta cuando se accede
      n <- arbitrary[A]                       //Generas un tipo arbitrario
      h <- oneOf( Gen.const(empty), genHeap ) //Seleccionas un valor vacio o generas otro
  } yield insert(n, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
