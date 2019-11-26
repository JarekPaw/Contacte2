package com.example.contacte2

import android.content.ContentResolver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        var name = holder.view.tv_name
        var number: TextView = holder.view.tv_no
        var email: TextView = holder.view.tv_email

        name.setText(ContactsLists.contactList[position])
        number.setText(ContactsLists.numberList[position])
        email.setText(ContactsLists.emailList[position])

    }
}
class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)