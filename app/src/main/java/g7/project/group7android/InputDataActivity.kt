package g7.project.group7android

import Konser
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import g7.project.group7android.databinding.ActivitySignInBinding

class InputDataActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firestore = FirebaseFirestore.getInstance()
        val _btInput = findViewById<Button>(R.id.btInput)

        _btInput.setOnClickListener() {
            tambahKonser()
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
        }

    }

    fun tambahKonser() {

        val _etNama = findViewById<EditText>(R.id.etNama).text.toString()
        val _etDeskripsi= findViewById<EditText>(R.id.etDeskripsi).text.toString()
        val _etLokasi = findViewById<EditText>(R.id.etLokasi).text.toString()
        val _etTanggal = findViewById<EditText>(R.id.etTanggal).text.toString()
        val _etJenis = findViewById<EditText>(R.id.etJenis).text.toString()
        val _etHarga = findViewById<EditText>(R.id.etHarga).text.toString()
        val _etGambar = findViewById<EditText>(R.id.etGambar).text.toString()

        val tiketSplit = _etJenis.split(",").map { it.trim() }
        val hargaSplit = _etHarga.split(",").map { it.trim() }

        val konserList =
            Konser(
                _etNama,
                _etDeskripsi,
                _etLokasi,
                _etTanggal,
                tiketSplit,
                hargaSplit,
                _etGambar
            )



        // Hapus semua data lama dari koleksi "konser"
        firestore.collection("konser").document(_etNama).set(konserList)
            .addOnSuccessListener {
                // Iterasi setiap dokumen dalam koleksi dan hapus
                Toast.makeText(this, "Berhasil add data ", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
            }
    }

}