
import java.util.HashSet;
import java.util.Set;

/**
 * El algoritmo consiste básicamente de un método recursivo, que examina todas las combinaciones de 9 ingredientes
 * que conforman el pastel de un total de 10. A cada ingrediente le asigna un valor entero distinto, siendo el 
 * Dulce=1, Fruta=2, Confite=3 y Masita=4. Como se repiten los ingredientes, las combinaciones obtenidas pueden dar 
 * pasteles iguales. Para asegurarnos que las soluciones sean distintas, cuando se consigue un pastel rico se calcula
 * un hash o identificador del mismo de tipo entero. Este hash es simplemente la representación numérica en base 10 de 
 * los 9 ingredientes del pastel. Una vez calculado el hash del pastel rico, se guarda en un Set, el cual nos garantiza 
 * que las soluciones allí encontradas al final del algoritmo serán únicas.
 *
 * La corrida de este algoritmo da una solución de 8112 pasteles ricos distintos.
 */
public class PastelRicoSolver {
  /**
   * La cantidad máxima de ingredientes en el pastel
   */
  private static final int MAX_INGREDIENTS = 9;
  /**
   * El pastel propiamente dicho
   */
  private int pastel[] = new int[MAX_INGREDIENTS];
  /**
   * Los 10 ingredientes. Los dulces están representados por el valor entero 1, 
   * las frutas por el 2, los confites por el 3 y la masita por el 4
   */
  private int[] ingredients = new int[] {1, 1, 1, 2, 2, 2, 3, 3, 3, 4};
  /**
   * Array usado para llevar la cuenta de qué elementos ya se usaron en el pastel
   */
  private boolean[] used = new boolean[ingredients.length];
  /**
   * Set usado para guardar los hashes de los pasteles ricos, o sea 
   * de aquéllos que son soluciones únicas
   */
  private Set<Long> solutionSet = new HashSet<Long>();

  /**
   * Encuentra todos los pasteles ricos distintos
   */
  public void solve() {
    doSolve(0);
  }

  /**
   * Devuelve la cantidad de soluciones distintas encontradas
   */
  public int getSolutionCount() {
    return solutionSet.size();
  }

  private void doSolve(int useCount) {
    if (useCount == MAX_INGREDIENTS) {
      // Se llegó al límite de ingredientes en el pastel. Se chequea si el pastel es rico.

      if (checkSolution()) {
        // El pastel es rico. Se calcula su hash para chequear que 
        // no esté en el set de soluciones distintas ya encontradas
        long pastelHash = calcPastelHash();

        // No importa si la solución es nueva o duplicada, como agregamos  
        // el hash a un Set, sólo quedarán las soluciones distintas 
        solutionSet.add(pastelHash);
      }
      return;
    }

    for (int i = 0; i < ingredients.length; i++) {
      if (used[i])
        continue;

      pastel[useCount] = ingredients[i];
      used[i] = true;

      doSolve(useCount + 1);

      used[i] = false;
    }    
  }

  /**
   * Calcula un hash del pastel, es decir, un valor que identifica unívocamente 
   * a la configuración de los ingredientes en el pastel dado
   * @return un hash del pastel de tipo long  
   */
  private long calcPastelHash() {
    long hash = 0;
    int power = 1;

    for (int i = 0; i < pastel.length; i++) {
      hash += pastel[i] * power;
      power *= 10;
    }
    return hash;
  }

  /**
   * Chequea si la fila dada hace rico al pastel
   * @param row fila a chequear
   * @return true si la fila dada hace rico al pastel, false sino
   */
  private boolean isRowSolution(int row) {

    // Chequea por los mismos ingredientes
    if (pastel[row * 3] == pastel[row * 3 + 1] && pastel[row * 3] == pastel[row * 3 + 2])
      return true;

    // Chequea tomando en cuenta la masita
    if (pastel[row * 3] == 4) {
      return pastel[row * 3 + 1] == pastel[row * 3 + 2];

    } else if (pastel[row * 3 + 1] == 4) {
      return pastel[row * 3] == pastel[row * 3 + 2];

    } else if (pastel[row * 3 + 2] == 4) {
      return pastel[row * 3] == pastel[row * 3 + 1];
    }
    return false;
  }

  /**
   * Chequea si la columna dada hace rico al pastel
   * @param col columna a chequear
   * @return true si la columna dada hace rico al pastel, false sino
   */
  private boolean isColSolution(int col) {

    // Chequea por los mismos ingredientes
    if (pastel[col] == pastel[col + 3] && pastel[col] == pastel[col + 6])
      return true;

    // Chequea tomando en cuenta la masita
    if (pastel[col] == 4) {
      return pastel[col + 3] == pastel[col + 6];

    } else if (pastel[col + 3] == 4) {
      return pastel[col] == pastel[col + 6];

    } else if (pastel[col + 6] == 4) {
      return pastel[col] == pastel[col + 3];
    }
    return false;
  }

  /**
   * Chequea si el pastel es un pastel rico
   * @return true si lo es, false en caso contrario
   */
  private boolean checkSolution() {

    // Chequeamos las filas

    for (int i = 0; i < 3; i++) {
      if (isRowSolution(i))
        return true;
    }

    // Chequeamos las columnas

    for (int i = 0; i < 3; i++) {
      if (isColSolution(i))
        return true;
    }

    return false;
  }
}
