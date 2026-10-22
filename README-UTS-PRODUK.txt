PROJECT UTS MOBILE II - KATALOG PRODUK

FITUR YANG SUDAH DITAMBAHKAN
1. Retrofit + Gson untuk API https://dummyjson.com/
2. Meminta 25 produk dengan kolom: id,title,description,category,price,stock,availabilityStatus,tags,dimensions,reviews,rating,images,thumbnail.
3. Daftar produk memakai RecyclerView + Card; thumbnail dimuat menggunakan Glide.
4. Klik item untuk membuka halaman detail; detail mengambil data berdasarkan ID dan gambar menggunakan kolom images.
5. Review pertama tampil di halaman detail; tombol Lihat semua review membuka dialog.
6. Bottom Navigation untuk Produk dan Profil.
7. Profil developer dengan foto, nama, NIM, email, nomor HP, prodi, jurusan, perguruan tinggi.

CARA MEMBUKA
- Ekstrak ZIP.
- Android Studio > Open > pilih folder Uts/Uts (folder yang berisi settings.gradle.kts).
- Tunggu Gradle Sync dan pastikan internet aktif untuk mengunduh dependency.
- Jalankan aplikasi di emulator atau HP Android.

SEBELUM DIKUMPULKAN
- Buka ProfileFragment.kt, ganti teks email dan nomor HP dengan data milikmu.
- Ganti gambar app/src/main/res/drawable/profile.* dengan foto profil sendiri jika perlu (nama resource harus tetap profile).
- Jalankan Build > Make Project, lalu tes daftar produk, klik detail, tombol review, dan menu profil.

CATATAN
Project telah diubah berdasarkan soal DummyJSON Product. Build belum bisa dinyatakan berhasil di lingkungan ini karena Android SDK/Gradle dependency tidak tersedia untuk verifikasi penuh; lakukan Gradle Sync dan Run di Android Studio. Jika ada pesan error, kirim screenshot Build Output agar bisa diperbaiki.
