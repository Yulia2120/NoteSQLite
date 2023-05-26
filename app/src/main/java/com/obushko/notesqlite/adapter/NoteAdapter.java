package com.obushko.notesqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obushko.notesqlite.R;
import com.obushko.notesqlite.activities.EditActivity;
import com.obushko.notesqlite.data.NoteDbConstants;
import com.obushko.notesqlite.data.NoteDbManager;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context context;
    private List<ListItem> mainArray;

    public NoteAdapter(Context context) {
        this.context = context;
        mainArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new NoteViewHolder(view, context, mainArray);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            holder.setData(mainArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainArray.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView tvTitle;
        private final Context context;
        private List<ListItem> mainArray;
        public NoteViewHolder(@NonNull View itemView, Context context, List<ListItem> mainArray) {
            super(itemView);
            this.context = context;
            this.mainArray = mainArray;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }
        public void setData(String title){
            tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
           // Log.d("MyLog", "Pressed: " + getAdapterPosition());
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(NoteDbConstants.LIST_ITEM_INTENT, mainArray.get(getAdapterPosition()));
            intent.putExtra(NoteDbConstants.EDIT_STATE, false);
            context.startActivity(intent);
        }
    }
    public void updateAdapter(List<ListItem> newList){
        mainArray.clear();
        mainArray.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, NoteDbManager noteDbManager){
        noteDbManager.deleteFromDb(mainArray.get(position).getId());   //удалили элемент из бд
        mainArray.remove(position);   //удалили его из списка
        notifyItemRangeChanged(0, mainArray.size());   //изменить список элементов(по позиции)
        notifyItemRemoved(position);   //сообщили адаптеру об удалении элемента
    }
}
