package com.plan_it.mobile.plan_it;

/**
 * Created by Kevin on 10-Nov-2015.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>{
    public static View view;
    private Context context;
    private List<Item> mItems;

    public class ItemListViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView itemText;
        TextView whoseBringing;
        TextView estCostText;
        TextView actCostText;
        ImageView editEstCostButton;
        ImageView editActCostButton;
        Button claimButton;
        String currency = "$";

        public ItemListViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.item_list_card_view);
            itemText = (TextView)itemView.findViewById(R.id.item_list_text_item);
            whoseBringing = (TextView)itemView.findViewById(R.id.item_list_text_supplying);
            estCostText = (TextView)itemView.findViewById(R.id.item_list_text_estimated_cost);
            actCostText = (TextView)itemView.findViewById(R.id.item_list_text_actual_cost);
            editEstCostButton = (ImageView)itemView.findViewById(R.id.item_list_estimated_cost_edit);
            editActCostButton = (ImageView)itemView.findViewById(R.id.item_list_actual_cost_edit);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, ViewEventActvity.class);
                    context.startActivity(intent);
                    Toast.makeText(context, itemText.getText() + " " + whoseBringing.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        public void bind(Item item){
            itemText.setText(item.item);
            whoseBringing.setText(item.providing);
            estCostText.setText(currency + Double.toString(item.estCost));
            actCostText.setText(currency + Double.toString(item.actCost));
        }
    }

    public ItemListAdapter(Context context, List<Item> items)
    {
        this.context = context;
        mItems = new ArrayList<>(items);
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public void setItems(List<Item> items)
    {
        mItems = new ArrayList<>(items);
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ItemListViewHolder vh = new ItemListViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int i) {
        final Item item = mItems.get(i);
        holder.bind(item);
    }

}
