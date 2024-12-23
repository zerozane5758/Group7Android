package g7.project.group7android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class priceAdapter(private val prices: List<dcPrice>) :
    RecyclerView.Adapter<priceAdapter.PriceViewHolder>() {

    inner class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val item = prices[position]
        holder.tvCategory.text = item.category
        holder.tvPrice.text = item.price
    }

    override fun getItemCount(): Int = prices.size
}
