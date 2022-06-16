import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.Explore_More_RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PreviousOrdersClass
import com.example.fooddelivery.model.RestaurantClass
import com.example.fooddelivery.model.RestaurantOrders
import com.squareup.picasso.Picasso

class Previous_Orders_RecyclerView (var Context : Context, var listItem : List<RestaurantClass>)
    : RecyclerView.Adapter<Previous_Orders_RecyclerView.MyViewHolder>() {

    lateinit var itemClick : Previous_Orders_RecyclerView.onIntemClickListener
    var onItemClick : ((RestaurantOrders) -> Unit)? = null

    interface onIntemClickListener{
        fun onClickItem(position: Int)
    }

    fun setOnIntemClickListener(listener: onIntemClickListener){
        itemClick=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_explore_more,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textNameRestaurantExploreMore.text = listItem[position].name
        holder.location.text = listItem[position].location
        Picasso.get().load(listItem[position].image).into(holder.image)

        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(listItems[position])
            itemClick.onClickItem(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textNameRestaurantExploreMore : TextView = itemView.findViewById(R.id.textNameRestaurantExploreMore)
        var image : ImageView = itemView.findViewById(R.id.imageRestaurantExploreMore)
        var location : TextView = itemView.findViewById(R.id.textLocationExploreMore)
    }

}