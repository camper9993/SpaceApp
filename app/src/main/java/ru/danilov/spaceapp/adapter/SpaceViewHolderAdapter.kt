package ru.danilov.spaceapp.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.danilov.spaceapp.R
import ru.danilov.spaceapp.model.Items
import ru.danilov.spaceapp.ui.InfoFragment


class SpaceAdapter(private var itemList: ArrayList<Items>) : RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_space, parent, false)


        return SpaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        Picasso.with(holder.context).load(itemList[position].links[0].href).resize(1000,700).into(holder.imageView)
        holder.itemView.setOnClickListener {
            Log.d("check", "test click")
            val activity = holder.context as AppCompatActivity
            val bundle = Bundle()
            bundle.putString("image_url", itemList[position].links[0].href)
            bundle.putString("description", itemList[position].data[0].description)
            bundle.putString("title", itemList[position].data[0].title)
            val infoFragment = InfoFragment()
            infoFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_constraint_layout, infoFragment)
                .addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun setUpItems(listOfItems: List<Items>) {
        itemList.clear()
        itemList.addAll(listOfItems)
        notifyDataSetChanged()
    }

    class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context!!
        val imageView : ImageView = view.findViewById(R.id.photo)
    }

}