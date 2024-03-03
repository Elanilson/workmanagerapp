package br.com.apkdoandroid.workmanagerapp

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import br.com.apkdoandroid.workmanagerapp.databinding.ActivityMainBinding
import br.com.apkdoandroid.workmanagerapp.work.MeuWork
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        solicitarPermissao()

        //val ontimeWorkerRequest = OneTimeWorkRequestBuilder<MeuWork>()
          //  .setInputData(workDataOf("nome" to "Elanilson", "idade" to 28))
          /*  .addTag("Trabalho")
            .setConstraints(Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresCharging(false) // padrao já
                .build())*/
         //   .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<MeuWork>(
            15,TimeUnit.MINUTES
        ).setInitialDelay(20,TimeUnit.SECONDS)
            .build()
        val workManager = WorkManager.getInstance(applicationContext)

        binding.buttonExecutar.setOnClickListener {

            workManager.enqueue(periodicWorkRequest)
           /* workManager.enqueueUniqueWork(
                "Trabal",
                ExistingWorkPolicy.KEEP,
                ontimeWorkerRequest
            )*/
        }
        workManager
            .getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this){workInfo ->
             //   Log.d("workmanage_android","Executando ${workInfo}")
                if(workInfo != null){
                    val progresso = workInfo.progress.getInt("progresso",0);
                    Log.d("workmanage_android","Executando ${progresso}")

                }
            }
        binding.buttonCancelar.setOnClickListener {

            workManager.cancelWorkById(periodicWorkRequest.id)
          //  workManager.cancelAllWorkByTag("Trabalho")
          //  workManager.cancelUniqueWork("Trabal")
          //  workManager.cancelAllWork()
        }
    }

    fun solicitarPermissao(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val permissaoNotificacao = ActivityCompat.checkSelfPermission(
                this,android.Manifest.permission.POST_NOTIFICATIONS
            )
            if(permissaoNotificacao == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),100)

            }

        }
    }
}