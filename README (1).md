# Custom Exception dan CRUD dengan Java Form

Pada praktikum ini dibuat dua implementasi utama, yaitu **class Custom Exception** untuk memvalidasi absensi berdasarkan hari kerja, serta **aplikasi CRUD data komik** menggunakan Java Form yang terhubung dengan PostgreSQL.

## 1. Custom Exception
Exception adalah kondisi error yang muncul saat program berjalan. Java memiliki exception bawaan, namun terkadang kita perlu membuat pengecualian khusus sesuai kebutuhan. Pada praktikum ini dibuat `HariKerjaException` yang digunakan untuk memeriksa apakah absensi dilakukan di hari kerja atau hari libur.

**Manfaat penggunaan Custom Exception:**
- Memberikan pesan error yang lebih spesifik dan informatif.  
- Memudahkan debugging dan pemeliharaan program.  
- Membuat struktur program lebih rapi dan terkontrol.  

**Contoh Source Code:**
```java
try {
    absensi.cekAbsensi("Sabtu");
} catch (HariKerjaException e) {
    System.out.println("Terjadi kesalahan: " + e.getMessage());
}
```
Pada program ini, method `cekAbsensi` akan mengecek input hari. Jika hari yang dicek adalah Sabtu/Minggu, maka akan dilempar ke `HariKerjaException` yang ditangkap oleh `catch` untuk menampilkan pesan kesalahan.

---

## 2. CRUD dengan Java Form
CRUD (Create, Read, Update, Delete) merupakan empat operasi dasar untuk pengolahan data. Implementasi praktikum ini menggunakan **Java Swing** sebagai GUI dan **PostgreSQL** dengan JDBC sebagai database.

**Operasi CRUD:**
- **Create (Insert Data):** Menambahkan data komik baru ke database dengan `PreparedStatement`.  
- **Read (Menampilkan Data):** Mengambil data dari database dengan query `SELECT` lalu ditampilkan di `JTable` melalui `DbUtils`.  
- **Update (Mengubah Data):** Memperbarui data komik sesuai ID dengan query `UPDATE`.  
- **Delete (Menghapus Data):** Menghapus data dari tabel setelah konfirmasi menggunakan `JOptionPane`.  

**Penjelasan singkat method utama:**
- `showTable()` → Menampilkan seluruh data komik ke `JTable`.  
- `jButton1ActionPerformed()` → Menambah data baru.  
- `jButton2ActionPerformed()` → Mengubah data komik.  
- `jButton3ActionPerformed()` → Menghapus data komik.  
- `bersih()` → Membersihkan seluruh input field.  
- `jTable1MouseClicked()` → Mengisi field otomatis saat baris di tabel diklik.  
