package dk.aau.cs.giraf.launcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HomeLayout extends RelativeLayout {
	
	private Context mContext;

	public HomeLayout(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public HomeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public HomeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec+600, heightMeasureSpec);
    }

}
