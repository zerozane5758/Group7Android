package g7.project.group7android

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.squareup.picasso.Picasso

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, ListTicket::class.java)
            startActivity(intent)
        }

        val _eventImage = findViewById<ImageView>(R.id.eventImage)
        val _tvJudul = findViewById<TextView>(R.id.tvJudul)
        val _tvLocation = findViewById<TextView>(R.id.tvLocation)
        val _tvTanggal = findViewById<TextView>(R.id.tvTanggal)
        val _tvType = findViewById<TextView>(R.id.tvType)

        val namaKonser = intent.getStringExtra("namaKonser")
        val lokasi = intent.getStringExtra("lokasi")
        val tanggal = intent.getStringExtra("tanggal")
        val type = intent.getStringExtra("seat")
        val gambar = intent.getStringExtra("gambar")

        _tvJudul.text = namaKonser
        _tvLocation.text = lokasi
        _tvTanggal.text = tanggal
        _tvType.text = type
        Picasso.get().load(gambar).into(_eventImage)

        val ivBarcode = findViewById<ImageView>(R.id.ivBarcode)

        // Pastikan `namaKonser` tidak null sebelum memproses
        if (namaKonser != null) {
            val qrCodeBitmap = generateQRCode(namaKonser)
            if (qrCodeBitmap != null) {
                ivBarcode.setImageBitmap(qrCodeBitmap)
            } else {
                Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun generateQRCode(content: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 800, 800)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}