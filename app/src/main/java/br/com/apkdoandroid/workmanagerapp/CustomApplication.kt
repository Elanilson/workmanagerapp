package br.com.apkdoandroid.workmanagerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import br.com.apkdoandroid.workmanagerapp.constantes.Constantes

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        exibirNotificacao(applicationContext)
    }

    fun exibirNotificacao(context: Context){
        val idCanal = Constantes.ID_CANAL
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val canal = NotificationChannel(idCanal,Constantes.NOME_CANAL, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(canal)
        }

     /*   val notificacaoBuilder = NotificationCompat.Builder(context,idCanal).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setShowWhen(true)
            setContentTitle("Lembrete")
            setContentText("Lembre-se de fazer algo")
        }*/

      //  notificationManager.notify(1,notificacaoBuilder.build())
    }
}