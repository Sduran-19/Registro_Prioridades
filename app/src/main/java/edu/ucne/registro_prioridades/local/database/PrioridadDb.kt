package  edu.ucne.registro_prioridades.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registro_prioridades.Data.dao.converters.Converters
import  edu.ucne.registro_prioridades.local.dao.PrioridadDao
import edu.ucne.registro_prioridades.local.dao.TicketDao
import edu.ucne.registro_prioridades.local.entities.PrioridadEntity
import edu.ucne.registro_prioridades.local.entities.TicketEntity


@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PrioridadDb: RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}