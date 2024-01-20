package com.adriantrist4n.pr502.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adriantrist4n.pr502.Task
import com.adriantrist4n.pr502.TaskViewModel
import com.adriantrist4n.pr502.ui.theme.BackgroundScreen
import com.adriantrist4n.pr502.ui.theme.TitleScaffold

/**
 * Pantalla que muestra y gestiona una lista de tareas.
 *
 * Esta pantalla proporciona funcionalidades para crear, editar y borrar tareas.
 * Utiliza el ViewModel [TaskViewModel] para gestionar el estado y los datos.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ListaScreen(taskViewModel: TaskViewModel = viewModel()) {
    // Configuración y diseño de la pantalla
    Box(
        modifier = Modifier
            .background(color = BackgroundScreen)
            .padding(top = 70.dp, bottom = 60.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Variables para controlar la visibilidad de diálogos y menús
            var showMenu by remember { mutableStateOf(false) }
            var showCreateDialog by remember { mutableStateOf(false) }
            var showDeleteConfirmDialog by remember { mutableStateOf(false) }
            var showEditDialog by remember { mutableStateOf(false) }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Task Manager", style = MaterialTheme.typography.h5, color = TitleScaffold)
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = TitleScaffold)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        showCreateDialog = true
                        showMenu = false
                    }) {
                        Text("Crear una nueva tarea")
                    }
                    DropdownMenuItem(onClick = {
                        showEditDialog = true
                        showMenu = false
                    }) {
                        Text("Editar una tarea")
                    }
                    DropdownMenuItem(onClick = {
                        showDeleteConfirmDialog = true
                        showMenu = false
                    }) {
                        Text("Borrar todas las tareas")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra la lista de tareas
            TaskList(taskViewModel.tasks)

            if (showCreateDialog) {
                TaskInputDialog(
                    title = "Crear Tarea \n",
                    onConfirm = { description ->
                        taskViewModel.createTask(description)
                        showCreateDialog = false
                    },
                    onDismiss = { showCreateDialog = false }
                )
            }

            if (showDeleteConfirmDialog) {
                ConfirmDeleteDialog(
                    onConfirm = {
                        taskViewModel.deleteAllTasks()
                        showDeleteConfirmDialog = false
                    },
                    onDismiss = { showDeleteConfirmDialog = false }
                )
            }

            if (showEditDialog) {
                var taskId by remember { mutableStateOf("") }
                var taskDescription by remember { mutableStateOf("") }

                AlertDialog(
                    onDismissRequest = { showEditDialog = false },
                    title = { Text("Editar Tarea") },
                    text = {
                        Column {
                            TextField(
                                value = taskId,
                                onValueChange = { taskId = it },
                                label = { Text("ID de la Tarea") }
                            )
                            TextField(
                                value = taskDescription,
                                onValueChange = { taskDescription = it },
                                label = { Text("Nueva Descripción") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            taskId.toIntOrNull()?.let { id ->
                                taskViewModel.editTask(id, taskDescription)
                            }
                            showEditDialog = false
                        }) {
                            Text("Guardar Cambios")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showEditDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

        }
    }
}

/**
 * Composable que muestra una lista de tareas.
 *
 * @param tasks Lista de tareas a mostrar.
 */
@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn {
        items(tasks) { task ->
            Text(task.description, color = TitleScaffold)
            Divider()
        }
    }
}

/**
 * Composable que muestra un diálogo para introducir una tarea.
 *
 * @param title Título del diálogo.
 * @param onConfirm Acción a realizar al confirmar.
 * @param onDismiss Acción a realizar al cancelar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputDialog(title: String, onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Descripción") }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(text) }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

/**
 * Composable que muestra un diálogo de confirmación para borrar tareas.
 *
 * @param onConfirm Acción a realizar al confirmar.
 * @param onDismiss Acción a realizar al cancelar.
 */
@Composable
fun ConfirmDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar") },
        text = { Text("¿Estás seguro de que quieres borrar todas las tareas?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
