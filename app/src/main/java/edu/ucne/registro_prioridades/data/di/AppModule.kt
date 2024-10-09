package edu.ucne.registro_prioridades.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.composeregistro_prioridades.data.remote.api.PrioridadesApi
import edu.ucne.composeregistro_prioridades.data.remote.api.SistemasApi
import  edu.ucne.composeregistro_prioridades.data.remote.api.ClientesApi
import edu.ucne.composeregistro_prioridades.data.remote.api.TicketsApi
import edu.ucne.registro_prioridades.data.local.database.DateAdapter
import edu.ucne.registro_prioridades.data.local.database.PrioridadDb
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    const val BASE_URL = "https://rdetalleapi-fxc8gdfbg4e7bqht.canadacentral-01.azurewebsites.net/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()

    @Provides
    @Singleton
    fun providesPrioridadesApi(moshi: Moshi): PrioridadesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemasApi(moshi: Moshi): SistemasApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemasApi::class.java)
    }

    @Provides
    @Singleton
    fun providesClientesApi(moshi: Moshi): ClientesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ClientesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTicketsApi(moshi: Moshi): TicketsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketsApi::class.java)
    }


    @Provides
    @Singleton
    fun ProvidedTicketDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            PrioridadDb::class.java,
            "TicketDb"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTicketDao(db: PrioridadDb) = db.ticketDao()

    @Provides
    fun providePrioridadDao(db: PrioridadDb) = db.prioridadDao()
}