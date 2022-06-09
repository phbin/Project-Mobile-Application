import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantClass
import com.squareup.picasso.Picasso

class Favorite_Restaurant_RecyclerView (var context : Context, var listItem : List<RestaurantClass>)
    : RecyclerView.Adapter<Favorite_Restaurant_RecyclerView.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_favorite_restaurant,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textNameRestPopular.text = listItem[position].name
        holder.textLocationPopular.text = listItem[position].location
        Picasso.get().load(listItem[position].image).into(holder.imgRestPopular)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textNameRestPopular : TextView = itemView.findViewById(R.id.textNameRestPopular)
        var imgRestPopular : ImageView = itemView.findViewById(R.id.imgRestPopular)
        var textLocationPopular : TextView = itemView.findViewById(R.id.textLocationPopular)
    }

}