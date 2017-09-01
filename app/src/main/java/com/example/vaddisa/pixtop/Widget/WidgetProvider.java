package com.example.vaddisa.pixtop.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vaddisa.pixtop.R;
import com.example.vaddisa.pixtop.Views.MainActivity;

/**
 * Created by sarathreddyvaddhi on 9/1/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    private String TAG = WidgetProvider.class.getSimpleName();

    public static final String WIDGET_ID_KEY = "WidgetId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive()");
        if (intent.hasExtra(WIDGET_ID_KEY)) {
            int[] ids = intent.getExtras().getIntArray(WIDGET_ID_KEY);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
            createRefreshIntent(context,ids);
        } else
            super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate()");

        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_list_layout);

            remoteViews.setRemoteAdapter(R.id.listViewWidget, intent);
            remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                    clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.listViewWidget, clickPI);
            remoteViews.setOnClickPendingIntent(R.id.empty_view, clickPI);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public PendingIntent createRefreshIntent(Context context, int[] appWidgetIds) {

        Intent updateIntent = new Intent(context, WidgetProvider.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(WidgetProvider.WIDGET_ID_KEY, appWidgetIds);

        PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                updateIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return clickPI;
    }
}

