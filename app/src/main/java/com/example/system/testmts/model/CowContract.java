package com.example.system.testmts.model;

import android.provider.BaseColumns;

public class CowContract {

    public CowContract() {
    }

    public static final class CowEntry implements BaseColumns {

        public static final String TABLE_NAME = "cows";

        public static final String COLUMN_COW_BREED = "breed";
        public static final String COLUMN_COW_SUIT = "suit";
        public static final String COLUMN_COW_AGE= "age";
    }
}
