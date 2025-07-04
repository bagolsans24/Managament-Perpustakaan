-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2025 at 06:34 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `id_akun` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(30) NOT NULL,
  `level` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`id_akun`, `username`, `password`, `nama`, `email`, `level`) VALUES
('AK001', 'kelompok2', 'admin123', 'Kelompok 2', 'asalasalan@gmail.com', 'admin'),
('AK002', 'geridhea', 'Pelerkuda24', 'Anggeri Dwi Rahmat', 'anggerid7@gmail.com', 'user'),
('AK003', 'sayacecep', 'qwerty12', 'Muhammad Cecep', 'cecep12@gmail.com', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `kode_buku` varchar(10) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `pengarang` varchar(30) NOT NULL,
  `penerbit` varchar(30) NOT NULL,
  `tahun_terbit` varchar(50) NOT NULL,
  `kategori` varchar(70) NOT NULL,
  `stok` int(11) NOT NULL,
  `kode_rak` varchar(15) NOT NULL,
  `lokasi_buku` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`kode_buku`, `judul_buku`, `pengarang`, `penerbit`, `tahun_terbit`, `kategori`, `stok`, `kode_rak`, `lokasi_buku`) VALUES
('BK001', 'Belajar Agama Islam', 'Gus Azka', 'Erlangga', '2025-06-18', 'Agama', 2, 'RK003', 'Blok C'),
('BK002', 'Novel Cinta', 'Azka Besari', 'Erlangga', '2025-06-18', 'Sastra', 3, 'RK002', 'Blok B'),
('BK003', 'Cerita Rakyat: Sangkuriang', 'Ardino Aldi', 'Airlangga', '2023-08-15', 'Seni, Hiburan, & Olahraga', 4, 'RK005', 'Blok E'),
('BK004', 'Dasar Komputer', 'Rudi Hartono', 'Andi', '2023-01-23', 'Karya Umum & Komputer', 6, 'RK006', 'Blok F'),
('BK005', 'Psikologi Kehidupan', 'Dwi Lestari', 'Gramedia', '2022-06-05', 'Filsafat & Psikologi', 5, 'RK007', 'Blok G'),
('BK006', 'Fiqih Wanita', 'Ust. Ahmad', 'Erlangga', '2021-03-01', 'Agama', 9, 'RK003', 'Blok C'),
('BK007', 'Sosiologi Indonesia', 'Nia Pratama', 'Salemba Empat', '2022-01-07', 'Ilmu-ilmu Sosial', 4, 'RK004', 'Blok D'),
('BK008', 'Dasar-dasar linguistik', 'Budi S', 'Gramedia', '2021-09-14', 'Bahasa ', 7, 'RK001', 'Blok A'),
('BK009', 'Matematika Dasar', 'Lisa Andayani', 'Erlangga', '2018-10-29', 'Ilmu Pengetahuan Alam & Matematika', 5, 'RK008', 'Blok H'),
('BK010', 'Teknologi Informasi Modern', 'Toni Surya', 'Andi', '2023-12-14', 'Teknologi & Ilmu Terapan', 3, 'RK009', 'Blok I'),
('BK011', 'Teater Tradisional', 'Rina Widya', 'Bentang', '2022-08-17', 'Seni, Hiburan, & Olahraga', 3, 'RK005', 'Blok E'),
('BK012', 'Sejarah Dunia', 'Ahmad Zahid', 'Mizan', '2025-05-10', 'Geografi & Sejarah', 1, 'RK010', 'Blok J'),
('BK013', 'Sistem Komputer', 'Wawan Iskandar', 'Andi', '2019-08-24', 'Karya Umum & Komputer', 9, 'RK006', 'Blok F'),
('BK014', 'Filsafat Timur', 'Dr. Lukman', 'Erlangga', '2022-09-08', 'Filsafat & Psikologi', 4, 'RK007', 'Blok G'),
('BK015', 'Akhlak Mulia', 'Ust. Rahmat', 'Tazkia', '2022-11-29', 'Agama', 4, 'RK003', 'Blok C'),
('BK016', 'Ilmu Politik Global', 'Wahyu Hidayat', 'Salemba Empat', '2021-10-06', 'Ilmu-ilmu Sosial', 6, 'RK004', 'Blok D'),
('BK017', 'Tata Bahasa Indonesia', 'Siti Rahayu', 'Gramedia', '2020-12-29', 'Bahasa ', 5, 'RK001', 'Blok A'),
('BK018', 'Fisika Modern', 'Andi Junaedi', 'Erlangga', '2021-04-01', 'Ilmu Pengetahuan Alam & Matematika', 11, 'RK008', 'Blok H');

