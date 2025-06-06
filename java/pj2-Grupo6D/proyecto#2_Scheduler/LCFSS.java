import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList; // Para inicializar la cola de procesos
import processing.Process;
import processing.Scheduler;

public class LCFSS extends Scheduler {
    private Stack<Process> processStack; // Pila para gestionar los procesos.
    private Queue<Process> processQueue; // Cola para recibir los procesos.
    private int completedProcesses = 0; // Contador de procesos completados.

    // Constructor que inicializa los valores y la pila
    public LCFSS(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
        super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
        this.processStack = new Stack<>(); // Inicializa la pila.
        this.processQueue = new LinkedList<>(); // Inicializa la cola como una lista enlazada.
    }

    @Override
    public void startSimulation() {
        System.out.println("Iniciando simulación con política Last-Come, First-Served (LCFS)");

        // Transferir los procesos de la cola a la pila
        while (!processQueue.isEmpty()) { 
            Process process = processQueue.poll(); // Extrae de la cola
            if (process != null) {
                processStack.push(process); // Agrega a la pila
            }
        }

        // Procesar los elementos de la pila
        while (!processStack.isEmpty()) {
            Process process = processStack.pop(); // Obtiene el proceso más reciente
            showCurrentProcess(process); // Muestra el proceso actual
            try {
                process.run(); // Simula la ejecución del proceso
                completedProcesses++; // Incrementa el contador de procesos completados
            } catch (Exception e) {
                System.err.println("Error ejecutando el proceso " + process.getProcessId() + ": " + e.getMessage());
            }
            showStack(); // Muestra el estado de la pila
        }

        // Finaliza la simulación
        endSimulation();
        System.out.println("Total de procesos Completados: " + completedProcesses);//Muestra el total de procesos  completados
    }

    /**
     * Muestra los detalles del proceso actual en ejecución.
     * @param process El proceso actual.
     */
    private void showCurrentProcess(Process process) {
        if (process != null) {
            System.out.println("Atendiendo proceso: " + process.getProcessId() + " de tipo " + process.getType() + 
                               " con tiempo de atención: " + process.getServiceTime());
        }
    }

    /**
     * Muestra el estado actual de la pila de procesos.
     */
    private void showStack() {
        System.out.println("Pila de procesos actual:");
        for (Process process : processStack) {
            System.out.printf("ID: %d | Tipo: %s | Tiempo de servicio: %.2f\n", 
                              process.getProcessId(), process.getType(), process.getServiceTime());
        }
    }
}


