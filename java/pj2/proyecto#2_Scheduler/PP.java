/* PP.java */
/**
			 ** Hecho por: Mario  Renato Monroy  Cortez
			 ** Carnet: 23000140
			 ** Seccion: D
			**/	
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PP {
    // Constantes de tiempo de servicio
    private static final int ARITHMETIC_TIME = 400;
    private static final int IO_TIME = 200;
    private static final int CONDITIONAL_TIME = 300;
    private static final int ITERATIVE_TIME = 500;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso incorrecto. Ejemplo:");
            System.out.println("java PP -priority 10");
            return;
        }

        String policy = args[0];
        int n = Integer.parseInt(args[1]);

        // Colas para procesos según prioridad
        ConcurrentLinkedQueue<Process> priority1Queue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Process> priority2Queue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Process> priority3Queue = new ConcurrentLinkedQueue<>();

        // Generar procesos aleatorios
        generateProcesses(n, priority1Queue, priority2Queue, priority3Queue);

        System.out.println("pid\ttype\tat\tst\tpriority\tct\ttat\twt");

        Scheduler scheduler;
        switch (policy) {
            case "-priority" -> scheduler = new PriorityScheduler(priority1Queue, priority2Queue, priority3Queue);
            // Otras políticas de planificación pueden añadirse aquí.
            default -> throw new IllegalArgumentException("Política no válida: " + policy);
        }

        scheduler.startSimulation();
    }

    // Método para generar procesos aleatorios
    private static void generateProcesses(int n, ConcurrentLinkedQueue<Process> priority1Queue,
                                          ConcurrentLinkedQueue<Process> priority2Queue,
                                          ConcurrentLinkedQueue<Process> priority3Queue) {
        Random rand = new Random();
        int arrivalTime = 0;

        for (int i = 1; i <= n; i++) {
            String type;
            int serviceTime;
            int priority;

            switch (rand.nextInt(4)) {
                case 0 -> {
                    type = "Aritmético";
                    serviceTime = ARITHMETIC_TIME;
                    priority = 1;
                }
                case 1 -> {
                    type = "I/O";
                    serviceTime = IO_TIME;
                    priority = 2;
                }
                case 2 -> {
                    type = "Condicional";
                    serviceTime = CONDITIONAL_TIME;
                    priority = 3;
                }
                default -> {
                    type = "Iterativo";
                    serviceTime = ITERATIVE_TIME;
                    priority = 1;
                }
            }

            arrivalTime += rand.nextInt(100); // Incremento aleatorio para el tiempo de llegada
            Process process = new Process(i, arrivalTime, serviceTime, type, priority);
            switch (priority) {
                case 1 -> priority1Queue.add(process);
                case 2 -> priority2Queue.add(process);
                case 3 -> priority3Queue.add(process);
            }
        }
    }
}

// Clase Process para representar los procesos
class Process extends Thread {
    private final int pid;
    private final int arrivalTime;
    private final int serviceTime;
    private final String type;
    private final int processPriority;

    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;

    public Process(int pid, int arrivalTime, int serviceTime, String type, int processPriority) {
        super("Process-" + pid);
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.type = type;
        this.processPriority = processPriority;
    }



    public int getArrivalTime() { return arrivalTime; }
    public int getServiceTime() { return serviceTime; }
    public int getProcessPriority() { return processPriority; }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
        this.turnaroundTime = completionTime - arrivalTime;
        this.waitingTime = turnaroundTime - serviceTime;
    }

    public void display() {
        System.out.printf("%d\t%s\t%d\t%d\t%d\t%d\t%d\t%d\n",
                pid, type, arrivalTime, serviceTime, processPriority, completionTime, turnaroundTime, waitingTime);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(serviceTime); // Simulación del tiempo de servicio
        } catch (InterruptedException e) {
            System.out.println("Error mientras se procesaba: " + e.getMessage());
        }
    }
}

// Clase abstracta Scheduler para los planificadores
abstract class Scheduler {
    protected ConcurrentLinkedQueue<Process> priority1Queue;
    protected ConcurrentLinkedQueue<Process> priority2Queue;
    protected ConcurrentLinkedQueue<Process> priority3Queue;

    public Scheduler(ConcurrentLinkedQueue<Process> p1, ConcurrentLinkedQueue<Process> p2, ConcurrentLinkedQueue<Process> p3) {
        this.priority1Queue = p1;
        this.priority2Queue = p2;
        this.priority3Queue = p3;
    }

    public abstract void startSimulation();
}

// Implementación de planificación por prioridad
class PriorityScheduler extends Scheduler {
    public PriorityScheduler(ConcurrentLinkedQueue<Process> p1, ConcurrentLinkedQueue<Process> p2, ConcurrentLinkedQueue<Process> p3) {
        super(p1, p2, p3);
    }

    @Override
    public void startSimulation() {
        int currentTime = 0;

        while (!priority1Queue.isEmpty() || !priority2Queue.isEmpty() || !priority3Queue.isEmpty()) {
            Process currentProcess = null;

            if (!priority1Queue.isEmpty()) {
                currentProcess = priority1Queue.poll();
            } else if (!priority2Queue.isEmpty()) {
                currentProcess = priority2Queue.poll();
            } else if (!priority3Queue.isEmpty()) {
                currentProcess = priority3Queue.poll();
            }

            if (currentProcess != null) {
                if (currentProcess.getArrivalTime() > currentTime) {
                    currentTime = currentProcess.getArrivalTime();
                }
                currentProcess.setCompletionTime(currentTime + currentProcess.getServiceTime());
                currentTime += currentProcess.getServiceTime();

                currentProcess.start();
                try {
                    currentProcess.join();
                } catch (InterruptedException e) {
                    System.out.println("Error mientras se procesaba: " + e.getMessage());
                }

                currentProcess.display();
            }
        }
    }
}