-- --------------------------------------------------------

--
-- Table structure for table `detailpinjam`
--

CREATE TABLE `detailpinjam` (
  `id_peminjaman` varchar(20) NOT NULL,
  `tanggal_pinjam` date NOT NULL,
  `tanggal_kembali` date NOT NULL,
  `status` varchar(20) NOT NULL,
  `nm_pjg` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detailpinjam`
--

INSERT INTO `detailpinjam` (`id_peminjaman`, `tanggal_pinjam`, `tanggal_kembali`, `status`, `nm_pjg`) VALUES
('P001', '2025-06-26', '2025-07-03', 'RENT', 'Andhika Agung Wicaksono'),
('P002', '2025-06-26', '2025-07-03', 'RETURNED', 'Andhika Agung Wicaksono'),
('P003', '2025-06-26', '2025-07-03', 'RENT', 'Andhika Agung Wicaksono'),
('P004', '2025-06-26', '2025-07-03', 'RENT', 'Andhika Agung Wicaksono'),
('P005', '2025-06-26', '2025-07-03', 'RENT', 'Andhika Agung Wicaksono'),
('P006', '2025-06-26', '2025-07-03', 'RETURNED', 'Andhika Agung Wicaksono'),
('P007', '2025-06-26', '2025-07-03', 'RENT', 'Andhika Agung Wicaksono'),
('P008', '2025-06-26', '2025-07-03', 'RETURNED', 'Andhika Agung Wicaksono'),
('P009', '2025-07-03', '2025-07-10', 'RETURNED', 'Anggeri Dwi Rahmat');

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` varchar(20) NOT NULL,
  `id_siswa` varchar(20) NOT NULL,
  `nmsiswa` varchar(100) NOT NULL,
  `id_buku` varchar(20) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `id_siswa`, `nmsiswa`, `id_buku`, `judul_buku`, `jumlah`) VALUES
('P001', 'S001', 'Anggeri Dwi Rahmat', 'BK008', 'Dasar-dasar linguistik', 1),
('P002', 'S002', 'Dhea Syifa Amalia', 'BK006', 'Fiqih Wanita', 1),
('P003', 'S003', 'Aliyyah Marwah Ulayya', 'BK014', 'Filsafat Timur', 1),
('P004', 'S004', 'Azka Fadlillah Nursy', 'BK016', 'Ilmu Politik Global', 1),
('P005', 'S005', 'Akmal Wicaksono', 'BK013', 'Sistem Komputer', 1),
('P006', 'S006', 'Alif Nurzhafran Sade', 'BK004', 'Dasar Komputer', 1),
('P007', 'S007', 'Nurtias Sil Isromi', 'BK012', 'Sejarah Dunia', 1),
('P008', 'S020', 'Hadi Purnama', 'BK004', 'Dasar Komputer', 1),
('P009', 'S002', 'Dhea Syifa Amalia', 'BK004', 'Dasar Komputer', 1);

-- --------------------------------------------------------

--
-- Table structure for table `pengembalian`
--

