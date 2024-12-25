package g7.project.group7android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.journeyapps.barcodescanner.BarcodeEncoder

class TicketAdapter(private val ticketList: List<Ticket>) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.bind(ticket)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TicketActivity::class.java).apply {
                putExtra("namaKonser", ticket.namaKonser)
                putExtra("deskripsi", ticket.deskripsi)
                putExtra("lokasi", ticket.lokasi)
                putExtra("tanggal", ticket.tanggal)
                putExtra("seat", ticket.seat)
                putExtra("harga", ticket.price)
                putExtra("gambar", ticket.gambar)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = ticketList.size

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ticket: Ticket) {
            itemView.findViewById<TextView>(R.id.tvTitle).text = ticket.namaKonser
            itemView.findViewById<TextView>(R.id.tvLocation).text = ticket.lokasi
            itemView.findViewById<TextView>(R.id.tvDate).text = ticket.tanggal
            itemView.findViewById<TextView>(R.id.tvSeat).text = ticket.seat
            itemView.findViewById<TextView>(R.id.tvPrice).text = ticket.price
            itemView.findViewById<TextView>(R.id.userEmail).text = ticket.email

            // Buat data untuk QR Code dalam format JSON
            val qrData = ticket.namaKonser

            // Generate QR Code
            val ivQr = itemView.findViewById<ImageView>(R.id.ivQr)
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(qrData, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400)
                ivQr.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}