package pl.udu.uwr.pum.shoppykotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.udu.uwr.pum.shoppykotlin.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: Item)

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE id = :id")
    fun getItem(id: Int): LiveData<Item>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAllItems()

    @Query("SELECT * FROM item_table WHERE name LIKE :query")
    fun search(query: String): LiveData<List<Item>>
}