CREATE TABLE `pengembalian` (
  `id_pengembalian` varchar(20) NOT NULL,
  `id_peminjaman` varchar(20) DEFAULT NULL,
  `nama` varchar(100) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `tanggal_pengembalian` date DEFAULT NULL,
  `denda` int(11) DEFAULT NULL,
  `nm_pjg` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengembalian`
--

INSERT INTO `pengembalian` (`id_pengembalian`, `id_peminjaman`, `nama`, `judul_buku`, `tanggal_pengembalian`, `denda`, `nm_pjg`) VALUES
('K001', 'P008', 'Hadi Purnama', 'Dasar Komputer', '2025-06-26', 0, 'Anggeri Dwi Rahmat'),
('K002', 'P002', 'Dhea Syifa Amalia', 'Fiqih Wanita', '2025-06-26', 0, 'Andhika Agung Wicaksono'),
('K003', 'P006', 'Alif Nurzhafran Sade', 'Dasar Komputer', '2025-07-31', 28000, 'Andhika Agung Wicaksono'),
('K004', 'P009', 'Dhea Syifa Amalia', 'Dasar Komputer', '2025-07-11', 1000, 'Azka Miftah');

-- --------------------------------------------------------

--
-- Table structure for table `penjaga`
--

CREATE TABLE `penjaga` (
  `id_penjaga` varchar(10) NOT NULL,
  `nama_penjaga` varchar(30) NOT NULL,
  `email` varchar(20) NOT NULL,
  `no_hp` varchar(12) NOT NULL,
  `alamat` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penjaga`
--

INSERT INTO `penjaga` (`id_penjaga`, `nama_penjaga`, `email`, `no_hp`, `alamat`) VALUES
('PJ001', 'Anggeri Dwi Rahmat', 'anggerid7@gmail.com', '089655985401', 'JL. Makmur no 66 Munjul, Cipayung, Jakarta Timur'),
('PJ002', 'Azka Miftah', 'gusazka@gmail.com', '09876958574', 'Cileungsi, Bogor'),
('PJ003', 'Andhika Agung Wicaksono', 'dikagans@gmail.com', '098765678', 'Ciganjur, Jakarta Selatan');

-- --------------------------------------------------------

--
-- Table structure for table `rak`
--

CREATE TABLE `rak` (
  `kode_rak` varchar(10) NOT NULL,
  `nama_rak` varchar(50) NOT NULL,
  `lokasi` varchar(15) NOT NULL,
  `max` varchar(10) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rak`
--

INSERT INTO `rak` (`kode_rak`, `nama_rak`, `lokasi`, `max`, `keterangan`) VALUES
('RK001', 'Bahasa', 'Blok A', '75', 'khusus buku bahasa'),
('RK002', 'Sastra', 'Blok B', '50', 'khusus buku sastra'),
('RK003', 'Agama', 'Blok C', '100', 'khusus buku agama'),
('RK004', 'Ilmu-ilmu Sosial', 'Blok D', '75', 'khusus buku ilmu sosial'),
('RK005', 'Seni, Hiburan & Olahraga', 'Blok E', '50', 'khusus buku seni hiburan dan olahraga'),
('RK006', 'Karya Umum & Komputer', 'Blok F', '70', 'khusus buku karya umum & komputer'),
('RK007', 'Filsafat & Psikolog', 'Blok G', '80', 'khusus buku filsafat & psikolog'),
('RK008', 'Ilmu Pengetahuan Alam & Matematika', 'Blok H', '90', 'khusus buku IPA dan Matematika'),
('RK009', 'Teknologi & Ilmu Terapan', 'Blok I', '70', 'khusus buku Teknologi dan Ilmu terapan'),
('RK010', 'Geografi & Sejarah', 'Blok J', '80', 'khusus buku geografi dan sejarah');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `id_siswa` varchar(10) NOT NULL,
  `nama_siswa` varchar(30) NOT NULL,
  `jenis_klmn` varchar(50) NOT NULL,
  `jurusan` varchar(30) NOT NULL,
  `kelas` varchar(20) NOT NULL,
  `no_hp` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`id_siswa`, `nama_siswa`, `jenis_klmn`, `jurusan`, `kelas`, `no_hp`) VALUES
('S001', 'Anggeri Dwi Rahmat', 'Laki-Laki', 'AKUTANSI ', 'Kelas 12', '089655985401'),
('S002', 'Dhea Syifa Amalia', 'Perempuan', 'MULTIMEDIA ', 'Kelas 10', '089639039860'),
('S003', 'Aliyyah Marwah Ulayya', 'Perempuan', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 12', '678678678678'),
('S004', 'Azka Fadlillah Nursy', 'Laki-Laki', 'AKUTANSI ', 'Kelas 11', '089501541796'),
('S005', 'Akmal Wicaksono', 'Laki-Laki', 'TEKNIK KOMPUTER DAN JARINGAN', 'Kelas 12', '085782864104'),
('S006', 'Alif Nurzhafran Sade', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 11', '082233221122'),
('S007', 'Nurtias Sil Isromi', 'Perempuan', 'TEKNIK KOMPUTER DAN JARINGAN', 'Kelas 10', '6755745564'),
('S008', 'Citra Santoso', 'Perempuan', 'MULTIMEDIA ', 'Kelas 10', '88684570488'),
('S009', 'Dewi Rahayu', 'Laki-Laki', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '83539846418'),
('S010', 'Fajar Putra', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '88298964057'),
('S011', 'Citra Lestari', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '84105483722'),
('S012', 'Hadi Putra', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '83847180324'),
('S013', 'Andi Putra', 'Perempuan', 'MULTIMEDIA ', 'Kelas 10', '89700115893'),
('S014', 'Gita Sari', 'Perempuan', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '88361659897'),
('S015', 'Budi Sari', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '81038346479'),
('S016', 'Eka Putra', 'Laki-Laki', 'AKUTANSI ', 'Kelas 10', '88856852360'),
('S017', 'Indah Sari', 'Laki-Laki', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '86741684740'),
('S018', 'Joko Putra', 'Laki-Laki', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '82377378043'),
('S019', 'Fajar Putra', 'Perempuan', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '88161546469'),
('S020', 'Hadi Purnama', 'Laki-Laki', 'TEKNIK KOMPUTER DAN JARINGAN', 'Kelas 10', '89549994080'),
('S021', 'Indah Lestari', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '85457506973'),
('S022', 'Eka Purnama', 'Laki-Laki', 'TEKNIK KENDARAAN RINGAN ', 'Kelas 10', '81963559964'),
('S023', 'Citra Rahayu', 'Laki-Laki', 'AKUTANSI ', 'Kelas 10', '83030757330'),
('S024', 'Indah Putra', 'Laki-Laki', 'AKUTANSI ', 'Kelas 10', '83243630991'),
('S025', 'Indah Sari', 'Laki-Laki', 'TEKNIK KOMPUTER DAN JARINGAN', 'Kelas 10', '83891600683'),
('S026', 'Andi Saputra', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '84956337915'),
('S027', 'Citra Pratama', 'Laki-Laki', 'AKUTANSI ', 'Kelas 10', '83189414424'),
('S028', 'Ismail Marjuki', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 12', '087755667788'),
('S029', 'mimy', 'Perempuan', 'AKUTANSI ', 'Kelas 11', '083173257715'),
('S030', 'Muhammad Cecep', 'Laki-Laki', 'MULTIMEDIA ', 'Kelas 10', '0823417623');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`id_akun`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`kode_buku`),
  ADD KEY `fk_kode_rak` (`kode_rak`);

--
-- Indexes for table `detailpinjam`
--
ALTER TABLE `detailpinjam`
  ADD KEY `fkidpeminjaman` (`id_peminjaman`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`);

--
-- Indexes for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD PRIMARY KEY (`id_pengembalian`),
  ADD KEY `id_peminjaman` (`id_peminjaman`);

--
-- Indexes for table `penjaga`
--
ALTER TABLE `penjaga`
  ADD PRIMARY KEY (`id_penjaga`);

--
-- Indexes for table `rak`
--
ALTER TABLE `rak`
  ADD PRIMARY KEY (`kode_rak`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`id_siswa`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buku`
--
ALTER TABLE `buku`
  ADD CONSTRAINT `fk_kode_rak` FOREIGN KEY (`kode_rak`) REFERENCES `rak` (`kode_rak`) ON DELETE CASCADE;

--
-- Constraints for table `detailpinjam`
--
ALTER TABLE `detailpinjam`
  ADD CONSTRAINT `fkidpeminjaman` FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman` (`id_peminjaman`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
