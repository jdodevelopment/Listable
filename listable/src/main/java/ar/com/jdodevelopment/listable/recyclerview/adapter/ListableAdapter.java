/*
    Copyright (c) 2019 Juan Daniel Ornella <juan.daniel.sp@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package ar.com.jdodevelopment.listable.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.jdodevelopment.listable.annotation.ListableEntity;
import ar.com.jdodevelopment.listable.recyclerview.viewholder.ListableViewHolder;

/**
 * It allow to list entities only declaring the Annotation '@ListableEntity'
 *
 * @author Juan Daniel Ornella
 * @param <T> the class that want to list
 */
public class ListableAdapter<T> extends RecyclerView.Adapter<ListableViewHolder<T>> {


    /**
     * Listener used to dispatch click events.
     */
    private ListableAdapter.OnListableClickListener<T> onListableClickListener;

    /**
     * Listener used to dispatch long click events.
     */
    private ListableAdapter.OnListableLongClickListener<T> onListableLongClickListener;

    /**
     * Resource ID for an XML layout resource to inflate to display the content of RecyclerView.
     */
    protected int layoutId;

    /**
     * Contains the list of objects that represent the data of this ListableAdapter.
     */
    protected final List<T> list;

    /**
     * Represent the entity than you want to list
     */
    protected final Class entityClass;


    /**
     * @param entityClass the class that wrap the entity that will be listed.
     */
    public ListableAdapter(Class entityClass) {
        this.list = new ArrayList<>();
        this.entityClass = entityClass;
        initListableEntity();
    }

    /**
     * @param entityClass the class that wrap the entity that will be listed.
     * @param layoutId    override the value 'layoutResource' of the Annotation '@ListableEntity'
     *                    for the purpose of declaring multiple views of the same entity
     */
    public ListableAdapter(Class entityClass, int layoutId) {
        this(entityClass);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ListableViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(this.layoutId, parent, false);
        return new ListableViewHolder<>(view, entityClass);
    }

    @Override
    public void onBindViewHolder(@NonNull ListableViewHolder<T> viewHolder, int position) {
        T obj = list.get(position);
        viewHolder.setValuesInViews(obj);

        if (onListableClickListener != null)
            viewHolder.setOnListableClickListener(onListableClickListener, obj);
        if (onListableLongClickListener != null)
            viewHolder.setOnListableLongClickListener(onListableLongClickListener, obj);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Init the required parameters that are getted from the annotation @ListableEntity.
     *
     * @throws IllegalStateException when entityClass haven't declared the Annotation '@ListableEntity'
     */
    private void initListableEntity() {
        ListableEntity listableEntity = (ListableEntity) entityClass.getAnnotation(ListableEntity.class);
        if (listableEntity != null) {
            this.layoutId = listableEntity.layoutResource();
        } else {
            String message = "The class " + entityClass.getName() + " haven't Annotation @ListableEntity";
            throw new IllegalStateException(message);
        }
    }

    /**
     * Appends the specified element to the end of this list and notify the RecyclerView.
     *
     * @param list collection containing elements to be added to this list
     */
    public void add(@NonNull Collection<? extends T> list) {
        int oldSize = this.list.size();
        this.list.addAll(list);
        notifyItemRangeInserted(oldSize, list.size() - oldSize);
    }


    /**
     * Appends the specified element to the end of this list and notify the RecyclerView.
     *
     * @param object element to be appended to this list
     */
    public void add(T object) {
        list.add(object);
        notifyItemInserted(list.size() - 1);
    }


    /**
     * Removes the element at the specified position in this list and notify the RecyclerView.
     *
     * @param position the index of the element to be removed
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Removes the object in this list and notify the RecyclerView.
     *
     * @param object to be removed
     */
    public void remove(T object) {
        int position = list.indexOf(object);
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Removes all elements from the list and notify the RecyclerView.
     */
    public void removeAll() {
        int oldSize = list.size();
        list.clear();
        notifyItemRangeRemoved(0, oldSize);
    }


    /**
     * Register a callback to be invoked when this view is clicked.
     *
     * @param onListableClickListener The callback that will run
     */
    public void setOnListableClickListener(OnListableClickListener<T> onListableClickListener) {
        this.onListableClickListener = onListableClickListener;
    }


    /**
     * Register a callback to be invoked when this view is clicked and held.
     *
     * @param onListableLongClickListener The callback that will run
     */
    public void setOnListableLongClickListener(OnListableLongClickListener<T> onListableLongClickListener) {
        this.onListableLongClickListener = onListableLongClickListener;
    }


    /**
     * Interface definition for a callback to be invoked when a itemview is clicked.
     */
    public interface OnListableClickListener<T> {
        void onListableClick(View view, T object);
    }


    /**
     * Interface definition for a callback to be invoked when a itemview is long clicked.
     */
    public interface OnListableLongClickListener<T> {
        void onListableLongClick(View view, T object);
    }


}