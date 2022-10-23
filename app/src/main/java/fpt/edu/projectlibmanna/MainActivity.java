package fpt.edu.projectlibmanna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.databinding.ActivityMainBinding;
import fpt.edu.projectlibmanna.model.ThuThu;
import fpt.edu.projectlibmanna.ui.login.ChangePasswordActivity;
import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity{
    public String key_user = "";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_phieu_muon)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        welcomeUser(navigationView);

        navigationView.getMenu().findItem(R.id.nav_doimk).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                /*chuyển qua màng hình đổi mk và gửi đi mã thu thu*/
                startScreenChangePassword();
                return true;
            }
        });
    }

    private void startScreenChangePassword() {
        Intent myIntent = getIntent();
        String userName = myIntent.getStringExtra("EXTRA_USER");
        Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
        intent.putExtra("EXTRA_USER", userName);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void welcomeUser(@NonNull NavigationView navigationView) {
        /*
        * XỬ LÝ THÔNG TIN CỦA NGƯỜI DÙNG
        * */
        Intent intent = getIntent();
        String userName = intent.getStringExtra("EXTRA_USER");
        ThuThuDao thuThuDao = new ThuThuDao(this);
        ThuThu thuthu = thuThuDao.getByMaThuThu(userName);
        View headerLayout = navigationView.getHeaderView(0);
        TextView tvUserName = headerLayout.findViewById(R.id.tv_nav_headr_name);
        TextView tvUserRole = headerLayout.findViewById(R.id.tv_nav_headr_role);
        tvUserName.setText(thuthu.getHoTen());
        if(userName.equals("admin")){
            tvUserRole.setText("(Admin)");
            /*
             * KHI ĐĂNG NHẬP VỚI TÀI KHOẢN ADMIN -> CHỨC NĂNG QL THỦ THƯ HOẶC ĐỘNG
             * */
            navigationView.getMenu().findItem(R.id.nav_ql_thu_thu).setVisible(true);
        }else {
            tvUserRole.setText("(ThuThu)");
            navigationView.getMenu().findItem(R.id.nav_ql_thu_thu).setVisible(false);
        }
        key_user = userName;
        StyleableToast.makeText(MainActivity.this, "Chào mừng " + userName, Toast.LENGTH_LONG, R.style.ToastWelcome).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
    }

    public String getKey_user(){
        return key_user;
    }
}