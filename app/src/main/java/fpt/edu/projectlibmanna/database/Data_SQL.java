package fpt.edu.projectlibmanna.database;

public class Data_SQL {
    public final static String INSERT_THUTHU = "INSERT INTO ThuThu(maThuThu, hoTen, matKhau) VALUES " +
            "('admin', 'Ho Minh Phu', '123456'), " +
            "('ngocn', 'Ngoc Nguyen', '123456'), " +
            "('phonghm', 'Phong Ho', '123456'), " +
            "('namnv', 'Nam Nguyen', '123456'), " +
            "('tinnv', 'Nguyen Viet Tin', '123456'), " +
            "('anhtq', 'Quoc An', '123456'), " +
            "('tuhnt', 'Thai Tu', '123456')";
    public final static String INSERT_THANHVIEN = "INSERT INTO ThanhVien(hoTen, namSinh) VALUES " +
            "('Trần Đức Hùng','1997')," +
            "('Hồ Nguyễn Thái Tú','1997')," +
            "('Lê Quốc Huy','1997')," +
            "('Bùi Đức Lân','1997')," +
            "('Phạm Công Hậu','1997')," +
            "('Hồ Minh Phú','1997')," +
            "('Lê Hữu Hưng','1997')," +
            "('Nguyễn Ngọc Ngân','1997')," +
            "('Đỗ Văn Trường','1997')," +
            "('Nguyễn Phi Anh','1997')," +
            "('Trần Thanh Viễn','1997')," +
            "('Võ Hữu Văn','1997')," +
            "('Đỗ Phú Quang','1997')," +
            "('Phạm Phú Huy','1997')," +
            "('Trần Văn Đô','1997')," +
            "('Dương Danh Quân','1997')," +
            "('Nguyễn Trọng Đạt','1997')," +
            "('Nguyễn Viết Quân','1997')," +
            "('Đỗ Lê Hiệp Phúc','1997')," +
            "('Nguyễn Tuấn Kiệt','1997')," +
            "('Lê Thị Diệp Chi','1997')," +
            "('Lê Thị Quỳnh Nga','1997')," +
            "('Nguyễn Hữu Quốc Cường','1997')," +
            "('Lê Anh Tú','1997')," +
            "('Bùi Ngọc Nguyên Vũ','1997')," +
            "('Nguyễn Tấn Quốc Anh','1997')," +
            "('Lê Thị Trang','1997')," +
            "('Trần Văn Vĩ','1997')," +
            "('Ngô Xuân Long','1997')," +
            "('Lê Viết Dũng','1997')," +
            "('Nguyễn Viết Tín','1997')," +
            "('Huỳnh Thị Tường Vy','1997')," +
            "('Lê Thị Thùy Vui','1997')," +
            "('Trương Phước Đạt','1997')," +
            "('Nguyễn Văn Tiến Dũng','1997')," +
            "('Hồ Thanh Quang','1997')," +
            "('Phạm Văn Sơn','1997')," +
            "('Lê Nguyễn Bảo Huy','1997')";
    public static final String INSERT_LOAISACH = "INSERT INTO LoaiSach(tenLoai) VALUES" +
            "('Lập trình cơ bản')," +
            "('Lập trình Android')," +
            "('Lập trình Web')," +
            "('Lập trình Java Core')," +
            "('Tiếng Anh cơ bản')," +
            "('Tiếng Anh nâng cao')";
    public static final String INSERT_SACH = "INSERT INTO Sach(tenSach, giaThue, maLoai) VALUES" +
            "('Lập Trình Ngôn Ngữ C/C++','5000','1')," +
            "('Cơ Sở Dữ Liệu','5000','1')," +
            "('Xây Dựng Trang Web','5000','1')," +
            "('Lập Trình Cơ Sở Với Javascrip','5000','1')," +
            "('Lập Trình Android Cơ Bản','5000','2')," +
            "('Thiết Kế Giao Diện Android','5000','2')," +
            "('Lập Trình Android Nâng Cao','5000','2')," +
            "('Lập Trình Sever Cho Android','5000','2')," +
            "('Lập Trình Mobile Đa Nền Tảng','5000','2')," +
            "('Lập Trình Angularjs','5000','3')," +
            "('Thiết Kế Web Với Html5/Css3','5000','3')," +
            "('Javascrip Nâng Cao','5000','3')," +
            "('Lập Trình Sever Cho Website','5000','3')," +
            "('Thiết Kế Web Với Bootrap','5000','3')," +
            "('Lập Trình Java Cơ Bản','5000','4')," +
            "('Lập Trình Java Nâng Cao','5000','4')," +
            "('Lập Trình Java Swing','5000','4')," +
            "('Lập Trình Mạng Với Java','5000','4')," +
            "('Dự Án Với Công Nghệ Spring Mvc','5000','4')," +
            "('Tiếng Anh Cho Người Mới Bắt Đầu','5000','5')," +
            "('Ngữ Pháp Tiếng Anh Căn Bản','5000','5')," +
            "('Từ Điển Anh Việt','5000','5')," +
            "('Giải Thích Ngữ Pháp','5000','6')," +
            "('Ngữ Pháptiếng Anh Nâng Cao','5000','6')," +
            "('Sách Bài Tập Tiếng Anh Nâng Cao','5000','6')";
    public static final String INSERT_PHIEUMUON = "INSERT INTO PhieuMuon(maThuThu, maThanhVien, maSach, tienThue, traSach, ngay) VALUES" +
            "('admin','1','16','5000','0','2021/07/02')," +
            "('admin','2','3','5000','0','2021/07/03')," +
            "('admin','3','21','5000','0','2021/07/04')," +
            "('admin','4','23','5000','0','2021/07/05')," +
            "('admin','5','2','5000','0','2021/07/06')," +
            "('admin','6','6','5000','0','2021/07/07')," +
            "('admin','7','12','5000','0','2021/07/08')," +
            "('admin','8','16','5000','0','2021/07/09')," +
            "('admin','9','16','5000','0','2021/07/10')," +
            "('admin','10','6','5000','0','2021/07/11')," +
            "('admin','11','9','5000','0','2021/07/12')," +
            "('admin','12','14','5000','0','2021/07/13')," +
            "('admin','13','8','5000','0','2021/07/14')," +
            "('admin','14','21','5000','0','2021/07/15')," +
            "('admin','15','13','5000','0','2021/07/16')," +
            "('admin','16','18','5000','0','2021/07/17')," +
            "('admin','17','4','5000','0','2021/07/18')," +
            "('admin','18','16','5000','0','2021/07/19')," +
            "('admin','19','5','5000','0','2021/07/20')," +
            "('admin','20','13','5000','0','2021/07/21')," +
            "('admin','21','14','5000','0','2021/07/22')," +
            "('admin','22','6','5000','0','2021/07/23')," +
            "('admin','23','3','5000','0','2021/07/24')," +
            "('admin','24','8','5000','0','2021/07/25')," +
            "('admin','25','21','5000','0','2021/07/26')," +
            "('admin','26','17','5000','0','2021/07/27')," +
            "('admin','27','18','5000','0','2021/07/28')," +
            "('admin','28','4','5000','0','2021/07/29')," +
            "('admin','29','15','5000','0','2021/07/30')," +
            "('admin','30','10','5000','0','2021/07/31')," +
            "('admin','31','4','5000','0','2021/08/01')," +
            "('admin','32','21','5000','0','2021/08/02')," +
            "('admin','33','11','5000','0','2021/08/03')," +
            "('admin','34','15','5000','0','2021/08/04')," +
            "('admin','35','14','5000','0','2021/08/05')," +
            "('admin','36','15','5000','0','2021/08/06')," +
            "('admin','37','22','5000','0','2021/08/07')," +
            "('admin','38','11','5000','0','2021/08/08')," +
            "('admin','1','7','5000','0','2021/08/09')," +
            "('admin','2','6','5000','0','2021/08/10')," +
            "('admin','3','19','5000','0','2021/08/11')," +
            "('admin','4','2','5000','0','2021/08/12')," +
            "('admin','5','9','5000','0','2021/08/13')," +
            "('admin','6','15','5000','0','2021/08/14')," +
            "('admin','7','5','5000','0','2021/08/15')," +
            "('admin','8','23','5000','0','2021/08/16')," +
            "('admin','9','12','5000','0','2021/08/17')," +
            "('admin','10','22','5000','0','2021/08/18')," +
            "('admin','11','9','5000','0','2021/08/19')," +
            "('admin','12','7','5000','0','2021/08/20')," +
            "('admin','13','12','5000','1','2021/08/21')," +
            "('admin','14','15','5000','1','2021/08/22')," +
            "('admin','15','13','5000','1','2021/08/23')," +
            "('admin','16','9','5000','1','2021/08/24')," +
            "('admin','17','2','5000','1','2021/08/25')," +
            "('admin','18','1','5000','1','2021/08/26')," +
            "('admin','19','9','5000','1','2021/08/27')," +
            "('admin','20','15','5000','1','2021/08/28')," +
            "('admin','21','21','5000','1','2021/08/29')," +
            "('admin','22','5','5000','1','2021/08/30')," +
            "('admin','23','19','5000','1','2021/08/31')," +
            "('admin','24','16','5000','1','2021/09/01')," +
            "('admin','25','10','5000','1','2021/09/02')," +
            "('admin','26','25','5000','1','2021/09/03')," +
            "('admin','27','7','5000','1','2021/09/04')," +
            "('admin','28','18','5000','1','2021/09/05')," +
            "('admin','29','14','5000','1','2021/09/06')," +
            "('admin','30','18','5000','1','2021/09/07')," +
            "('admin','31','9','5000','1','2021/09/08')," +
            "('admin','32','17','5000','1','2021/09/09')," +
            "('admin','33','7','5000','1','2021/09/10')," +
            "('admin','34','17','5000','1','2021/09/11')," +
            "('admin','35','23','5000','1','2021/09/12')," +
            "('admin','36','18','5000','1','2021/09/13')," +
            "('admin','37','16','5000','1','2021/09/14')," +
            "('admin','38','10','5000','1','2021/09/15')," +
            "('admin','1','18','5000','1','2021/09/16')," +
            "('admin','2','4','5000','1','2021/09/17')," +
            "('admin','3','24','5000','1','2021/09/18')," +
            "('admin','4','8','5000','1','2021/09/19')," +
            "('admin','5','22','5000','1','2021/09/20')," +
            "('admin','6','12','5000','1','2021/09/21')," +
            "('admin','7','2','5000','1','2021/09/22')," +
            "('admin','8','2','5000','1','2021/09/23')," +
            "('admin','9','13','5000','1','2021/09/24')," +
            "('admin','10','2','5000','1','2021/09/25')," +
            "('admin','11','12','5000','1','2021/09/26')," +
            "('admin','12','7','5000','1','2021/09/27')," +
            "('admin','13','17','5000','1','2021/09/28')," +
            "('admin','14','25','5000','1','2021/09/29')," +
            "('admin','15','10','5000','1','2021/09/30')," +
            "('admin','16','24','5000','1','2021/10/01')," +
            "('admin','17','5','5000','1','2021/10/02')," +
            "('admin','18','16','5000','1','2021/10/03')," +
            "('admin','19','12','5000','1','2021/10/04')," +
            "('admin','20','3','5000','1','2021/10/05')," +
            "('admin','21','25','5000','1','2021/10/06')," +
            "('admin','22','22','5000','1','2021/10/07')," +
            "('admin','23','25','5000','1','2021/10/08')," +
            "('admin','24','23','5000','1','2021/10/09')";
}
