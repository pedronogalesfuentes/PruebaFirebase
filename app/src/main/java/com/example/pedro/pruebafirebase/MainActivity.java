package com.example.pedro.pruebafirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //INI: lista de variables necesarias para crear la lista
    private String[] lista = {"Windows", "Linux", "OSx"}; //Array de String que contiene los valores de la lista
    private ListView listaListView; // objeto de tipo ListView que enlazaremos al ListView que hemos creado en el Layout
    //private ArrayAdapter arrayAdapter; //objeto de tipo ArrayAdapter necesario para enganchar el array al ListView
    //FIN: lista de variables necesarias para crear la lista


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//INI ahora hago una lista
        listaListView = (ListView) findViewById(R.id.listView); // asociamos a nuestro objeto ListView el que hemos creado en el Layout
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista); /* construimos un objeto ArrayAdapter con los siguientes parámetros:
contexto = this
layout = un layout estándar de Android, en concreto el android.R.layout.simple_list_item_1
array = el array que hemos creado, sisOp */
        listaListView.setAdapter(arrayAdapter); //asociamos al ListView el ArrayAdapter que hemos construido
        listaListView.setOnItemClickListener( //definimos el método callback en caso de pulsar sobre un item de la lista
                new AdapterView.OnItemClickListener() {//construimos un nuevo método
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int itemPosition = position; //esta es la posición del item pulsado (empieza a contar en cero)
                        String itemValue = (String) listaListView.getItemAtPosition(itemPosition); //este es el string del item pulsado
                        Toast.makeText(getApplication(), "Seleccion en lista:" + itemValue, Toast.LENGTH_SHORT).show();
                    }
                });
        final String item = "item";
        arrayAdapter.add(item); //////ME ESTA FALLANDO ESTO EL .ADD DE UN ARRAY ADAPTER
//FIN ahora hago una lista


//INI Create Firebase database object by using the following code:
        // Connect to the Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference("hijo");
//FIN Create Firebase database object by using the following code

        // creamos una variable de texto ligada al recurso textView2
        final TextView texto2 = (TextView) findViewById(R.id.textView2);

        //creamos un botón ligado al recurso button2
        //le asociamos un OnClickListener que leera el texto de la variable texto2 y lo introducirá en la BBDD Firebase
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the text from Edit text
                String text = texto2.getText().toString();
                //INI set it on Firebase
                //myRef.setValue(text);
                //FIN set it on Firebase
            }
        });

        //INI

        // Assign a listener to detect changes to the child items
        // of the database reference.
        myRef.addChildEventListener(new ChildEventListener(){

        // This function is called once for each child that exists
        // when the listener is added. Then it is called
        // each time a new child is added.
        @Override
        public void onChildAdded (DataSnapshot dataSnapshot, String previousChildName) {
            String value = dataSnapshot.getValue(String.class);
            Toast.makeText(getApplication(), "onChildAdded:" + value, Toast.LENGTH_SHORT).show();
            //arrayAdapter.add(value);
        }

        // This function is called each time a child item is removed.
    public void onChildRemoved(DataSnapshot dataSnapshot){
        String value = dataSnapshot.getValue(String.class);
        Toast.makeText(getApplication(), "onChildRemoved:" + value, Toast.LENGTH_SHORT).show();
        //arrayAdapter.remove(value);
    }

    // The following functions are also required in ChildEventListener implementations.
    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName){}
    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName){}

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

    }
}

