package com.sky.quickindex;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 作者：SKY
 * 创建时间：2016-9-11 15:24
 * 描述：对模拟联系人列表的适配器的搭建
 */

public class ContactAdapter extends BaseAdapter {

    private List<ContactInfo> infoList;

    public ContactAdapter (List<ContactInfo> infoList) {
        this.infoList = infoList;
    }

    @Override
    public int getCount () {
        return infoList.size();
    }

    @Override
    public Object getItem (int position) {
        return getItem(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.lv_contact_list_item, null);
            holder.tv_first_letter = (TextView) convertView.findViewById(R.id.tv_first_letter);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContactInfo info = infoList.get(position);
        holder.tv_first_letter.setText(info.getFirstLetter());
        holder.tv_name.setText(info.getName());
        if (position == 0){
            holder.tv_first_letter.setVisibility(View.VISIBLE);
        } else {
            // 将当前 view 与上一个 view 比较，如果首字母相同，则只显示上一个的首字母view
            ContactInfo lastInfo = infoList.get(position - 1 );
            if (lastInfo.getFirstLetter().equals(info.getFirstLetter())){
                holder.tv_first_letter.setVisibility(View.GONE);
            }else{
                holder.tv_first_letter.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    static class ViewHolder{
        private TextView tv_first_letter;
        private TextView tv_name;
    }
}
