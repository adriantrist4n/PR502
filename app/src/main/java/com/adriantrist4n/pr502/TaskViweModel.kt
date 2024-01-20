package com.adriantrist4n.pr502;

import androidx.lifecycle.ViewModel;
import androidx.compose.runtime.mutableStateListOf;

/**
 * Clase que representa una tarea con un identificador y una descripción.
 */
data class Task(val id: Int, var description: String);

/**
 * ViewModel para manejar una lista de tareas.
 *
 * Esta clase permite la creación, modificación y eliminación de tareas
 * en una lista mutable. Cada tarea es representada por la clase Task.
 */
class TaskViewModel : ViewModel() {
    // Lista mutable de tareas
    var tasks = mutableStateListOf<Task>();

    // Variable para asignar un ID único a cada nueva tarea
    private var nextTaskId = 1;

    /**
     * Crea una nueva tarea y la añade a la lista de tareas.
     *
     * @param description La descripción de la nueva tarea.
     */
    fun createTask(description: String) {
        tasks.add(Task(nextTaskId++, description));
    }

    /**
     * Edita la descripción de una tarea existente.
     *
     * @param taskId El identificador de la tarea a editar.
     * @param newDescription La nueva descripción de la tarea.
     */
    fun editTask(taskId: Int, newDescription: String) {
        tasks.find { it.id == taskId }?.description = newDescription;
    }

    /**
     * Elimina todas las tareas de la lista.
     */
    fun deleteAllTasks() {
        tasks.clear();
    }
}
