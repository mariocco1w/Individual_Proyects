/* FCFS.java */
/**
			 ** Hecho por: Mario  Renato Monroy  Cortez
			 ** Carnet: 23000140
			 ** Seccion: D
			**/	
            import java.util.Random;
            import java.util.concurrent.ConcurrentLinkedQueue;
            
            class Process {
                private int pid; // ID del proceso
                private int at; // tiempo de llegada
                private int st; // tiempo de servicio
                private int ct; // tiempo de finalización
                private int tat; // tiempo de retorno
                private int wt; // tiempo de espera
                private String type; // Tipo de proceso: Aritmético, I/O, Condicional, o Iterativo
                
                public String getType() {
                    return type;
                }
                
                public Process(int pid, int at, int st, String type) {
                    this.pid = pid;
                    this.at = at;
                    this.st = st;
                    this.type = type;
                }
            
                public int getVar(String var) {
                    return switch (var) {
                        case "at" -> at;
                        case "st" -> st;
                        case "ct" -> ct;
                        case "tat" -> tat;
                        case "wt" -> wt;
                        case "pid" -> pid;
                        default -> 0;
                    };
                }
            
                public void setVar(String var, int value) {
                    switch (var) {
                        case "at" -> at = value;
                        case "st" -> st = value;
                        case "ct" -> ct = value;
                        case "tat" -> tat = value;
                        case "wt" -> wt = value;
                        default -> pid = value;
                    }
                }
            
                public void updateAfterCt() {
                    tat = ct - at;
                    wt = tat - st;
                }
            
                public void display() {
                    System.out.printf("%d\t%s\t%d\t%d\t%d\t%d\t%d\n", pid, type, at, st, ct, tat, wt);
                }
            
                public void processService() {
                    try {
                        Thread.sleep(st); // Simula el tiempo de servicio del proceso
                    } catch (InterruptedException e) {
                        System.out.println("Error mientras se procesaba: " + e.getMessage());
                    }
                }
            }
            
            // Clase base para los planificadores
            abstract class Scheduler {
                protected ConcurrentLinkedQueue<Process> processQueue;
                protected int completedProcesses = 0;
            
                public Scheduler(ConcurrentLinkedQueue<Process> processQueue) {
                    this.processQueue = processQueue;
                }
            
                public abstract void startSimulation();
            
                public void displayStatus() {
                    System.out.println("Cola de procesos:");
                    for (Process process : processQueue) {
                        System.out.printf("Proceso %d | Tipo: %s | Tiempo de servicio: %d ms\n", process.getVar("pid"), process.getType(), process.getVar("st"));
                    }
                    System.out.printf("Procesos completados: %d\n", completedProcesses);
                }
            }
            
            class FCFScheduler extends Scheduler {
                public FCFScheduler(ConcurrentLinkedQueue<Process> processQueue) {
                    super(processQueue);
                }
            
                @Override
                public void startSimulation() {
                    Process previous = null;
                    while (!processQueue.isEmpty()) {
                        Process current = processQueue.poll();
                        int completionTime = (previous == null) ? current.getVar("at") + current.getVar("st")
                                : Math.max(previous.getVar("ct"), current.getVar("at")) + current.getVar("st");
            
                        current.setVar("ct", completionTime);
                        current.updateAfterCt();
                        current.display();
                        current.processService();
                        previous = current;
                        completedProcesses++;
                    }
                }
            
            
                public static void main(String[] args) {
                    if (args.length < 6) {
                        System.out.println("Uso incorrecto. Ejemplo:");
                        System.out.println("java ProcessScheduler -fcfs 1.5-3 2 1 2.5 3");
                        return;
                    }
            
                    String policy = args[0];
                    String timeRange = args[1];
                    int arithmeticTime = Integer.parseInt(args[2]);
                    int ioTime = Integer.parseInt(args[3]);
                    int conditionalTime = Integer.parseInt(args[4]);
                    int iterativeTime = Integer.parseInt(args[5]);
            
                    ConcurrentLinkedQueue<Process> processQueue = generateProcesses(10, timeRange, arithmeticTime, ioTime, conditionalTime, iterativeTime);
            
                    Scheduler scheduler;
                    switch (policy) {
                        case "-fcfs" -> scheduler = new FCFScheduler(processQueue);
                        // Puedes agregar otras políticas aquí, como LCFS, RR, o Priority
                        default -> throw new IllegalArgumentException("Política no válida: " + policy);
                    }
            
                    System.out.println("Ejecutando simulación con política " + policy);
                    scheduler.startSimulation();
                    System.out.printf("Promedio de tiempo de espera: %.2f ms\n", average(processQueue, "wt"));
                }
            
                private static ConcurrentLinkedQueue<Process> generateProcesses(int n, String timeRange, int arithmeticTime, int ioTime, int conditionalTime, int iterativeTime) {
                    ConcurrentLinkedQueue<Process> processList = new ConcurrentLinkedQueue<>();
                    Random rand = new Random();
                    int arrivalTime = 0;
            
                    for (int i = 1; i <= n; i++) {
                        String type;
                        int serviceTime;
                        switch (rand.nextInt(4)) {
                            case 0 -> {
                                type = "Arithmetic";
                                serviceTime = arithmeticTime;
                            }
                            case 1 -> {
                                type = "I/O";
                                serviceTime = ioTime;
                            }
                            case 2 -> {
                                type = "Conditional";
                                serviceTime = conditionalTime;
                            }
                            default -> {
                                type = "Iterative";
                                serviceTime = iterativeTime;
                            }
                        }
            
                        arrivalTime += rand.nextInt(100); // Incremento aleatorio para el tiempo de llegada
                        processList.add(new Process(i, arrivalTime, serviceTime, type));
                    }
            
                    return processList;
                }
            
                private static float average(ConcurrentLinkedQueue<Process> processQueue, String var) {
                    int total = 0;
                    int count = processQueue.size();
            
                    for (Process process : processQueue) {
                        total += process.getVar(var);
                    }
            
                    return (count == 0) ? 0 : (float) total / count;
                }
            }
            