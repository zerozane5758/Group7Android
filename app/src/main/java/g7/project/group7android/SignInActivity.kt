package g7.project.group7android

import Konser
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
        val konserList = listOf(
            Konser(
                "Taylor Swift: The Eras Tour",
                "Taylor Swift memulai tur globalnya dengan tema The Eras Tour. Pada tanggal 22 Juni 2024, Taylor Swift akan menggelar konser di Wembley Stadium, London, Inggris.",
                "Wembley Stadium",
                "2024-06-22",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/01/2024/02/17/taylor-swift-eras-tour-081023-3-3411bb8115944906a22fa9d1b7239d13-103355577.jpg"
            ),
            Konser(
                "BTS: Permission to Dance on Stage",
                "BTS melakukan tur dunia dengan tema Permission to Dance on Stage. Salah satu konsernya diadakan pada tanggal 10 April 2022 di Allegiant Stadium, Las Vegas, Amerika Serikat.",
                "Allegiant Stadium",
                "2022-04-10",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://asset.kompas.com/crops/cFUh0-M1yf8-pw2QpOgc01HawAM=/0x172:1080x892/750x500/data/photo/2022/02/23/6215bd8864f3f.jpg"
            ),
            Konser(
                "Coldplay : Music of the Spheres",
                "Coldplay melakukan world tour dengan tema Music of the Spheres. Dan tepatnya pada tanggal 15 November 2023 Coldplay akan melakukan konser di Stadium Gelora Bung Karno, Jakarta, Indonesia.",
                "Gelora Bung Karno Stadium",
                "2023-11-15",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://statik.tempo.co/data/2023/05/09/id_1202686/1202686_720.jpg"
            ),
            Konser(
                "Westlife: The Wild Dreams Tour",
                "Westlife menggelar tur bertajuk The Wild Dreams Tour di Indonesia. Pada tanggal 9 dan 10 Februari 2023, Westlife tampil di ICE BSD City, Tangerang, Indonesia.",
                "ICE BSD City",
                "2023-02-09 & 2023-02-10",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://cdn.antaranews.com/cache/1200x800/2022/07/03/ARTWORK-BANNER-1920x1280.jpg"
            ),
            Konser(
                "Justin Bieber: Justice World Tour",
                "Justin Bieber mengadakan tur dunia bertajuk Justice World Tour. Pada tanggal 3 November 2022, Justin Bieber tampil di Stadion Madya, Gelora Bung Karno, Jakarta, Indonesia.",
                "Stadion Madya, Gelora Bung Karno",
                "2022-11-03",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://s.kaskus.id/img/hot_thread/hot_thread_fd1qp8ezrbif.jpg"
            ),
            Konser(
                "BLACKPINK: BORN PINK World Tour",
                "BLACKPINK menggelar konser sebagai bagian dari tur dunia BORN PINK. Pada tanggal 11 dan 12 Maret 2023, BLACKPINK tampil di Stadion Utama Gelora Bung Karno, Jakarta, Indonesia.",
                "Stadion Utama Gelora Bung Karno",
                "2023-03-11 & 2023-03-12"  ,
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://consequence.net/wp-content/uploads/2022/10/blackpink-hero.jpg?quality=80"
            ),
            Konser(
                "Ed Sheeran: Divide Tour",
                "Ed Sheeran mengadakan konser Divide Tour di Indonesia. Pada tanggal 3 Mei 2019, Ed Sheeran tampil di Stadion Utama Gelora Bung Karno, Jakarta, Indonesia.",
                "Stadion Utama Gelora Bung Karno",
                "2019-05-03",
                listOf("Ultimate Experience", "My Universe", "Festival", "CAT 1", "CAT 2", "CAT 3"),
                listOf("Rp. 15.000.000", "Rp. 10.000.000", "Rp. 8.000.000", "Rp. 5.000.000", "Rp. 4.200.000", "Rp. 3.000.000"),
                "https://themusicalgypsy.com/wp-content/uploads/2020/11/ed-feature.jpg"
            )
        )
        tambahKonser(konserList)
    }

    fun tambahKonser(konserList: List<Konser>) {
        // Hapus semua data lama dari koleksi "konser"
        firestore.collection("konser")
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Iterasi setiap dokumen dalam koleksi dan hapus
                for (document in querySnapshot) {
                    firestore.collection("konser").document(document.id).delete()
                }
                // Setelah data lama dihapus, tambahkan data baru
                for (konser in konserList) {
                    firestore.collection("konser").document(konser.namaKonser).set(konser)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Data konser berhasil ditambahkan.",
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
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal menghapus data lama: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    fun tambahKonser(konser: Konser) {
//        // Hapus semua data lama dari koleksi "konser"
//        firestore.collection("konser")
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                // Iterasi setiap dokumen dalam koleksi dan hapus
//                for (document in querySnapshot) {
//                    firestore.collection("konser").document(document.id).delete()
//                }
//                // Setelah data lama dihapus, tambahkan data baru
//                firestore.collection("konser").get().addOnSuccessListener { result ->
//                    val maxId = result.documents.mapNotNull { it.id.toIntOrNull() }.maxOrNull() ?: 0
//                    val nextId = (maxId + 1).toString()
//
//                    firestore.collection("konser").document(nextId).set(konser)
//                        .addOnSuccessListener {
//                            Toast.makeText(
//                                this,
//                                "Data konser berhasil diperbarui.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        .addOnFailureListener { e ->
//                            Toast.makeText(
//                                this,
//                                "Gagal menambahkan data konser: ${e.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                }
//            }
//    }
}