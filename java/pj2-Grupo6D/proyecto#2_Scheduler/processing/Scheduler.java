/*Process.java
 * By:Mario Renato Monroy Cortez
 * Carnet: 23000140
 */
package processing;

import java.util.Queue;
import java.util.LinkedList;

public abstract class Scheduler {
    protected boolean dualMode;          // Modo dual 
    protected String timeRange;          // Rango de tiempo para la simulación.
    protected float arithmeticTime;      // Tiempo requerido para operaciones aritméticas.
    protected float ioTime;              // Tiempo requerido para operaciones de E/S.
    protected float conditionalTime;     // Tiempo requerido para operaciones condicionales.
    protected float loopTime;            // Tiempo requerido para iteraciones de bucles.
    protected Queue<Process> processQueue; // Cola para gestionar los procesos entrantes.

    /**
     * Constructor para inicializar el planificador.
     */
    public Scheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
        this.dualMode = dualMode;
        this.timeRange = timeRange;
        this.arithmeticTime = arithmeticTime;
        this.ioTime = ioTime;
        this.conditionalTime = conditionalTime;
        this.loopTime = loopTime;
        this.processQueue = new LinkedList<>(); // Inicializa la cola de procesos.
    }

    /**
     * Método abstracto que cada subclase debe implementar para iniciar la simulación.
     */
    public abstract void startSimulation();

    /**
     * Agrega un proceso a la cola de procesos.
     * @param process El proceso a agregar.
     */
    public void addProcess(Process process) {
        if (process != null) {
            processQueue.offer(process);
        }
    }

    /**
     * Finaliza la simulación y muestra un mensaje.
     */
    protected void endSimulation() {
        System.out.println("Simulación finalizada.");
        System.out.println("Procesos completados: " + processQueue.size());
    }

    /**
     * Devuelve el rango de tiempo como una cadena.
     */
    public String getTimeRange() {
        return timeRange;
    }
}