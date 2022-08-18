package com.applicaiton.my_auth.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.applicaiton.my_auth.Model.HeaderModel;
import com.applicaiton.my_auth.Model.LineModel;
import com.applicaiton.my_auth.Model.QueueModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    List<LineModel> linesList = new ArrayList<>();
    DatabaseHelper helper;

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }


    //Insert Lines:
    public long insertHeaders(HeaderModel model) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.intHeaderID, model.getIntHeaderID());
        contentValues.put(DatabaseHelper.strMessage, model.getStrMessage());
        contentValues.put(DatabaseHelper.date, model.getDate());
        contentValues.put(DatabaseHelper.strUserName, model.getStrUserName());
        contentValues.put(DatabaseHelper.strCell, model.getStrCell());
        contentValues.put(DatabaseHelper.strEmail, model.getStrEmail());
        contentValues.put(DatabaseHelper.companyID, model.getCompanyID());
        contentValues.put(DatabaseHelper.groupID, model.getGroupID());

        long id = database.insert(DatabaseHelper.HEADERS_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertLines(LineModel model) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.intAuthLineId, model.getIntAuthLineId());
        contentValues.put(DatabaseHelper.strLineMessage1, model.getStrLineMessage1());
        contentValues.put(DatabaseHelper.strLineMessage2, model.getStrLineMessage2());
        contentValues.put(DatabaseHelper.strLineMessage3, model.getStrLineMessage3());
        contentValues.put(DatabaseHelper.blnIsApproved, model.getBlnIsApproved());
        contentValues.put(DatabaseHelper.intHeaderID, model.getIntHeaderID());

        long id = database.insert(DatabaseHelper.LINES_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertQueue(QueueModel model) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.intAuthLineId, model.getIntAuthLineId());
        contentValues.put(DatabaseHelper.IsAuthorized, model.getIsAuthorized());
        contentValues.put(DatabaseHelper.Instructions, model.getInstructions());

        long id = database.insert(DatabaseHelper.QUEUE_TABLE_NAME, null, contentValues);
        return id;
    }

    public List<HeaderModel> getHeaders() {
        List<HeaderModel> list = new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();

        String[] columns = {DatabaseHelper.UID, DatabaseHelper.intHeaderID,
                DatabaseHelper.strMessage,
                DatabaseHelper.date,
                DatabaseHelper.strUserName,
                DatabaseHelper.strCell,
                DatabaseHelper.strEmail,
                DatabaseHelper.companyID,
                DatabaseHelper.groupID,

        };

        Cursor cursor = database.query(DatabaseHelper.HEADERS_TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HeaderModel model = new HeaderModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
            list.add(model);
        }
        return list;
    }


    public List<LineModel> getLines() {
        List<LineModel> list = new ArrayList<>();
        SQLiteDatabase database = helper.getWritableDatabase();

        String[] columns = {DatabaseHelper.UID, DatabaseHelper.intAuthLineId,
                DatabaseHelper.strLineMessage1,
                DatabaseHelper.strLineMessage2,
                DatabaseHelper.strLineMessage3,
                DatabaseHelper.blnIsApproved,
                DatabaseHelper.intHeaderID

        };

        Cursor cursor = database.query(DatabaseHelper.LINES_TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            LineModel model = new LineModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            list.add(model);
        }
        return list;
    }

    public List<LineModel> getLinesByHeaderId(String headerId) {

        SQLiteDatabase database = helper.getWritableDatabase();


        String selection = DatabaseHelper.intHeaderID + "=?";


        String[] args = {"" + headerId};

        String[] columns = {DatabaseHelper.UID, DatabaseHelper.intAuthLineId,
                DatabaseHelper.strLineMessage1,
                DatabaseHelper.strLineMessage2,
                DatabaseHelper.strLineMessage3,
                DatabaseHelper.blnIsApproved,
                DatabaseHelper.intHeaderID

        };
        Cursor cursor = database.query(DatabaseHelper.LINES_TABLE_NAME, columns, selection, args, null, null, null);
        linesList.clear();
        while (cursor.moveToNext()) {
            LineModel model = new LineModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            linesList.add(model);
        }
        return linesList;

    }

    public long deleteLines(String id) {
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from table_name where id = id
        String selection = DatabaseHelper.intAuthLineId + " LIKE ?";

        String[] args = {"" + id};
        long ids = database.delete(DatabaseHelper.LINES_TABLE_NAME, selection, args);

        return ids;
    }

    //get HEADER by User Name , date, route Name, order types, user id
