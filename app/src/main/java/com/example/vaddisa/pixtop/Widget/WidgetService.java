package com.example.vaddisa.pixtop.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by sarathreddyvaddhi on 9/1/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewsFactory(this.getApplicationContext(), intent);
    }

}
