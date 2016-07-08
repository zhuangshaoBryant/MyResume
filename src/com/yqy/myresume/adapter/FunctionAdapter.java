package com.yqy.myresume.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yqy.myresume.R;
import com.yqy.myresume.bean.Function;

public class FunctionAdapter extends BaseAdapter{

	private Context context;
	private List<Function> list;
	private int height;
	
	public FunctionAdapter(Context context,List<Function> list,int height){
		this.context = context;
		this.list = list;
		this.height = height;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if(view == null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.item_function, null);
			holder.iconIv = (ImageView) view.findViewById(R.id.iconIv);
			holder.nameTv = (TextView) view.findViewById(R.id.nameTv);
			holder.itemLl = (LinearLayout) view.findViewById(R.id.itemLl);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.itemLl.setLayoutParams(new ListView.LayoutParams(LayoutParams.MATCH_PARENT, height/4));
		Function function = list.get(position);
		holder.iconIv.setImageResource(Integer.parseInt(function.getIconresource()));
		holder.nameTv.setText(function.getTitle());
		return view;
	}

	
	class ViewHolder{
		private ImageView iconIv;
		private TextView nameTv;
		private LinearLayout itemLl;
	}
	
}
