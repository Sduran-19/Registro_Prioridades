package  edu.ucne.registro_prioridades.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import  edu.ucne.registro_prioridades.data.local.dao.PrioridadDao
import edu.ucne.registro_prioridades.data.local.dao.TicketDao
import edu.ucne.registro_prioridades.data.local.entities.PrioridadEntity
import edu.ucne.registro_prioridades.data.local.entities.TicketEntity


@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],

    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao() : PrioridadDao
    abstract fun ticketDao() : TicketDao

}