package ru.castprograms.hackeducation

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.ui.main.top.TopViewModel
import ru.castprograms.hackeducation.ui.start.authorization.AuthorizationViewModel
import ru.castprograms.hackeducation.ui.start.registration.RegistrationViewModel
import ru.castprograms.hackeducation.ui.start.splash.SplashViewModel

class HackApplication : Application() {
    private val module = module {
        single {
            Firebase.firestore.apply {
                this.firestoreSettings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()
            }
        }
        single { DataUserFirebase(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { AuthorizationViewModel(get()) }
        viewModel { MainViewModel(get()) }
        viewModel { RegistrationViewModel(get()) }
        viewModel { TopViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@HackApplication)
            modules(module)
        }
    }
}