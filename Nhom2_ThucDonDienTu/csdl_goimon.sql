-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 19, 2022 lúc 07:57 AM
-- Phiên bản máy phục vụ: 10.4.21-MariaDB
-- Phiên bản PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `csdl_goimon`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `banan`
--

CREATE TABLE `banan` (
  `maban` int(11) NOT NULL,
  `tenban` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `banan`
--

INSERT INTO `banan` (`maban`, `tenban`) VALUES
(1, 'Bàn 1'),
(2, 'Bàn 2'),
(3, 'Bàn 3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_datmon`
--

CREATE TABLE `tbl_datmon` (
  `id` int(11) NOT NULL,
  `monan_id` int(11) NOT NULL,
  `dongia` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `thanhtien` int(11) NOT NULL,
  `maban` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_datmon`
--

INSERT INTO `tbl_datmon` (`id`, `monan_id`, `dongia`, `soluong`, `thanhtien`, `maban`) VALUES
(98, 1, 20000, 4, 80000, 1),
(99, 2, 25000, 5, 125000, 3),
(100, 33, 10000, 1, 10000, 2),
(105, 31, 35000, 2, 70000, 1),
(109, 2, 25000, 2, 50000, 2),
(110, 19, 150000, 2, 300000, 1),
(111, 0, 0, 0, 0, 0),
(113, 32, 40000, 1, 40000, 2),
(117, 95, 25000, 2, 50000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_loaimon`
--

CREATE TABLE `tbl_loaimon` (
  `maloai` int(11) NOT NULL,
  `tenloai` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `hinhanh` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_loaimon`
--

INSERT INTO `tbl_loaimon` (`maloai`, `tenloai`, `hinhanh`) VALUES
(1, 'Canh, Cháo, Súp', 'canh.png'),
(2, 'Bún, mì, miến, phở', 'bun.png'),
(3, 'Cơm', 'com.png'),
(4, 'Chiên, Xào', 'xao.png'),
(5, 'Nước giải khát', 'nuoc_giai_khat.png'),
(6, 'Ăn vặt', 'an_vat.png'),
(7, 'Nướng', 'an_vat.png'),
(8, 'Kho', 'kho.png'),
(9, 'Lẩu', 'lau.png'),
(10, 'Chay', 'chay.png'),
(11, 'Khác', 'khac.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_monan`
--

CREATE TABLE `tbl_monan` (
  `mamon` int(11) NOT NULL,
  `tenmon` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `gia` int(11) NOT NULL,
  `hinhanh` text COLLATE utf8_unicode_ci NOT NULL,
  `maloai_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_monan`
--

INSERT INTO `tbl_monan` (`mamon`, `tenmon`, `gia`, `hinhanh`, `maloai_id`) VALUES
(2, 'Súp Gà Ngô non', 25000, 'sup_ngo_cua.png', 1),
(3, 'Mực xào thập cẩm', 180000, 'muc_xao_thap_cam.png', 4),
(4, 'Cải làn xào tỏi', 70000, 'cai_lan_xao_toi.png', 4),
(5, 'Canh chua ngao', 90000, 'canh_chua_ngao.png', 1),
(6, 'Cơm tấm', 25000, 'com_tam.png', 3),
(7, 'Tôm sú nướng ngũ vị', 650000, 'tom_su_nuong_ngu_vi.png', 7),
(8, 'Bò xốt tiêu đen', 150000, 'bo_xot_tieu_den.png', 4),
(9, 'Ngọn susu xào tỏi', 45000, 'susu_xao_toi.png', 4),
(10, 'Canh bóng mọc', 60000, 'canh_bong.png', 1),
(11, 'Dưa hấu', 50000, 'dua_hau.png', 6),
(12, 'Súp ngô cua', 30000, 'sup_ngo_cua.png', 1),
(13, 'Rau muống xào tỏi', 15000, 'rau_muong_xao_toi.png', 4),
(14, 'Cà tím xào thịt heo', 15000, 'ca_tim_xao_thit_heo.png', 4),
(15, 'Cua biển rang me', 5000, 'cua_bien_rang_me_chua_ngot.png', 1),
(16, 'Khổ qua xào trứng', 2000, 'kho_qua_xao_trung.png', 4),
(17, 'Vịt xào măng', 30000, 'vit_xao_mang.png', 4),
(18, 'Hến xào sả ớt', 2000, 'hen_xao_sa_ot.png', 4),
(19, 'Lẩu cua đồng', 150000, 'lau_cua_dong.png', 9),
(20, 'Lẩu gà lá é', 200000, 'lau_ga_la_e.png', 9),
(21, 'Cá lóc kho nghệ', 30000, 'ca_loc_kho_nghe.png', 8),
(22, 'Gà kho gừng sả', 30000, 'ga_kho_gung_sa.png', 8),
(23, 'Cá kho tộ', 20000, 'ca_kho_to.png', 8),
(24, 'Ba rọi kho tiêu xanh', 25000, 'ba_roi_kho_tieu_xanh.png', 8),
(25, 'Cơm chiên hải sản', 50000, 'com_chien_hai_san.png', 3),
(26, 'Cơm chiên trứng', 25000, 'com_chien_trung.png', 3),
(27, 'Cơm sườn', 30000, 'com_suon.png', 3),
(28, 'Cơm gà', 35000, 'com_ga.png', 3),
(29, 'Bún riêu cua', 15000, 'bun_rieu_cua.png', 2),
(30, 'Bún bò Huế', 30000, 'bun_bo_hue.png', 2),
(31, 'Cháo ếch Singapore', 35000, 'chao_ech.png', 1),
(32, 'Cháo gà', 40000, 'chao_ga.png', 1),
(33, 'Nước suối', 10000, 'nuoc_suoi.png', 5),
(34, 'Nước ngọt các loại', 15000, 'nuoc_ngot.png', 5),
(95, 'Cơm chiên', 25000, 'img2140025467.jpg', 10),
(96, 'Cơm chiên', 15000, 'chim8.png', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_nguoidung`
--

CREATE TABLE `tbl_nguoidung` (
  `id` int(11) NOT NULL,
  `tendangnhap` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `matkhau` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `hoten` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `gioitinh` tinyint(1) NOT NULL,
  `ngaysinh` date DEFAULT NULL,
  `loaiquyen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_nguoidung`
--

INSERT INTO `tbl_nguoidung` (`id`, `tendangnhap`, `matkhau`, `hoten`, `gioitinh`, `ngaysinh`, `loaiquyen`) VALUES
(1, 'vonhan2k', '123456', 'Võ Hữu Nhân', 0, '2000-06-25', 1),
(2, 'thaingoc2k', 'admin', 'Phương Thái Ngọc', 0, '2000-03-01', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `banan`
--
ALTER TABLE `banan`
  ADD PRIMARY KEY (`maban`);

--
-- Chỉ mục cho bảng `tbl_datmon`
--
ALTER TABLE `tbl_datmon`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_loaimon`
--
ALTER TABLE `tbl_loaimon`
  ADD PRIMARY KEY (`maloai`);

--
-- Chỉ mục cho bảng `tbl_monan`
--
ALTER TABLE `tbl_monan`
  ADD PRIMARY KEY (`mamon`);

--
-- Chỉ mục cho bảng `tbl_nguoidung`
--
ALTER TABLE `tbl_nguoidung`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `banan`
--
ALTER TABLE `banan`
  MODIFY `maban` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tbl_datmon`
--
ALTER TABLE `tbl_datmon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT cho bảng `tbl_loaimon`
--
ALTER TABLE `tbl_loaimon`
  MODIFY `maloai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `tbl_monan`
--
ALTER TABLE `tbl_monan`
  MODIFY `mamon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;

--
-- AUTO_INCREMENT cho bảng `tbl_nguoidung`
--
ALTER TABLE `tbl_nguoidung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
