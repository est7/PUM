package pl.udu.uwr.pum.shoppyjava.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private final ItemDao itemDao;
    private final LiveData<List<Item>> readAllData;

    public ItemRepository(Application application){
        ItemDatabase db = ItemDatabase.getDatabase(application);
        itemDao = db.itemDao();
        readAllData = itemDao.readAllData();
    }

    public void insert(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> itemDao.addItem(item));
    }

    public LiveData<List<Item>> ReadAllData() {
        return readAllData;
    }
}