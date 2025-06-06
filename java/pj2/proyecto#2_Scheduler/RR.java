/* RoundRobin.java */
import processing.Process;
import processing.Scheduler;

import java.util.Random;


class RRScheduler extends Scheduler {
    private float quantum;
    private int completedProcesses = 0;

    public RRScheduler(boolean dualMode, String timeRange, float arithmeticTime, float ioTime, float conditionalTime, float loopTime, float quantum) {
        super(dualMode, timeRange, arithmeticTime, ioTime, conditionalTime, loopTime);
        this.quantum = quantum;
    }

    @Override
    public void startSimulation() {
        System.out.println("Iniciando simulación con política Round Robin (RR) y quantum de " + quantum + " segundos");

        // Crear procesos con tiempos y tipos específicos
        generateProcesses();

        // Simulación Round Robin usando tiempo restante dentro del proceso
        while (!processQueue.isEmpty()) {
            Process process = processQueue.poll(); // Obtiene el primer proceso en la cola

            if (process != null) {
                System.out.printf("Procesando proceso ID %d de tipo %s\n", process.getProcessId(), process.getType());

                // Procesar el proceso según el quantum
                try {
                    // Calcula el tiempo de procesamiento para el quantum
                    float remainingTime = process.getServiceTime();
                    float processingTime = Math.min(remainingTime, quantum);
                    Thread.sleep((long) (processingTime * 1000));

                    // Actualizar el tiempo restante del proceso
                    process.setServiceTime(remainingTime - processingTime);

                    // Verificar si el proceso está completo
                    if (process.getServiceTime() > 0) {
                        System.out.printf("Proceso ID %d no completado, tiempo restante: %.2f segundos\n", process.getProcessId(), process.getServiceTime());
                        // En lugar de volver a agregarlo a la cola, lo mantenemos en un estado procesable
                        processQueue.add(process);
                    } else {
                        System.out.printf("Proceso ID %d completado.\n", process.getProcessId());
                        completedProcesses++;
                    }
                } catch (InterruptedException e) {
                    System.err.println("Error procesando el proceso: " + e.getMessage());
                }
            }

            // Mostrar el estado actual de la cola
            displayStatus();
        }

        // Finalizar la simulación
        endSimulation();
        System.out.println("Número total de procesos completados: " + completedProcesses);
    }

    private void generateProcesses() {
        // Generar procesos con tiempos específicos según los parámetros
        System.out.println("Generando procesos para Round Robin...");
        String[] range = timeRange.split("-");
        float rangeStart = Float.parseFloat(range[0]);
        float rangeEnd = Float.parseFloat(range[1]);
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            float serviceTime = rangeStart + random.nextFloat() * (rangeEnd - rangeStart);
            String type = switch (random.nextInt(4)) {
                case 0 -> "A"; // Aritmético
                case 1 -> "IO"; // Entrada/Salida
                case 2 -> "C"; // Condicional
                case 3 -> "L"; // Iterativo
                default -> "U"; // Desconocido
            };
            Process process = new Process(i, type, i, i, serviceTime, serviceTime);
            process.setServiceTime(serviceTime);
            processQueue.add(process);
        }

        System.out.println("Procesos generados.");
    }

    private void displayStatus() {
        System.out.println("Estado actual de la cola de procesos:");
        for (Process process : processQueue) {
            System.out.printf("Proceso ID %d, Tipo %s, Tiempo restante: %.2f segundos\n", 
                              process.getProcessId(), process.getType(), process.getServiceTime());
        }
    }
}

