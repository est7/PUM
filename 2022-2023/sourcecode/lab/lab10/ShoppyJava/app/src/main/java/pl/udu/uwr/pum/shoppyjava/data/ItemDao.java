package pl.udu.uwr.pum.shoppyjava.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.udu.uwr.pum.shoppyjava.model.Item;

@Dao
public interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addItem(Item item);

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    LiveData<List<Item>> readAllData();

    @Query("SELECT * FROM item_table WHERE id = :id")
    LiveData<Item> getItem(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * FROM item_table WHERE name LIKE :query")
    LiveData<List<Item>> searchItem(String query);
}
