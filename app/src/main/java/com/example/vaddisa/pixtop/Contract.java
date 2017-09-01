package com.example.vaddisa.pixtop;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sarathreddyvaddhi on 9/1/17.
 */

public class Contract {

    public static final String AUTHORITY = "com.udacity.stockhawk";

    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_QUOTE = "quote";
    public static final String PATH_QUOTE_WITH_SYMBOL = "quote/*";

    public static final class Quote implements BaseColumns {

        public static final Uri uri = BASE_URI.buildUpon().appendPath(PATH_QUOTE).build();

        public static final String TABLE_NAME = "quotes";

        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ABSOLUTE_CHANGE = "absolute_change";
        public static final String COLUMN_PERCENTAGE_CHANGE = "percentage_change";
        public static final String COLUMN_HISTORY = "history";
        public static final String COLUMN_ASK = "ask";
        public static final String COLUMN_ASK_SIZE = "askSize";
        public static final String COLUMN_BID = "bid";
        public static final String COLUMN_BID_SIZE = "bidSize";
        public static final String COLUMN_LAST_TRADE_SIZE = "lastTradeSize";
        public static final String COLUMN_LAST_TRADE_DATE_STR = "lastTradeDateStr";
        public static final String COLUMN_LAST_TRADE_TIME_STR = "lastTradeTimeStr";


        public static final int POSITION_ID = 0;
        public static final int POSITION_SYMBOL = 1;
        public static final int POSITION_PRICE = 2;
        public static final int POSITION_ABSOLUTE_CHANGE = 3;
        public static final int POSITION_PERCENTAGE_CHANGE = 4;
        public static final int POSITION_HISTORY = 5;
        public static final int POSITION_ASK = 6;
        public static final int POSITION_ASK_SIZE = 7;
        public static final int POSITION_BID = 8;
        public static final int POSITION_BID_SIZE = 9;
        public static final int POSITION_LAST_TRADE_SIZE = 10;
        public static final int POSITION_LAST_TRADE_DATE_STR = 11;
        public static final int POSITION_LAST_TRADE_TIME_STR = 12;
        public static final int POSITION_LAST_TRADE_TIME = 13;


        public static final String[] QUOTE_COLUMNS = {
                _ID,
                COLUMN_SYMBOL,
                COLUMN_PRICE,
                COLUMN_ABSOLUTE_CHANGE,
                COLUMN_PERCENTAGE_CHANGE,
                COLUMN_HISTORY,
                COLUMN_ASK,
                COLUMN_ASK_SIZE,
                COLUMN_BID,
                COLUMN_BID_SIZE,
                COLUMN_LAST_TRADE_SIZE,
                COLUMN_LAST_TRADE_DATE_STR,
                COLUMN_LAST_TRADE_TIME_STR
        };

        public static Uri makeUriForStock(String symbol) {
            return uri.buildUpon().appendPath(symbol).build();
        }

        public static String getStockFromUri(Uri uri) {
            return uri.getLastPathSegment();
        }


    }

}
