package com.example.contacte2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = MyAdapter()// adapter to połączenie między bazą da nych a widokiem w aplikacji. Dostosowuje suche dane z bazy danych do odpowiednich miejsc w widoku. Wie jak wygląda widok i umieszcza np nr tel w odpowiednim TextView

        val myContResolv: ContentResolver = contentResolver
        val myCursor: Cursor? = myContResolv.query(ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )
        if (myCursor!= null)
            try {
                myCursor.moveToNext()
                while (!myCursor.isAfterLast){
                    val id: String = myCursor.getString(myCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val name: String = myCursor.getString(myCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    ContactsLists.contactList.add(name)
                    myCursor.moveToNext()
                    readPhoneNumber(contentResolver, id)
                    readEmail(contentResolver, id)
                }
            }finally {
                myCursor.close()
            }
    }
}

/*jeśli kontakt ma więcej niż jeden nmer telefonu. Wszystkie z tych numerów mają to samo id kontaktu. Dlatego
* filtr ContactsContract.CommonDataKinds.Phone._ID + "= ?", arrayOf(id) je przepuści. Wtedy otrzymamy cursor, który będzie miał więcej
* niż jeden numer. Od nas zależy czy wczytamy jeden czy wszystkie(w pętli). */
fun readPhoneNumber(contentResolver: ContentResolver, id: String){
    val phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
        arrayOf(id),
        null)
        if (phoneCursor!!.moveToFirst()) {  // powinno też być dobrze: phoneCursor!!.moveToFirst()

            val number1: String = phoneCursor.getString(
                phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            ContactsLists.numberList.add(number1)




        }else ContactsLists.numberList.add("Brak nr")
        phoneCursor.close()


}
fun readEmail(contentResolver: ContentResolver, id: String){
    val emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
        null,
        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "= ?",
        arrayOf(id),
        null)
    if (emailCursor!!.moveToFirst()){
        val email1: String = emailCursor.getString(
            emailCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS))
        ContactsLists.emailList.add(email1)

    }else ContactsLists.emailList.add(" - ")
    emailCursor.close()

}

