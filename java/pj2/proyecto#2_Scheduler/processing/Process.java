/* Process.java */
/**
			 ** Hecho por: Mario  Renato Monroy  Cortez
			 ** Carnet: 23000140
			 ** Seccion: D
			**/	
package processing;
public class Process {
    private int processId;
    private int id;
    private String type;
    private int timeToService;
    private float serviceTime;
    private float remainingTime;

    public void run(){
        System.out.println("Ejecutando el proceso con  ID: "+ this.getProcessId());
        
    }

    public Process(int id, String type,int processId, int timeToService,float serviceTime,float remainingTime) {
        this.id = id;
        this.type = type;
        this.timeToService = timeToService;
        this.processId = processId;
        this.timeToService = timeToService;
        this.serviceTime = serviceTime;
        this.remainingTime = serviceTime;
    
    }
    public void setServiceTime(float serviceTime){
        this.serviceTime = serviceTime;
    }
    public float getServiceTime(){
        return serviceTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getTimeToService() {
        return timeToService;
    }
    public float getRemainingTime(){
        return remainingTime;
    }
    public void reduceRemainingTime(float time){
        this.remainingTime = Math.max(0, this.remainingTime - time);
    }
        
    }