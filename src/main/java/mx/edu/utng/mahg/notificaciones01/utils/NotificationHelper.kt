package mx.edu.utng.mahg.notificaciones01.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * 🎪 NotificationHelper - El Maestro de Ceremonias de tus Notificaciones
 *
 * Esta clase es como el director de una orquesta: coordina todo para que
 * las notificaciones suenen (y se vean) perfectas! 🎵
 */
object NotificationHelper {

    // Constantes - Como las "etiquetas" de tus cajas organizadoras 📦
    const val CHANNEL_ID = "estudio_recordatorios_channel"
    const val CHANNEL_NAME = "Recordatorios de Estudio"
    const val CHANNEL_DESCRIPTION = "Notificaciones motivadoras para no olvidar tus estudios 📚"
    const val NOTIFICATION_ID = 1001

    /**
     * 📡 Crea el canal de notificaciones
     *
     * Piensa en esto como crear tu propia estación de radio.
     * Solo necesitas hacerlo UNA vez (como registrar tu estación ante las autoridades)
     *
     * @param context El contexto de tu app (como tu "dirección" dentro de Android)
     */
    fun createNotificationChannel(context: Context) {
        // Solo Android 8.0+ necesita canales (como solo autos nuevos tienen Bluetooth)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Definimos la IMPORTANCIA - ¿Es una emergencia o un recordatorio gentil?
            val importance = NotificationManager.IMPORTANCE_HIGH

            // Creamos el canal con sus características
            val channel = NotificationChannel(
                CHANNEL_ID,           // ID único - como tu número de teléfono
                CHANNEL_NAME,         // Nombre visible - como tu nombre artístico
                importance            // Qué tan "ruidoso" será
            ).apply {
                description = CHANNEL_DESCRIPTION

                // Configuraciones adicionales - ¡el toque especial! ✨
                enableLights(true)    // LED de notificación (si el dispositivo lo tiene)
                enableVibration(true) // Vibración - ¡bzz bzz! 📳
                setShowBadge(true)    // Muestra el "numerito" en el ícono de la app
            }

            // Registramos nuestro canal en el sistema
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            println("✅ Canal de notificaciones creado exitosamente!")
        }
    }
}
