package hu.bme.aut.android.todo.model
import java.io.Serializable
import android.arch.persistence.room.*

@Entity(tableName = "City")
data class City(
        @PrimaryKey(autoGenerate = true)
        var Id: Long?,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "sight1")
        var sight1: String,
        @ColumnInfo(name = "sight2")
        var sight2: String,
        @ColumnInfo(name = "sight3")
        var sight3: String,
        @ColumnInfo(name = "description")
        var description: String
) : Serializable



