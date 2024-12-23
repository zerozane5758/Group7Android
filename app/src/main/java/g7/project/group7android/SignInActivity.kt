package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import g7.project.group7android.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Tambahkan finish() untuk menutup SignInActivity
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
        val url = "https://statik.tempo.co/data/2023/05/09/id_1202686/1202686_720.jpg"
        tambahKonser(
            "Coldplay : Music of the Spheres",
            "Coldplay melakukan world tour dengan tema Music of the Spheres. Dan tepatnya pada tanggal 15 November 2023 Coldplay akan melakukan konser di Stadium Gelora Bung Karno, Jakarta, Indonesia.",
            "Gelora Bung Karno Stadium",
            "2023-11-15",
            listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
            url
        )
    }

    fun tambahKonser(
        nama: String,
        deskripsi: String,
        lokasi: String,
        tanggal: String,
        jenisTiket: List<String>,
        gambar: String
    ) {
        // Hapus semua data lama dari koleksi "konser"
        firestore.collection("konser")
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Iterasi setiap dokumen dalam koleksi dan hapus
                for (document in querySnapshot) {
                    firestore.collection("konser").document(document.id).delete()
                }
                // Setelah data lama dihapus, tambahkan data baru
                val dataKonser = hashMapOf(
                    "namaKonser" to nama,
                    "deskripsi" to deskripsi,
                    "lokasi" to lokasi,
                    "tanggal" to tanggal,
                    "jenisTiket" to jenisTiket,
                    "gambar" to gambar
                )
                firestore.collection("konser").get().addOnSuccessListener { result ->
                    val maxId = result.documents.mapNotNull { it.id.toIntOrNull() }.maxOrNull() ?: 0
                    val nextId = (maxId + 1).toString()

                    firestore.collection("konser").document(nextId).set(dataKonser)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Data konser berhasil diperbarui.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Gagal menambahkan data konser: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

            }
    }
}