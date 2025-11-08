package mx.edu.utng.mahg.notificaciones01.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import mx.edu.utng.mahg.notificaciones01.MainActivity
import mx.edu.utng.mahg.notificaciones01.R

/**
 * 🚀 NotificationManager - El Cartero Digital
 *
 * Esta clase se encarga de ENVIAR las notificaciones.
 * Es como el cartero que lleva tu correspondencia, pero instantáneamente! 📮
 */
class AppNotificationManager(private val context: Context) {

    /**
     * 💌 Envía una notificación motivadora
     *
     * @param title El título - debe ser LLAMATIVO (como el asunto de un email)
     * @param message El mensaje - aquí va la magia ✨
     * @param priority Qué tan importante es (bajo, medio, alto)
     */
    fun sendMotivationalNotification(
        title: String,
        message: String,
        priority: Int = NotificationCompat.PRIORITY_HIGH
    ) {
        // 🔐 Verificar permisos - ¡No podemos tocar puertas sin permiso!
        if (!checkNotificationPermission()) {
            println("⚠️ No hay permiso para enviar notificaciones")
            return
        }

        // 🎯 Intent - Qué pasa cuando el usuario toca la notificación
        // Es como decir: "Si tocas esto, te llevo a la pantalla principal"
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 🎨 Construyendo la notificación - ¡Como decorar un pastel!
        val notification = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // Ícono pequeño - tu "logo"
            .setContentTitle(title)                    // Título en NEGRITA
            .setContentText(message)                   // El mensaje principal
            .setPriority(priority)                     // Qué tan urgente es
            .setAutoCancel(true)                      // Se borra al tocarla
            .setContentIntent(pendingIntent)          // Acción al tocar
            .setStyle(
                // 📖 Estilo expandible - ¡Para mensajes largos!
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Visible en pantalla bloqueada
            .build()

        // 📬 Enviando la notificación al mundo
        try {
            NotificationManagerCompat.from(context).notify(
                NotificationHelper.NOTIFICATION_ID,
                notification
            )
            println("✅ ¡Notificación enviada exitosamente!")
        } catch (e: SecurityException) {
            println("❌ Error: No se pudo enviar la notificación - ${e.message}")
        }
    }

    /**
     * 🔍 Verifica si tenemos permiso para notificar
     *
     * En Android 13+, necesitamos pedir permiso explícito.
     * Es como pedir permiso para usar el carro de tus papás 🚗
     */
    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // En versiones antiguas, el permiso se otorga automáticamente
            true
        }
    }

    /**
     * 🎲 Generador de mensajes motivadores aleatorios
     *
     * ¡Variedad es la sal de la vida! Cada notificación será diferente
     */
    fun getRandomMotivationalMessage(): Pair<String, String> {
        val messages = listOf(
            "¡Hora de brillar! 🌟" to "Tu cerebro está listo para absorber conocimiento. ¡Dale una oportunidad! 🧠📚",
            "¿Olvidaste tu estudio? 🤔" to "¡Tu cerebro te está pidiendo ayuda! No lo dejes esperando 🧠📖",
            "¡Pausa para el éxito! ⏸️" to "Unos minutos de estudio hoy = Un futuro brillante mañana ✨",
            "¡Alerta de genio! 🚨" to "Tu yo del futuro te agradecerá este momento de estudio 🙏📘",
            "Momento de superación 🦸‍♂️" to "Cada página que lees te acerca más a tus metas. ¡Vamos! 🎯",
            "¡Tu mente tiene hambre! 🍔" to "Aliméntala con algo de conocimiento delicioso 🧠🍎",
            "Checkpoint alcanzado 🏁" to "¡Es hora de subir de nivel! Abre ese libro y evoluciona 📚⬆️",
            "Notificación épica ⚔️" to "Los héroes también estudian. ¡Demuestra tu valentía! 🛡️📖"
        )

        return messages.random()
    }
}
