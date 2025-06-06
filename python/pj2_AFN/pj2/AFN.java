/*
	Utilice esta clase para guardar la informacion de su
	AFN. NO DEBE CAMBIAR LOS NOMBRES DE LA CLASE NI DE LOS 
	METODOS que ya existen, sin embargo, usted es libre de 
	agregar los campos y metodos que desee.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AFN{

	/*
		Implemente el constructor de la clase AFN
		que recibe como argumento un string que 
		representa el path del archivo que contiene
		la informacion del AFN (i.e. "Documentos/archivo.AFN").
		Puede utilizar la estructura de datos que desee
	*/

	private List<String> alphabet;                // symbols of input
	private int numStates;                        // total states including 0
	private Set<Integer> finalStates;             // set of accepting states
	private List<List<Set<Integer>>> transitions; // row 0: lambda, row i: symbol i-1

	public AFN(String path)  throws IOException{

		BufferedReader reader = new BufferedReader(new FileReader(path));

		// 1. Alfabeto
		String line = reader.readLine();

		if (line == null) throw new IOException("Archivo vacio");

		alphabet = Arrays.asList(line.split(","));

		// 2. Número de estados
		line = reader.readLine();

		numStates = Integer.parseInt(line.trim());

		// 3. Estados finales
		line = reader.readLine();

		finalStates = new HashSet<>();

		if (!line.isEmpty()) {

			for (String tok : line.split(",")) {

				finalStates.add(Integer.parseInt(tok.trim()));

			}
		}

		// 4. Inicializar matriz de transiciones (M+1 filas × N columnas)
		int rows = alphabet.size() + 1;  // +1 para λ

		transitions = new ArrayList<>();

		for (int i = 0; i < rows; i++) {

			List<Set<Integer>> row = new ArrayList<>();

			for (int j = 0; j < numStates; j++) {

				row.add(new HashSet<>());
			}

			transitions.add(row);
		}

		// 5. Cargar cada fila de la matriz
		for (int i = 0; i < rows; i++) {

			line = reader.readLine();

			if (line == null)

				throw new IOException("Matriz de transiciones incompleta");

			String[] cells = line.split(",");

			if (cells.length != numStates)

				throw new IOException("Se esperaban " + numStates + " columnas en la fila " + i);

			for (int j = 0; j < numStates; j++) {

				String cell = cells[j].trim();

				if (!cell.isEmpty()) {

					String[] parts = cell.split(";");

					for (String p : parts) {

						transitions.get(i).get(j).add(Integer.parseInt(p.trim()));

					}
				}
			}
		}
		reader.close();
	}

	/*
		Implemente el metodo accept, que recibe como argumento
		un String que representa la cuerda a evaluar, y devuelve
		un boolean dependiendo de si la cuerda es aceptada o no 
		por el AFN. Recuerde lo aprendido en el proyecto 1.
	*/
	public boolean accept(String string) {
		Set<Integer> currentStates = lambdaClosure(Set.of(1)); // realice un agregado Set.of(1)

		for (char c : string.toCharArray()) {
			// 🔧 Conversión de char a String para evitar indexOf = -1
			int symbolIndex = alphabet.indexOf(String.valueOf(c));
			if (symbolIndex == -1) return false;

			Set<Integer> nextStates = new HashSet<>();
			for (int state : currentStates) {
				nextStates.addAll(transitions.get(symbolIndex + 1).get(state));
			}
			currentStates = lambdaClosure(nextStates);
			if (currentStates.isEmpty()) return false;
		}

		for (int state : currentStates) {
			if (finalStates.contains(state)) return true;
		}
		return false;
	}

	// Helper method to compute lambda closure of a set of states
	private Set<Integer> lambdaClosure(Set<Integer> states) {
		Set<Integer> closure = new HashSet<>(states);
		LinkedList<Integer> stack = new LinkedList<>(states); //reemplace el bucle por una pila para evitar ciclos infinitos

		while (!stack.isEmpty()) {
			int state = stack.pop();
			for (int next : transitions.get(0).get(state)) {
				if (!closure.contains(next)) {
					closure.add(next);
					stack.push(next);
				}
			}
		}
		return closure;
	}

	/*
		Implemente el metodo toAFD. Este metodo debe generar un archivo
		de texto que contenga los datos de un AFD segun las especificaciones
		del proyecto.
	*/
	public void toAFD(String afdPath) throws IOException {
		List<Set<Integer>> dfaStates = new ArrayList<>();
		List<List<Integer>> dfaTransitions = new ArrayList<>();
		Set<Integer> dfaFinalStates = new HashSet<>();

		Set<Integer> initialState = lambdaClosure(Set.of(1));
		dfaStates.add(initialState);

		for (int i = 0; i < dfaStates.size(); i++) {
			Set<Integer> currentState = dfaStates.get(i);
			List<Integer> transitionsRow = new ArrayList<>();

			for (int state : currentState) {
				if (finalStates.contains(state)) {
					dfaFinalStates.add(i);
					break;
				}
			}

			for (int symbol = 0; symbol < alphabet.size(); symbol++) {
				Set<Integer> nextState = new HashSet<>();
				for (int state : currentState) {
					nextState.addAll(transitions.get(symbol + 1).get(state));
				}
				nextState = lambdaClosure(nextState);

				int stateIndex = dfaStates.indexOf(nextState);
				if (stateIndex == -1) {
					stateIndex = dfaStates.size();
					dfaStates.add(nextState);
				}
				transitionsRow.add(stateIndex);
			}
			dfaTransitions.add(transitionsRow);
		}

		try (java.io.PrintWriter writer = new java.io.PrintWriter(afdPath)) {
			writer.println(String.join(",", alphabet));
			writer.println(dfaStates.size());

			StringBuilder finalStatesStr = new StringBuilder();
			for (int state : dfaFinalStates) {
				if (finalStatesStr.length() > 0) finalStatesStr.append(",");
				finalStatesStr.append(state);
			}
			writer.println(finalStatesStr.toString());

			for (List<Integer> stateTransitions : dfaTransitions) {
				StringBuilder transitionStr = new StringBuilder();
				for (int i = 0; i < stateTransitions.size(); i++) {
					if (i > 0) transitionStr.append(",");
					transitionStr.append(stateTransitions.get(i));
				}
				writer.println(transitionStr);
			}
		}
	}

	/*
		El metodo main debe recibir como primer argumento el path
		donde se encuentra el archivo ".afd" y debe empezar a evaluar 
		cuerdas ingresadas por el usuario una a una hasta leer una cuerda vacia (""),
		en cuyo caso debe terminar. Tiene la libertad de implementar este metodo
		de la forma que desee. Si se envia la bandera "-to-afd", entonces en vez de
		evaluar, debe generar un archivo .afd
	*/
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Uso: java AFN ruta.afn [-to-afd salida.afd]");
			return;
		}

		String rutaAFN = args[0];
		AFN afn;
		try {
			afn = new AFN(rutaAFN);
		} catch (IOException e) {
			System.err.println("Error al leer el archivo AFN: " + e.getMessage());
			return;
		}

		if (args.length >= 3 && "-to-afd".equals(args[1])) {
			String rutaSalida = args[2];
			try {
				afn.toAFD(rutaSalida);
				System.out.println("AFD generado exitosamente en: " + rutaSalida);
			} catch (IOException e) {
				System.err.println("Error al generar el archivo AFD: " + e.getMessage());
			}
		} else {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
				System.out.println("Ingrese cadenas para evaluar (línea vacía para terminar):");
				String linea;
				while ((linea = br.readLine()) != null && !linea.isEmpty()) {
					boolean aceptada = afn.accept(linea);
					System.out.println(aceptada ? "Aceptada" : "Rechazada");
				}
			}
		}
	}
}
