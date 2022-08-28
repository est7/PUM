package pl.udu.uwr.pum.notyjava.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import pl.udu.uwr.pum.notyjava.R;
import pl.udu.uwr.pum.notyjava.data.DataProvider;
import pl.udu.uwr.pum.notyjava.db.DBHandler;
import pl.udu.uwr.pum.notyjava.model.NoteModel;

public class NotyWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NotyWidgetItemFactory(getApplicationContext(), intent);
    }

    static class NotyWidgetItemFactory implements RemoteViewsFactory {

        private final Context context;
        private final int appWidgetId;
        private ArrayList<NoteModel> noteList;
        private DBHandler dbHandler;

        public NotyWidgetItemFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            // otworz baze danych
            //noteList = DataProvider.dummyData2;
            dbHandler = new DBHandler(context);
            noteList = dbHandler.getNotes();
        }

        @Override
        public void onDataSetChanged() {
            noteList = dbHandler.getNotes();
        }

        @Override
        public void onDestroy() {
            dbHandler.close();
        }

        @Override
        public int getCount() {
            return noteList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_list);
            remoteViews.setTextViewText(R.id.itemListTextView, noteList.get(position).getTime().toString()
                    + "\n" + noteList.get(position).getTextNote());
            remoteViews.setTextColor(R.id.itemListTextView,  noteList.get(position).getColor());
            Log.d("data3", String.valueOf(noteList.get(position).getColor()));

            Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            fillIntent.putExtra("id", noteList.get(position).getId());
            remoteViews.setOnClickFillInIntent(R.id.itemListTextView, fillIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return noteList.get(position).getId(); // identyfikacja
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
