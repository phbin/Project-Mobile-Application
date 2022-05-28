import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R

class DishDetail_Choice_Size_RecyclerView: RecyclerView.Adapter<DishDetail_Choice_Size_RecyclerView.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_choice_size,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 25
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

}