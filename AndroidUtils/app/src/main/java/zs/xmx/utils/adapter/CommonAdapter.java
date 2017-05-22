package zs.xmx.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/10/26 21:51
 * @本类描述	  通用适配器
 * @内容说明
 * ---------------------------------------------     
 * @补充内容  todo List中有多种不同类型Item的处理
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> mDatas;
    protected Context mContext;
    protected int     mLayoutID;

    /**
     * @param context       上下文
     * @param layoutID_item item 布局ID
     * @param datas         List<Bean> bean类数组
     */
    public CommonAdapter(Context context, final int layoutID_item, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutID = layoutID_item;

    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    @Override
    public T getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * ItemInfo infos = mDatas.get(position);
     * holder.setText(R.id.tv_title, infos.getTitle());
     * holder.setText(R.id.tv_desc, infos.getDesc());
     * holder.setText(R.id.tv_data, infos.getData());
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutID, position);

        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    /**
     * 分析得知需要Item,holder,position
     *
     * @param holder
     * @param item
     * @param position
     */
    protected abstract void convert(ViewHolder holder, T item, int position);


    //<------------------------多种Item------------------------------------>
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 用一个List数组存储Item,数组大小就是这个ViewType的大小
     * List 数组的角标位置就是对应的Item
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
