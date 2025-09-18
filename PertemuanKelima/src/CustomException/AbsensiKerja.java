/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CustomException;

/**
 *
 * @author Husain
 */
public class AbsensiKerja {

    // Custom Exception Class
    public static class HariKerjaException extends Exception {
        public HariKerjaException(String message) {
            super(message);
        }
    }

    public void cekAbsensi(String namaHari) throws HariKerjaException {
        String hari = namaHari.toLowerCase();

        if (hari.equals("sabtu") || hari.equals("minggu")) {
            throw new HariKerjaException("Hari " + namaHari + " adalah hari libur. Tidak perlu absensi.");
        }
        System.out.println("Hari " + namaHari + " adalah hari kerja. Silakan absen.");
    }

    public static void main(String[] args) {
        AbsensiKerja absensi = new AbsensiKerja();

        System.out.println("--- Mencoba Absensi ---");
        
        // Kasus 1: Absensi di hari libur (Sabtu)
        String hariLibur = "Sabtu";
        try {
            System.out.println("Mengecek hari: " + hariLibur);
            absensi.cekAbsensi(hariLibur);
        } catch (HariKerjaException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }

        System.out.println("\n---");

        // Kasus 2: Absensi di hari kerja (Senin)
        String hariKerja = "Senin";
        try {
            System.out.println("Mengecek hari: " + hariKerja);
            absensi.cekAbsensi(hariKerja);
        } catch (HariKerjaException e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
