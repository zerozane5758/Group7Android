package g7.project.group7android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterKonser(private val konserList: List<Konser>) : RecyclerView.Adapter<adapterKonser.KonserViewHolder>() {

    class KonserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaKonser: TextView = itemView.findViewById(R.id.titleTextView)
        val lokasi: TextView = itemView.findViewById(R.id.location)
        val tanggal: TextView = itemView.findViewById(R.id.date)
        val gambar: ImageView = itemView.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KonserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return KonserViewHolder(view)
    }

    override fun onBindViewHolder(holder: KonserViewHolder, position: Int) {
        val konser = konserList[position]
        holder.namaKonser.text = konser.namaKonser
        holder.lokasi.text = konser.lokasi
        holder.tanggal.text = konser.tanggal
        Picasso.get().load(konser.gambar).into(holder.gambar)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, OrderActivity::class.java).apply {
                putExtra("namaKonser", konser.namaKonser)
                putExtra("deskripsi", konser.deskripsi)
                putExtra("lokasi", konser.lokasi)
                putExtra("tanggal", konser.tanggal)
                putStringArrayListExtra("jenisTiket", ArrayList(konser.jenisTiket))
                putExtra("gambar", konser.gambar)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return konserList.size
    }
}