import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //ドライバのクラス読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");

            //DB接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Password");

            //DBとのやり取りする窓口(PreparedStatementオブジェクト)を作成
            String selectSql="SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(selectSql);

            //キーボードから検索するID(数字）を入力
            int num =keyInNum();

            //入力された数字を元にSELECT文の作成・実行
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            //結果の表示
            while(rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);
            }


        } catch (ClassNotFoundException e) {
            System.err.println("jdbcドライバの読み込みに失敗");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースの接続に異常が発生しました。");
            e.printStackTrace();
        } finally {
            //接続を閉じる
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSet切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatement切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    //標準入力から入力された値をIntで返す
    private static int keyInNum() {
        Scanner scan = new Scanner(System.in);
        System.out.println("検索キーワードを入力してください");
        int num = Integer.parseInt(scan.nextLine());
        scan.close();
        return num;
    }

}
