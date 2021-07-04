package Recomendaciones;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskService {

    //Programador de tareas
    TaskScheduler scheduler;

    // Un map para tener las tareas programadas
    Map<Integer, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public ScheduleTaskService(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    // Tarea programada para que se ejecute cada 7 dias
    public void addTaskToScheduler(int id, Runnable tarea) {
        ScheduledFuture<?> scheduledTask = scheduler.schedule(tarea, new PeriodicTrigger(7, TimeUnit.DAYS));
        jobsMap.put(id, scheduledTask);
    }

    // Remover tarea programada
    public void removeTaskFromScheduler(int id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(id, null);
        }
    }
}
