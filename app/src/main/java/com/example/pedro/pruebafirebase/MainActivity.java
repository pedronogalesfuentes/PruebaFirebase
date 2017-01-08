package com.example.pedro.pruebafirebase;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //INI Create Firebase database object by using the following code:
    // Connect to the Firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    // Get a reference to the todoItems child items it the database
    final DatabaseReference myRef = database.getReference("hijo");
//FIN Create Firebase database object by using the following code

    //INI: lista de variables necesarias para crear la lista
    private List<String> lista; //objeto de tipo lista que tiene la lista de nombres
    private ListView listaListView; // objeto de tipo ListView que enlazaremos al ListView que hemos creado en el Layout
    private ArrayAdapter<String> adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private ArrayAdapter arrayAdapter; //objeto de tipo ArrayAdapter necesario para enganchar el array al ListView
    //FIN: lista de variables necesarias para crear la lista


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//INI ahora hago una lista
        lista = new ArrayList<String>(); //hago la lista con los datos
        lista.add("pepe");
        lista.add("juan");


        //hago el adapter con la lista anterior
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);

        listaListView = (ListView) findViewById(R.id.listView); // asociamos a nuestro objeto ListView el que hemos creado en el Layout
        //ahora le asocio el adapter a la lista
        listaListView.setAdapter(adapter);

        listaListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){

                //INI eliminamos el elemento seleccionado

                    Query myQuery = myRef.orderByValue().equalTo((String) listaListView.getItemAtPosition(position));
                    myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren()) {
                                DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                firstChild.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
            }
        });

 //FIN



        //creamos la variable ligada al editText en el que introduciremos el texto cada vez que queramos
        //introducir un nuevo campo en la lista
        final EditText editText = (EditText) findViewById(R.id.editText);

        //creamos un botón ligado al recurso button2
        //le asociamos un OnClickListener que leera el texto de la variable texto2 y lo introducirá en la BBDD Firebase
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the text from Edit text
                String texto = (String) editText.getText().toString();
                //adapter.add(texto);

                //INI set it on Firebase
                //myRef.setValue(texto);

                // Create a new child with a auto-generated ID.
                DatabaseReference childRef = myRef.push();

                // Set the child's data to the value passed in from the text box.
                childRef.setValue(texto);
                //FIN set it on Firebase
            }
        });

        //INI

        // Assign a listener to detect changes to the child items
        // of the database reference.
        myRef.addChildEventListener(new ChildEventListener() {

            // This function is called once for each child that exists
            // when the listener is added. Then it is called
            // each time a new child is added.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplication(), "onChildAdded:" + value, Toast.LENGTH_SHORT).show();
                adapter.add(value);
            }

            // This function is called each time a child item is removed.
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplication(), "onChildRemoved:" + value, Toast.LENGTH_SHORT).show();
                adapter.remove(value);
            }

            // The following functions are also required in ChildEventListener implementations.
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });
        //FIN
/*
//INI In order to listen to changes to Firebase.
        //http://kodesnippets.com/index.php/2016/05/29/getting-started-with-firebase-in-android/
        //defino para la BBDD myRef un ValueEventListener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //defino su metodo onDataChange que se invocará cada vez que los datos son actualizados o modificados
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplication(), "Value Changed To:" + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//FIN  In order to listen to changes to Firebase.

*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

