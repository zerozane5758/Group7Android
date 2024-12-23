package g7.project.group7android

data class Konser(
    val namaKonser: String,
    val deskripsi: String,
    val lokasi: String,
    val tanggal: String,
    val jenisTiket: List<String>,
    val gambar: String
)
