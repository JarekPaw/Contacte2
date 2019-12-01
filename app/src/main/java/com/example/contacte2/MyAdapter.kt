package com.example.contacte2

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.adapter_layout.view.*

class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(viewOne: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(viewOne.context)
        val adapterLayout: View = layoutInflater.inflate(R.layout.adapter_layout,viewOne,false)
        return MyViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return ContactsLists.contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = holder.view.tv_name
        val number: TextView = holder.view.tv_no
        val email: TextView = holder.view.tv_email
        val btnCall: Button = holder.view.btn_call //okodowanie przycisku Call

        name.setText(ContactsLists.contactList[position])//w [] może być też: holder.adapterPosition
        number.setText(ContactsLists.numberList[holder.adapterPosition])
        email.setText(ContactsLists.emailList[position])

        btnCall.setOnClickListener {
            val makeCallIntent = Intent()
            if (ContactsLists.numberList[holder.adapterPosition] == "Brak nr"){ //lub: ContactsLists.numberList[position]
                Toast.makeText(holder.itemView.context, "Brak numeru",Toast.LENGTH_SHORT)
                    .show()
            }else{
                makeCallIntent.data = Uri.parse("tel:"+ContactsLists.numberList[holder.adapterPosition]) // lub [position]
                //makeCallIntent.action = Intent.ACTION_CALL // używając tej akcji, wysłany numer będzie od razu uruchamiany
                makeCallIntent.action = Intent.ACTION_DIAL // używając tej akcji, wysyłamy numer do polaw wybierania numerów. Nie jest potrzebne pozwolenie Permission
                startActivity(holder.itemView.context,makeCallIntent,null)
            }

        }

    }
}
class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)