//    public List<HeadersModel> getHeaderByDateRouteNameOrderTypes(String date, int routeName, int orderTypes, int userId) {
//
//        headerList.clear();
//        SQLiteDatabase database = helper.getWritableDatabase();
//        //select * from tableName where name = ? and customerName = ?:
//        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
////        String selection = DatabaseHelper.UserName + "=?" +
////                " and " + DatabaseHelper.DATE + "=?" +
////                " and " + DatabaseHelper.ROUTE_NAME + "=?" +
////                " and " + DatabaseHelper.ORDER_TYPES + "=?" +
////                " and " + DatabaseHelper.userId + "=?";
//
//        String selection = DatabaseHelper.DATE + "=?" +
//                " and " + DatabaseHelper.ROUTE_NAME + "=?" +
//                " and " + DatabaseHelper.ORDER_TYPES + "=?" +
//                " and " + DatabaseHelper.userId + "=?";
//
//
//        String[] args = {date, "" + routeName, "" + orderTypes, "" + userId};
//        String[] columns = {DatabaseHelper.UID, DatabaseHelper.StoreName, DatabaseHelper.Route, DatabaseHelper.DeliverySequence,
//                DatabaseHelper.Invoiced, DatabaseHelper.InvoiceNo, DatabaseHelper.OrderNo, DatabaseHelper.CustomerPastelCode,
//                DatabaseHelper.CustomerId, DatabaseHelper.MESSAGESINV, DatabaseHelper.UserName, DatabaseHelper.OrderId
//                , DatabaseHelper.strLoadedBy, DatabaseHelper.Loaded, DatabaseHelper.blnPicked, DatabaseHelper.blnPriority, DatabaseHelper.deladdress
//                , DatabaseHelper.Value, DatabaseHelper.OrderDate, DatabaseHelper.condition, DatabaseHelper.strCrateName, DatabaseHelper.DATE
//                , DatabaseHelper.ROUTE_NAME, DatabaseHelper.ORDER_TYPES, DatabaseHelper.userId};
//
//        Cursor cursor = database.query(DatabaseHelper.HEADERS_TABLE_NAME, columns, selection, args, null, null, null);
//        while (cursor.moveToNext()) {
//            HeadersModel model = new HeadersModel(
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getInt(3),
//                    cursor.getInt(4),
//                    cursor.getString(5),
//                    cursor.getString(6),
//                    cursor.getString(7),
//                    cursor.getInt(8),
//                    cursor.getString(9),
//                    cursor.getString(10),
//                    cursor.getInt(11),
//                    cursor.getString(12),
//                    cursor.getInt(13),
//                    cursor.getInt(14),
//                    cursor.getInt(15),
//                    cursor.getString(16),
//                    cursor.getInt(17),
//                    cursor.getString(18),
//                    cursor.getString(19),
//                    cursor.getString(20),
//                    cursor.getString(21),
//                    cursor.getInt(22),
//                    cursor.getInt(23),
//                    cursor.getInt(24)
//            );
//            headerList.add(model);
//        }
//        return headerList;
//
//    }
//
//
//    public List<HeadersModel> getHeadersByLines(int orderId) {
//
//        headerList.clear();
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//
//        String selection = DatabaseHelper.OrderId + "=?";
//
//
//        String[] args = {"" + orderId};
//
//        String[] columns = {DatabaseHelper.UID, DatabaseHelper.StoreName, DatabaseHelper.Route, DatabaseHelper.DeliverySequence,
//                DatabaseHelper.Invoiced, DatabaseHelper.InvoiceNo, DatabaseHelper.OrderNo, DatabaseHelper.CustomerPastelCode,
//                DatabaseHelper.CustomerId, DatabaseHelper.MESSAGESINV, DatabaseHelper.UserName, DatabaseHelper.OrderId
//                , DatabaseHelper.strLoadedBy, DatabaseHelper.Loaded, DatabaseHelper.blnPicked, DatabaseHelper.blnPriority, DatabaseHelper.deladdress
//                , DatabaseHelper.Value, DatabaseHelper.OrderDate, DatabaseHelper.condition, DatabaseHelper.strCrateName, DatabaseHelper.DATE
//                , DatabaseHelper.ROUTE_NAME, DatabaseHelper.ORDER_TYPES, DatabaseHelper.userId};
//
//        Cursor cursor = database.query(DatabaseHelper.HEADERS_TABLE_NAME, columns, selection, args, null, null, null);
//        while (cursor.moveToNext()) {
//            HeadersModel model = new HeadersModel(
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getInt(3),
//                    cursor.getInt(4),
//                    cursor.getString(5),
//                    cursor.getString(6),
//                    cursor.getString(7),
//                    cursor.getInt(8),
//                    cursor.getString(9),
//                    cursor.getString(10),
//                    cursor.getInt(11),
//                    cursor.getString(12),
//                    cursor.getInt(13),
//                    cursor.getInt(14),
//                    cursor.getInt(15),
//                    cursor.getString(16),
//                    cursor.getInt(17),
//                    cursor.getString(18),
//                    cursor.getString(19),
//                    cursor.getString(20),
//                    cursor.getString(21),
//                    cursor.getInt(22),
//                    cursor.getInt(23),
//                    cursor.getInt(24)
//            );
//            headerList.add(model);
//        }
//        return headerList;
//
//    }


    //Update Quantity of lines table, as well as changing the flag value using orderId & orderDetailsId:
