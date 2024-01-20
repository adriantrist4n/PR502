package com.adriantrist4n.pr502.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriantrist4n.pr502.ui.theme.ColorScaffold
import com.adriantrist4n.pr502.ui.theme.TitleScaffold


/**
 * Componente usando para mostrar una fila personalizada que se puede expandir o contraer.
 *
 * Esta Composable muestra un texto y un botón que permite expandir o contraer contenido adicional.
 * Cuando se expande, muestra el contenido proporcionado en el parámetro `content`.
 *
 * @param text El texto que se muestra en la fila. Representa el título o la descripción del contenido expandible.
 * @param content Un Composable que se muestra cuando la fila está expandida. Este es el contenido detallado asociado con el texto proporcionado.
 */
@Composable
fun CustomRow(text: String, content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) } // Variable para recordar el estado

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(
                text = text,
                color = TitleScaffold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    // Mostramos el icono correspodiente dependiendo del estado
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Contraer" else "Desplegar",
                    tint = TitleScaffold
                )
            }
        }

        if (isExpanded) {
            content() // Contenido @Composable que se la pasa a la funcion como parametro
        }

        Divider(modifier = Modifier.height(1.dp), color = ColorScaffold)
    }
}