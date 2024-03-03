package br.com.apkdoandroid.workmanagerapp.work

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import br.com.apkdoandroid.workmanagerapp.R
import br.com.apkdoandroid.workmanagerapp.constantes.Constantes
import kotlinx.coroutines.delay

class MeuWork(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context,workerParameters) {

     override suspend fun doWork(): Result {
         executarAcao()
        return Result.success(workDataOf("retorno" to 200))
     }

     private suspend fun executarAcao(){
         setForeground(ForegroundInfo(
             System.currentTimeMillis().toInt(),
             NotificationCompat.Builder(applicationContext,Constantes.ID_CANAL)
                 .setSmallIcon(R.drawable.ic_launcher_foreground)
                 .setShowWhen(true)
                 .setContentTitle("Lembrete")
                 .setContentText("Lembre-se de fazer algo")
                 .build()
         ))

        /* val nome  = workerParameters.inputData.getString("nome")
         val idade  = workerParameters.inputData.getInt("idade",0)
         Log.d("workmanage_android","Nome: ${nome} , idade: ${idade}")*/

         setProgress(workDataOf("progresso" to 0))
         repeat(100){contador->
             delay(1000)
             setProgress(workDataOf("progresso" to contador))
            // Log.d("workmanage_android","Executando ${contador}")
         }
     }
 }