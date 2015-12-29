package com.offpad.testoffpad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.offpad.testoffpad.Adapters.CustomListViewAdapter;
import com.offpad.testoffpad.Models.Transactions;

import java.util.List;

/**
 * Created by djlophu on 14/09/15.
 */
public class History extends Activity{
    ListView listHistory;
    List<Transactions> transactionItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        //Call all the transactions from the database...
        transactionItems = new Select()
                .from(Transactions.class)
                .orderBy("id ASC")
                .execute();

        //Now assigning the customadapter to the listview...
        listHistory = (ListView) findViewById(R.id.listHistory);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.singlelist, transactionItems);
        listHistory.setAdapter(adapter);
    }
    public void Back(View v){
            this.finish();
    }
}
