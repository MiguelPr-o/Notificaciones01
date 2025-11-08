package mx.edu.utng.mahg.notificaciones01

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import mx.edu.utng.mahg.notificaciones01.utils.AppNotificationManager
import mx.edu.utng.mahg.notificaciones01.utils.NotificationHelper

/**
 * 🎬 MainActivity - La Estrella del Show
 *
 * Esta es la pantalla principal donde todo sucede.
 * Piensa en ella como el escenario de un teatro 🎭
 */
class MainActivity : ComponentActivity() {

    // 📱 Lanzador de permisos - Nuestro "portero de seguridad"
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            println("✅ ¡Permiso concedido! Ahora podemos notificar")
        } else {
            println("❌ Permiso denegado. El usuario no quiere notificaciones")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 📡 Crear el canal de notificaciones (solo se hace una vez)
        NotificationHelper.createNotificationChannel(this)

        // 🔐 Pedir permiso si es necesario
        checkAndRequestNotificationPermission()

        // 🎨 Configurar la interfaz con Jetpack Compose
        setContent {
            NotificationAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationScreen()
                }
            }
        }
    }

    /**
     * 🔐 Verifica y solicita el permiso de notificaciones
     *
     * Es como pedirle permiso a tu profesor para ir al baño:
     * Si ya te lo dio, entras. Si no, tienes que preguntar.
     */
    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    println("✅ Ya tenemos permiso para notificar")
                }
                else -> {
                    // Pedir permiso al usuario
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    /**
     * 🎨 Pantalla principal de la app
     *
     * Aquí es donde creamos la interfaz visual usando Composables
     * (funciones que dibujan la pantalla)
     */
    @Composable
    fun NotificationScreen() {
        // 🎯 Estado para contar notificaciones enviadas
        var notificationCount by remember { mutableStateOf(0) }

        // 📬 Instancia de nuestro gestor de notificaciones
        val notificationManager = remember { AppNotificationManager(this) }

        // 📦 Contenedor principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 🎭 Título principal
            Text(
                text = "🧠 Notificaciones Inteligentes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 📝 Descripción
            Text(
                text = "¡Envía recordatorios motivadores para no olvidar tus estudios! 📚✨",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // 🎲 Botón principal - ¡EL BOTÓN MÁGICO!
            Button(
                onClick = {
                    // Obtener mensaje aleatorio
                    val (title, message) = notificationManager.getRandomMotivationalMessage()

                    // Enviar notificación
                    notificationManager.sendMotivationalNotification(
                        title = title,
                        message = message
                    )

                    // Incrementar contador
                    notificationCount++
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "🚀 Enviar Notificación Motivadora",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📊 Contador de notificaciones
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Notificaciones Enviadas",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$notificationCount",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 💡 Consejo adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💡",
                        fontSize = 32.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "Tip: Puedes personalizar los mensajes en NotificationManager.kt",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

/**
 * 🎨 Tema de la aplicación
 *
 * Define los colores y estilos generales de la app
 */
@Composable
fun NotificationAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
            secondary = androidx.compose.ui.graphics.Color(0xFF03DAC6),
            tertiary = androidx.compose.ui.graphics.Color(0xFFFF9800)
        ),
        content = content
    )
}
