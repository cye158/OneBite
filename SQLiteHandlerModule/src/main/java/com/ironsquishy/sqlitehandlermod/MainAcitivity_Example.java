package com.ironsquishy.sqlitehandlermod;
/*
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainAcitivity_Example extends ActionBarActivity {

    EditText inputText;
    TextView outputText;
    SQLDBHandlers dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.textInput);
        outputText = (TextView) findViewById(R.id.outputText);
        dbHandler = new SQLDBHandlers(this, null, null,1);
        printDatabase();
    }

    //For add button on screen.
    public void addButtonCLicked(View view)
    {
        EntryField entry = new EntryField(inputText.getText().toString());
        dbHandler.addEntry(entry);
        printDatabase();

    }

    //Delete item from screen.
    public void deleteButtonClicked(View view)
    {
        String inText = inputText.getText().toString();
        dbHandler.deleteEntry(inText);
        printDatabase();
    }


    //Print out on screen.
    public void printDatabase()
    {
        String dbString = dbHandler.databaseToString();
        outputText.setText(dbString);
        inputText.setText("");
    }


}

*/