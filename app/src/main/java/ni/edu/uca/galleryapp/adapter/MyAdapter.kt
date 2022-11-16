package ni.edu.uca.galleryapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.galleryapp.databinding.ImageGridBinding
import ni.edu.uca.galleryapp.models.Image

class MyAdapter(val data: ArrayList<Image>, val context : Context): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class  MyViewHolder(val imageGridBinding: ImageGridBinding) : RecyclerView.ViewHolder(imageGridBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ImageGridBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.imageGridBinding.imag.setImageBitmap(data[position].image)
    }

    override fun getItemCount(): Int {
        return  data.size
    }
}