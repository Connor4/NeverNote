package com.connor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

public class ArcMenu extends ViewGroup implements OnClickListener
{
	private Status mCurrentStatus = Status.CLOSE;
	private View mCButton;
	private OnArcMenuItemClickListener mArcMenuItemClickListner;

	public enum Status
	{
		OPEN, CLOSE
	}

	public enum Position
	{
		RIGHT_BOTTOM
	}

	public interface OnArcMenuItemClickListener
	{
		void onClick(View view, int position);
	}

	public void setOnArcMenuItemClickListener(
			OnArcMenuItemClickListener mArcMenuItemClickListner)
	{
		this.mArcMenuItemClickListner = mArcMenuItemClickListner;
	}

	public ArcMenu(Context context)
	{
		this(context, null);
	}

	public ArcMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int count = getChildCount();
		for (int i = 0; i < count; i++)
		{
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		if (changed)
		{
			layoutCButton();
			int count = getChildCount();

			for (int i = 0; i < count - 1; i++)
			{
				View child = getChildAt(i + 1);
				child.setVisibility(View.GONE);
				int cWidth = child.getMeasuredWidth();
				int cHeight = child.getMeasuredHeight();

				int cl = (int) (getMeasuredWidth() - 1.5 * cWidth);
				int ct = (int) (getMeasuredHeight() - 1.2 * (i + 1) * cHeight - 1.2 * cHeight);

				child.layout(cl, ct, cl + cWidth, ct + cWidth);

			}
		}
	}

	private void layoutCButton()
	{
		mCButton = getChildAt(0);
		mCButton.setOnClickListener(this);

		int l = 0;
		int t = 0;

		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();

		l = (int) (getMeasuredWidth() - 1.5 * width);
		t = (int) (getMeasuredHeight() - 1.2 * height);

		mCButton.layout(l, t, l + width, t + width);
	}

	@Override
	public void onClick(View v)
	{
		RotateCButton(v, 0f, 360f, 300);
		ToggleMenu(300);
	}

	public void ToggleMenu(int duration)
	{
		// 为menuItem添加平移动画和旋转动画
		int count = getChildCount();

		for (int i = 0; i < count - 1; i++)
		{
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);
			int cHeight = childView.getMeasuredHeight();

			int ct = (int) (1.2 * (i + 1) * cHeight);

			AnimationSet animset = new AnimationSet(true);
			Animation tranAnim = null;

			// to open
			if (mCurrentStatus == Status.CLOSE)
			{
				tranAnim = new TranslateAnimation(0, 0, ct, 0);
				childView.setClickable(true);
				childView.setFocusable(true);

			} else
			// to close
			{
				tranAnim = new TranslateAnimation(0, 0, 0, ct);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			tranAnim.setFillAfter(true);
			tranAnim.setDuration(duration);
			tranAnim.setStartOffset((i * 100) / count);

			tranAnim.setAnimationListener(new AnimationListener()
			{

				@Override
				public void onAnimationStart(Animation animation)
				{

				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{

				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
					if (mCurrentStatus == Status.CLOSE)
					{
						childView.setVisibility(View.GONE);
					}
				}
			});
			// 旋转动画
			RotateAnimation rotateAnim = new RotateAnimation(0, 720,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);

			animset.addAnimation(rotateAnim);
			animset.addAnimation(tranAnim);
			childView.startAnimation(animset);

			final int pos = i + 1;
			childView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (mArcMenuItemClickListner != null)
						mArcMenuItemClickListner.onClick(childView, pos);

					menuItemAnim(pos - 1);
					changeStatus();

				}
			});
		}
		// 切换菜单状态
		changeStatus();
	}

	private void menuItemAnim(int pos)
	{
		for (int i = 0; i < getChildCount() - 1; i++)
		{

			View childView = getChildAt(i + 1);
			if (i == pos)
			{
				childView.startAnimation(scaleBigAnim(300));
			} else
			{

				childView.startAnimation(scaleSmallAnim(300));
			}

			childView.setClickable(false);
			childView.setFocusable(false);

		}

	}

	/**
	 * 为当前点击的Item设置变小和透明度降低的动画
	 * 
	 * @param duration
	 * @return
	 */

	private Animation scaleSmallAnim(int duration)
	{

		AnimationSet animationSet = new AnimationSet(true);

		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	/**
	 * 为当前点击的Item设置变大和透明度降低的动画
	 * 
	 * @param duration
	 * @return
	 */
	private Animation scaleBigAnim(int duration)
	{
		AnimationSet animationSet = new AnimationSet(true);

		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);

		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

	}

	/**
	 * 切换菜单状态
	 */
	private void changeStatus()
	{
		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN
				: Status.CLOSE);
	}

	public boolean isOpen()
	{
		return mCurrentStatus == Status.OPEN;
	}

	/**
	 * 
	 * @param v
	 * @param start
	 *            开始旋转角度
	 * @param end
	 *            结束旋转角度
	 * @param duration
	 *            动画时间
	 */
	private void RotateCButton(View v, float start, float end, int duration)
	{
		RotateAnimation anim = new RotateAnimation(start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}

}
