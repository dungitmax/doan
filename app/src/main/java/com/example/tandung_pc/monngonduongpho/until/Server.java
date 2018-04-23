package com.example.tandung_pc.monngonduongpho.until;

/**
 * Created by TANDUNG-PC on 2/10/2018.
 */

public class Server {
    //public static String localhost = "http://192.168.1.14/";//home
    // public static String localhost = "http://192.168.43.2/";//home
    //public static String localhost = "http://192.168.1.104/";//home
    // public static String localhost = "http://10.17.6.184/";//home
    public static String localhost = "http://10.17.6.106/";//other
    //public static String localhost = "http://192.168.1.11/";//cuong's house
    public static String DuongdanBunPho = localhost + "server_appmonanduongpho/getfoodbunpho.php";
    public static String DuongdanMonChe = localhost + "server_appmonanduongpho/getfoodmonche.php";
    public static String DuongdanNuocUong = localhost + "server_appmonanduongpho/getfoodnuocuong.php";
    public static String DuongdanChienNuong = localhost + "server_appmonanduongpho/getfooddochien.php";
    public static String DuongdanBanhMi = localhost + "server_appmonanduongpho/getfoodbanhmi.php";
    public static String DuongdanOther = localhost + "server_appmonanduongpho/getfoodother.php";
    public static String DuongdanRegister = localhost + "server_appmonanduongpho/register.php";
    public static String DuongdanLogin = localhost + "server_appmonanduongpho/login.php";
    public static String DuongdanGetAllCuaHang = localhost + "server_appmonanduongpho/get_all_cuahang.php";
    public static String DuongdanUploadImage = localhost + "server_appmonanduongpho/upload.php";
    public static String DuongdanGetUser = localhost + "server_appmonanduongpho/getuser.php";
    public static String Duongdangetcoment = localhost + "server_appmonanduongpho/get_comment_by_foodid.php?page=";
    public static String DuongdanSendComment = localhost + "server_appmonanduongpho/create_comment.php";

}