//    public long updateLinesQuantity(int orderId, int orderDetailsId, int userId, int quantity, int flag, int loaded) {
//        SQLiteDatabase database = helper.getWritableDatabase();
//        String selection = DatabaseHelper.OrderIds + " LIKE ? AND " + DatabaseHelper.OrderDetailId + " LIKE ? ";
//        String[] args = {"" + orderId, "" + orderDetailsId};
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.Qty, quantity);
//        contentValues.put(DatabaseHelper.FLAG, flag);
//        contentValues.put(DatabaseHelper.Loadeds, loaded);
//
//        long ids = database.update(DatabaseHelper.LINES_TABLE_NAME, contentValues, selection, args);
//
//        return ids;
//    }

//    public long deleteQueue(int id) {
//        SQLiteDatabase database = helper.getWritableDatabase();
//        //select * from table_name where id = id
//        String selection = DatabaseHelper.UID + " LIKE ?";
//
//        String[] args = {"" + id};
//        long ids = database.delete(DatabaseHelper.QUEUE_TABLE_NAME, selection, args);
//
//        return ids;
//    }

    /**
     * Drop Header table
     */


    class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        private static final String DATABASE_NAME = "my_auth.db";
        private static final int VERSION_NUMBER = 10;

        //Header Table:
        private static final String HEADERS_TABLE_NAME = "headers";
        private static final String UID = "_id";
        private static final String intHeaderID = "intHeaderID";
        private static final String strMessage = "strMessage";
        private static final String date = "date";
        private static final String strUserName = "strUserName";
        private static final String strCell = "strCell";
        private static final String strEmail = "strEmail";
        private static final String companyID = "companyID";
        private static final String groupID = "groupID";

        //Creating the table:
        private static final String CREATE_HEADER_TABLE = "CREATE TABLE " + HEADERS_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + intHeaderID + " VARCHAR(255),"
                + strMessage + " VARCHAR(255),"
                + date + " VARCHAR(255),"
                + strUserName + " VARCHAR(255),"
                + strCell + " VARCHAR(255),"
                + strEmail + " VARCHAR(255),"
                + companyID + " INTEGER,"
                + groupID + " INTEGER);";
        private static final String DROP_HEADER_TABLE = "DROP TABLE IF EXISTS " + HEADERS_TABLE_NAME;


        private static final String LINES_TABLE_NAME = "lines";
        private static final String intAuthLineId = "intAuthLineId";
        private static final String strLineMessage1 = "strLineMessage1";
        private static final String strLineMessage2 = "strLineMessage2";
        private static final String strLineMessage3 = "strLineMessage3";
        private static final String blnIsApproved = "blnIsApproved";
        //Creating the table:
        private static final String CREATE_LINE_TABLE = "CREATE TABLE " + LINES_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + intAuthLineId + " VARCHAR(255),"
                + strLineMessage1 + " VARCHAR(255),"
                + strLineMessage2 + " VARCHAR(255),"
                + strLineMessage3 + " VARCHAR(255),"
                + blnIsApproved + " VARCHAR(255),"
                + intHeaderID + " VARCHAR(255));";
        private static final String DROP_LINES_TABLE = "DROP TABLE IF EXISTS " + LINES_TABLE_NAME;

        private static final String QUEUE_TABLE_NAME = "queue";
        private static final String IsAuthorized = "IsAuthorized";
        private static final String Instructions = "strLineMessage1";
        //Creating the table:
        private static final String CREATE_QUEUE_TABLE = "CREATE TABLE " + QUEUE_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + intAuthLineId + " VARCHAR(255),"
                + IsAuthorized + " INTEGER,"
                + Instructions + " VARCHAR(255));";
        private static final String DROP_QUEUE_TABLE = "DROP TABLE IF EXISTS " + QUEUE_TABLE_NAME;


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create table:
            try {
                db.execSQL(CREATE_HEADER_TABLE);
                db.execSQL(CREATE_LINE_TABLE);
                db.execSQL(CREATE_QUEUE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_HEADER_TABLE);
                db.execSQL(DROP_LINES_TABLE);
                db.execSQL(DROP_QUEUE_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}