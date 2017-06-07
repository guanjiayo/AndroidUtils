package zs.xmx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/7
 * @本类描述	  <merge>标签使用
 * @使用场景
 * 1. 根布局是FrameLayout且不需要设置background或padding等属性,可以用merge代替,因为Activity的ContentView父元素就是FrameLayout,所以可以用merge消除只剩一个.
 * 2. 某布局作为子布局被其他布局include时,使用merge当作该布局的顶节点,这样在被引入时顶结点会自动被忽略,而将其子节点全部合并到主布局中.
 * 3. 自定义View如果继承LinearLayout(ViewGroup),建议让自定义View的布局文件根布局设置成merge,这样能少一层结点.
 *
 * @补充内容
 * 1. 因为merge标签并不是View,所以在通过LayoutInflate.inflate()方法渲染的时候,第二个参数必须指定一个父容器,且第三个参数必须为true,也就是必须为merge下的视图指定一个父亲节点.
 * 2. 因为merge不是View,所以对merge标签设置的所有属性都是无效的.
 * 3. 注意如果<include>的layout用了<merge>,调用<include>的根布局也使用了<merge>标签,那么就失去布局的属性了
 * 4. <merge>标签必须使用在根布局
 * 5. <ViewStub>标签中的layout布局不能使用<merge>标签
 *
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class MergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
    }
}
