/* ProcessScheduler.java */
/**
			 ** Hecho por: Mario  Renato Monroy  Cortez
			 ** Carnet: 23000140
			 ** Seccion: D
			**/	
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProcessScheduler {
            static final int ARITHMETIC_TIME = 400;
            static final int IO_TIME = 200;
            static final int CONDITIONAL_TIME = 300;
            static final int ITERATIVE_TIME = 500;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Uso incorrecto. Ejemplo:");
            System.out.println("java ProcessScheduler -fcfs 1.5-3 2 1 2.5 3");
            scanner.close();
            return;
        }

        boolean dualMode = false;
        String policy = args[0];
        int argIndex = 1;

        if (policy.equals("-dual")) {
            dualMode = true;
            policy = args[1];
            argIndex++;
        }

        String timeRange = args[argIndex++];
        float arithmeticTime = Float.parseFloat(args[argIndex++]);
        float ioTime = Float.parseFloat(args[argIndex++]);
        float conditionalTime = Float.parseFloat(args[argIndex++]);
        float loopTime = Float.parseFloat(args[argIndex++]);
        float quantum = policy.equals("-rr") ? Float.parseFloat(args[argIndex]) : -1;

        // Crear e inicializar el simulador de acuerdo a la política y parámetros
        Scheduler scheduler = switch (policy) {
            case "-fcfs" -> new FCFScheduler(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
            case "-lcfs" -> new LCFSScheduler(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
            case "-rr" -> new RRScheduler(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime, quantum);
            case "-pp" -> new PriorityScheduler(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
            default -> throw new IllegalArgumentException("Política no válida: " + policy);
        };

        // Iniciar la simulación
        scheduler.startSimulation();

        // Cerrar el scanner al finalizar
        scanner.close();
    }

    // Abstracción de la clase Scheduler para diferentes políticas
    abstract static class Scheduler {
        protected boolean dualMode;
        protected String timeRange;
        protected float arithmeticTime;
        protected float ioTime;
        protected float conditionalTime;
        protected float loopTime;

        protected ConcurrentLinkedQueue<Process> processQueue = new ConcurrentLinkedQueue<>();
        protected int completedProcesses = 0;

        public Scheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
            this.dualMode = dualMode;
            this.timeRange = timeRange;
            this.arithmeticTime = arithmeticTime;
            this.ioTime = ioTime;
            this.conditionalTime = conditionalTime;
            this.loopTime = loopTime;
        }

        public abstract void startSimulation();

        protected void displayStatus() {
            System.out.println("Estado de la cola de procesos:");
            processQueue.forEach(process -> {
                System.out.printf("Proceso %d | Tipo: %s | Tiempo de Servicio: %.2f\n", process.getProcessId(), process.getType(), process.getServiceTime());
            });
            System.out.printf("Procesos completados: %d\n", completedProcesses);
        }

        protected void endSimulation() {
            System.out.println("Simulación finalizada. Estadísticas:");
            System.out.printf("Total de procesos atendidos: %d\n", completedProcesses);
            System.out.printf("Procesos restantes en cola: %d\n", processQueue.size());
            System.out.printf("Política utilizada: %s\n", this.getClass().getSimpleName());
        }
    }

    // Implementación de First-Come, First-Served (FCFS)
    static class FCFScheduler extends Scheduler {
        public FCFScheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
            super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
        }

        @Override
        public void startSimulation() {
            System.out.println("Iniciando simulación con política First-Come, First-Served (FCFS)");
            // Lógica específica para el algoritmo FCFS
        }
    }

    // Implementación de Last-Come, First-Served (LCFS)
    static class LCFSScheduler extends Scheduler {
        public LCFSScheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
            super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
        }

        @Override
        public void startSimulation() {
            System.out.println("Iniciando simulación con política Last-Come, First-Served (LCFS)");
            // Lógica específica para el algoritmo LCFS
        }
    }

    // Implementación de Round Robin (RR)
    static class RRScheduler extends Scheduler {
        private float quantum;

        public RRScheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime, float quantum) {
            super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
            this.quantum = quantum;
        }

        @Override
        public void startSimulation() {
            System.out.println("Iniciando simulación con política Round Robin (RR) y quantum de " + quantum);
            // Lógica específica para el algoritmo Round Robin
        }
    }

    // Implementación de Priority Policy (PP)
    static class PriorityScheduler extends Scheduler {
        public PriorityScheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime) {
            super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
        }

        @Override
        public void startSimulation() {
            System.out.println("Iniciando simulación con política Priority Policy (PP)");
            // Lógica específica para el algoritmo de prioridad
        }
    }

    // Clase de proceso
    static class Process extends Thread {
        private final int id;
        private final String type;
        private final float serviceTime;

        public Process(int id, String type, float serviceTime) {
            this.id = id;
            this.type = type;
            this.serviceTime = serviceTime;
        }

        public int getProcessId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public float getServiceTime() {
            return serviceTime;
        }

        @Override
        public void run() {
            System.out.printf("Atendiendo proceso %d de tipo %s con tiempo de servicio %.2f segundos\n", id, type, serviceTime);
            try {
                Thread.sleep((long) (serviceTime * 1000));
            } catch (InterruptedException e) {
                System.out.println("Error en el proceso: " + e.getMessage());
            }
        }
    }
}
