package com.kdp.wanandroidclient.ui.adapter;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kdp.wanandroidclient.R;
import com.kdp.wanandroidclient.application.AppContext;
import com.kdp.wanandroidclient.bean.ArticleBean;
import com.kdp.wanandroidclient.common.Const;
import com.kdp.wanandroidclient.common.ListDataHolder;
import com.kdp.wanandroidclient.inter.OnArticleListItemClickListener;
import com.kdp.wanandroidclient.utils.DateUtils;

/**
 * author: 康栋普
 * date: 2018/2/12
 */

public class ArticleListAdapter extends BaseListAdapter<ArticleBean> {

    private int Type;
    private OnArticleListItemClickListener listener;

    public ArticleListAdapter(OnArticleListItemClickListener listener, int type) {
        this.listener = listener;
        this.Type = type;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_article_list;
    }

    @Override
    public void bindDatas(ListDataHolder holder, final ArticleBean bean, int itemType, final int position) {
        TextView tv_author = holder.getView(R.id.tv_author);
        final TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_time = holder.getView(R.id.tv_time);
        TextView tv_type = holder.getView(R.id.tv_type);
        ImageView img_collect = holder.getView(R.id.img_collect);
        tv_author.setText("");
        tv_author.append("作者: ");
        tv_author.append(getSpanText(bean.getAuthor()));
        tv_time.setText(DateUtils.parseTime(bean.getPublishTime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_title.setText(Html.fromHtml(bean.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_title.setText(Html.fromHtml(bean.getTitle()));
        }
        tv_type.setText("");
        tv_type.append("分类: ");
        tv_type.append(getSpanText(bean.getChapterName()));

        switch (Type) {
            case Const.LIST_TYPE.TREE:
                tv_type.setVisibility(View.GONE);
            case Const.LIST_TYPE.HOME:
            case Const.LIST_TYPE.SEARCH:
                img_collect.setImageResource(bean.isCollect() ? R.drawable.ic_favorite_light_24dp : R.drawable.ic_favorite_gray_24dp);
                img_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCollectClick(position, bean.getId());
                        }
                    }
                });
                break;
            case Const.LIST_TYPE.COLLECT:
                img_collect.setImageResource(R.drawable.ic_favorite_light_24dp);
                img_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCollectClick(position, bean.getId(), bean.getOriginId());
                        }
                    }
                });
                break;
            default:
                break;
        }


        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTreeClick(bean.getChapterId(), bean.getChapterName());
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(tv_title.getText().toString(), bean.getLink());
                }
            }
        });
    }

    private CharSequence getSpanText(String source) {
        Spannable mSpan = new SpannableString(source);
        mSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AppContext.getContext(), R.color._0091ea)), 0, source.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return mSpan;
    }
}
