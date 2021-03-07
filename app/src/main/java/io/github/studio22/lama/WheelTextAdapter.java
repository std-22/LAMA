package io.github.studio22.lama;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class WheelTextAdapter extends CursorWheelLayout.CycleWheelAdapter {

    private final List<MenuItemData> menuItems;
    private final LayoutInflater inflater;
    private final int gravity;

    public WheelTextAdapter(Context context, List<MenuItemData> menuItems) {
        this.menuItems = menuItems;
        gravity = Gravity.CENTER;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public View getView(View parent, int position) {
        MenuItemData itemData = getItem(position);
        @SuppressLint("InflateParams") View root = inflater.inflate(R.layout.text_rotation, null, false);

        TextView textView = root.findViewById(R.id.wheel_menu_item_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(18);
        textView.setText(itemData.number);
        //textView.setTextColor(Color.parseColor("#151e27"));

        if (textView.getLayoutParams() instanceof FrameLayout.LayoutParams)
            ((FrameLayout.LayoutParams) (textView.getLayoutParams())).gravity = gravity;
        return root;
    }

    @Override
    public MenuItemData getItem(int position) {
        return menuItems.get(position);
    }
}
