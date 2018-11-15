package zs.xmx

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.lib_utils.utils.AlertDialog
import zs.xmx.lib_utils.utils.ToastUtils
import zs.xmx.lib_utils.utils.sp.SPUtils
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SPUtils.getInstance(this, "share")
        val str = "abc"
        SPUtils.putParam("token", str)
        ToastUtils.showToast(this, SPUtils.getParam("token", "200") as String)


    }

    fun showDilog(view: View) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val alertDialog2 = AlertDialog.getInstance(this@MainActivity)
                    alertDialog2
                            .setTitle("提示")
                            .setMsg("单例测试2")
                            .show()
                }

            }
        }, 0, 2000)


    }
}
